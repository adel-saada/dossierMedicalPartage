package com.medical.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medical.beans.Consultation;
import com.medical.beans.Medecin;
import com.medical.beans.Patient;
import com.medical.dao.ConsultationDao;
import com.medical.dao.DaoFactory;
import com.medical.dao.MedecinDao;
import com.medical.dao.PatientDao;
import com.medical.forms.ConsultationForm;
import com.medical.forms.MedecinForm;
import com.medical.forms.PatientForm;

@WebServlet( urlPatterns = "/PanelPatient" )
public class PatientPanel extends HttpServlet {

    private static final long          serialVersionUID                              = -750566686517763815L;

    public static final String         CONF_DAO_FACTORY                              = "daofactory";

    public static final String         ATT_SESSION_PATIENT                           = "sessionPatient";
    public static final String         ATT_PATIENT                                   = "patient";
    public static final String         ATT_PATIENT_FORM                              = "patientForm";
    private static final String        ATT_LISTE_MEDECIN                             = "listeMedecin";

    public static final String         ACCES_CONNEXION                               = "/WEB-INF/connexion.jsp";

    public static final String         ACCES_PATIENT                                 = "/panelPatient/patient.jsp";
    public static final String         ACCES_PATIENT_2                               = "/panelPatient/patient2.jsp";
    private static final String        ACCES_PATIENT_3                               = "/panelPatient/patient3.jsp";

    public static final String         STR_AJOUTER_CONSULTATION                      = "ajouterConsultation";
    public static final String         STR_MODIFIER_CONSULTATION                     = "modifierConsultation";
    public static final String         STR_SUPPRIMER_CONSULTATION                    = "supprimerConsultation";

    private static final String        ATT_LISTE_CONSULTATION                        = "listeConsultation";

    private static final String        ATT_CONSULTATION_FORM                         = "consultationForm";

    private static final String        STR_MODIFIER_PATIENT                          = "modifierPatient";

    private static final String        ATT_MAP_CONSULTATION_MEDECINS_POUR_UN_PATIENT = "mapConsultationsMedecinsPourUnPatient";

    private List<Medecin>              listeMedecin;

    private List<Consultation>         listeConsultation;
    private Map<Medecin, Consultation> mapConsultationsMedecinsPourUnPatient;

    private PatientDao                 patientDao;
    private MedecinDao                 medecinDao;
    private ConsultationDao            consultationDao;

    @Override
    public void init() throws ServletException {
        this.patientDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPatientDao();
        this.medecinDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMedecinDao();
        this.consultationDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getConsultationDao();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_PATIENT ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_PATIENT ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        }
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        PatientForm patientForm = new PatientForm( patientDao );
        MedecinForm medecinForm = new MedecinForm( medecinDao );
        ConsultationForm consultationForm = new ConsultationForm( consultationDao );

        listeMedecin = medecinForm.listerMedecins();
        listeConsultation = consultationForm.listerConsultations();

        String action = request.getParameter( "action" );
        HttpSession session = request.getSession( true );

        switch ( action ) {
        case STR_AJOUTER_CONSULTATION:
            consultationForm.ajouterConsultation( request, (Patient) session.getAttribute( ATT_SESSION_PATIENT ),
                    listeMedecin );
            if ( request.getParameter( "id-medecin-clique" ).matches( "[0-9]+" ) ) {
                int idMedecin = Integer.parseInt( request.getParameter( "id-medecin-clique" ) );
                session.setAttribute( "medecinSelection", medecinForm.remplirInformationsMedecinParId( idMedecin ) );
            }
            break;
        case STR_MODIFIER_CONSULTATION:
            consultationForm.modifierConsultation( request, listeMedecin );
            break;
        case STR_SUPPRIMER_CONSULTATION:
            consultationForm.supprimerConsultation( request, listeMedecin );
            break;
        case STR_MODIFIER_PATIENT:
            patientForm.modifierPatient( request, patientForm.listerPatients() );
            patientForm.remplirInformationsPatient( (Patient) session.getAttribute( ATT_SESSION_PATIENT ) );
            break;
        }

        mapConsultationsMedecinsPourUnPatient = consultationForm
                .mapperConsultationsMedecinsPourUnPatient( (Patient) session.getAttribute( ATT_SESSION_PATIENT ) );

        request.setAttribute( ATT_PATIENT_FORM, patientForm );
        request.setAttribute( ATT_CONSULTATION_FORM, consultationForm );

        if ( session.getAttribute( ATT_SESSION_PATIENT ) == null ) {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        } else {
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            session.setAttribute( ATT_LISTE_CONSULTATION, listeConsultation );
            session.setAttribute( ATT_MAP_CONSULTATION_MEDECINS_POUR_UN_PATIENT,
                    mapConsultationsMedecinsPourUnPatient );
            if ( action.startsWith( "ajouter" ) ) {
                this.getServletContext().getRequestDispatcher( ACCES_PATIENT ).forward( request, response );
            } else if ( action.endsWith( "Patient" ) ) {
                this.getServletContext().getRequestDispatcher( ACCES_PATIENT_3 ).forward( request, response );
            } else if ( action.startsWith( "modifier" ) || action.startsWith( "supprimer" ) ) {
                this.getServletContext().getRequestDispatcher( ACCES_PATIENT_2 ).forward( request, response );
            }
        }
    }

}
