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

import com.medical.beans.Admin;
import com.medical.beans.Consultation;
import com.medical.beans.Medecin;
import com.medical.beans.Patient;
import com.medical.dao.AdminDao;
import com.medical.dao.ConsultationDao;
import com.medical.dao.DaoFactory;
import com.medical.dao.MedecinDao;
import com.medical.dao.PatientDao;
import com.medical.forms.AdminForm;
import com.medical.forms.ConsultationForm;
import com.medical.forms.MedecinForm;
import com.medical.forms.PatientForm;

@WebServlet( urlPatterns = "/Connexion" )
public class Connexion extends HttpServlet {
    private static final long          serialVersionUID                              = 1L;

    public static final String         CONF_DAO_FACTORY                              = "daofactory";

    public static final String         PARAM_LOGIN_RADIO                             = "login-radio";

    /* admin */
    public static final String         ATT_SESSION_ADMIN                             = "sessionAdmin";
    public static final String         ATT_ADMIN_FORM                                = "adminForm";
    public static final String         ATT_ADMIN                                     = "admin";
    public static final String         ATT_LISTE_PATIENT                             = "listePatient";
    public static final String         ATT_LISTE_MEDECIN                             = "listeMedecin";
    public static final String         ACCES_ADMIN                                   = "/restreint/admin.jsp";

    /* patient */
    public static final String         ATT_SESSION_PATIENT                           = "sessionPatient";
    public static final String         ATT_PATIENT                                   = "patient";
    public static final String         ATT_PATIENT_FORM                              = "patientForm";
    public static final String         ACCES_PATIENT                                 = "/panelPatient/patient.jsp";
    private static final String        ATT_MAP_CONSULTATION_MEDECINS_POUR_UN_PATIENT = "mapConsultationsMedecinsPourUnPatient";

    public static final String         ATT_LISTE_CONSULTATION                        = "listeConsultation";
    private static final String        ATT_LISTE_CONSULTATION_PATIENT_MEDECIN        = "listeConsultationPatientMedecin";

    /* medecin */
    public static final String         ATT_SESSION_MEDECIN                           = "sessionMedecin";
    public static final String         ATT_MEDECIN                                   = "medecin";
    public static final String         ATT_MEDECIN_FORM                              = "medecinForm";
    public static final String         ACCES_MEDECIN                                 = "/panelMedecin/medecin.jsp";

    public static final String         ACCES_CONNEXION                               = "/WEB-INF/connexion.jsp";

    /* consultation */
    private static final String        ATT_CONSULTATION_FORM                         = "consultationForm";

    private static final String        ATT_MAP_CONSULTATION_PATIENT                  = "mapConsultationPatient";

    private AdminDao                   adminDao;
    private PatientDao                 patientDao;
    private MedecinDao                 medecinDao;
    private ConsultationDao            consultationDao;

    private List<Medecin>              listeMedecin;
    private List<Patient>              listePatient;

    private List<Consultation>         listeConsultation;
    private List<Consultation>         listeConsultationPatientMedecin;

    private Map<Medecin, Consultation> mapConsultationsMedecinsPourUnPatient;
    private Map<Medecin, Consultation> mapConsultationPatient;

    @Override
    public void init() throws ServletException {
        this.adminDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAdminDao();
        this.patientDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPatientDao();
        this.medecinDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMedecinDao();
        this.consultationDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getConsultationDao();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_ADMIN ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_ADMIN ).forward( request, response );
        } else if ( session.getAttribute( ATT_SESSION_PATIENT ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_PATIENT ).forward( request, response );
        } else if ( session.getAttribute( ATT_SESSION_MEDECIN ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_MEDECIN ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        }

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession( true );

        String pageJsp = ACCES_CONNEXION;

        AdminForm adminForm = new AdminForm( adminDao );
        PatientForm patientForm = new PatientForm( patientDao );
        MedecinForm medecinForm = new MedecinForm( medecinDao );
        ConsultationForm consultationForm = new ConsultationForm( consultationDao );

        String categorieLogin = request.getParameter( PARAM_LOGIN_RADIO );

        if ( categorieLogin == null ) {
            request.setAttribute( "erreurRadio", "il faut selectionner un bouton radio" );
            this.getServletContext().getRequestDispatcher( pageJsp ).forward( request, response );
        }

        switch ( categorieLogin ) {
        case "admin":
            Admin admin = adminForm.connecterAdmin( request );
            listePatient = patientForm.listerPatients();
            listeMedecin = medecinForm.listerMedecins();
            request.setAttribute( ATT_ADMIN_FORM, adminForm );
            session.setAttribute( ATT_SESSION_ADMIN, admin );
            session.setAttribute( ATT_LISTE_PATIENT, listePatient );
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            if ( adminForm.getErreurs().isEmpty() ) {
                pageJsp = ACCES_ADMIN;
            } else {
                session.setAttribute( ATT_SESSION_ADMIN, null );
            }
            break;
        case "medecin":
            Medecin medecin = medecinForm.connecterMedecin( request );
            medecinForm.remplirInformationsMedecin( medecin );
            listePatient = patientForm.listerPatientsDuMedecin( medecin.getId() );
            listeMedecin = medecinForm.listerMedecins();
            if ( !listePatient.isEmpty() ) {
                listeConsultationPatientMedecin = consultationForm.listerConsultations( request,
                        medecin.getId(), patientForm.listerPatients() );
                session.setAttribute( ATT_LISTE_CONSULTATION_PATIENT_MEDECIN, listeConsultationPatientMedecin );
            }
            session.setAttribute( ATT_SESSION_MEDECIN, medecin );
            session.setAttribute( ATT_LISTE_PATIENT, listePatient );
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            request.setAttribute( ATT_MEDECIN_FORM, medecinForm );
            session.setAttribute( ATT_MEDECIN, medecin );

            if ( medecinForm.getErreurs().isEmpty() ) {
                pageJsp = ACCES_MEDECIN;
            } else {
                session.setAttribute( ATT_SESSION_MEDECIN, null );
            }
            break;
        case "patient":
            Patient patient = patientForm.connecterPatient( request );
            patientForm.remplirInformationsPatient( patient );
            listeConsultation = consultationForm.listerConsultations();
            mapConsultationPatient = consultationForm.mapperConsultationsMedecinsPourUnPatient( patient );
            listeMedecin = medecinForm.listerMedecins();
            mapConsultationsMedecinsPourUnPatient = consultationForm
                    .mapperConsultationsMedecinsPourUnPatient( patient );
            request.setAttribute( ATT_PATIENT_FORM, patientForm );
            request.setAttribute( ATT_CONSULTATION_FORM, consultationForm );
            session.setAttribute( ATT_LISTE_CONSULTATION, listeConsultation );
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            session.setAttribute( ATT_MAP_CONSULTATION_PATIENT, mapConsultationPatient );
            session.setAttribute( ATT_SESSION_PATIENT, patient );
            session.setAttribute( ATT_MAP_CONSULTATION_MEDECINS_POUR_UN_PATIENT,
                    mapConsultationsMedecinsPourUnPatient );

            if ( patientForm.getErreurs().isEmpty() ) {
                pageJsp = ACCES_PATIENT;
            } else {
                session.setAttribute( ATT_SESSION_PATIENT, null );
            }
            break;
        }
        this.getServletContext().getRequestDispatcher( pageJsp ).forward( request, response );

    }

}
