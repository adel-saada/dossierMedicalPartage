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
import com.medical.beans.Diagnostic;
import com.medical.beans.Medecin;
import com.medical.beans.Patient;
import com.medical.dao.ConsultationDao;
import com.medical.dao.DaoFactory;
import com.medical.dao.DiagnosticDao;
import com.medical.dao.MedecinDao;
import com.medical.dao.PatientDao;
import com.medical.forms.ConsultationForm;
import com.medical.forms.DiagnosticForm;
import com.medical.forms.MedecinForm;
import com.medical.forms.PatientForm;

@WebServlet( urlPatterns = "/PanelMedecin" )
public class MedecinPanel extends HttpServlet {

    private static final long             serialVersionUID                           = -2342194797822088718L;

    public static final String            CONF_DAO_FACTORY                           = "daofactory";

    public static final String            ATT_SESSION_MEDECIN                        = "sessionMedecin";
    private static final String           ATT_LISTE_MEDECIN                          = "listeMedecin";

    private static final String           ATT_DIAGNOSTIC_FORM                        = "diagnosticForm";

    public static final String            ATT_PATIENT                                = "patient";
    public static final String            ATT_PATIENT_FORM                           = "patientForm";
    private static final String           ATT_LISTE_PATIENT                          = "listePatient";

    public static final String            ACCES_CONNEXION                            = "/WEB-INF/connexion.jsp";

    public static final String            ACCES_MEDECIN                              = "/panelMedecin/medecin.jsp";
    public static final String            ACCES_MEDECIN_2                            = "/panelMedecin/medecin2.jsp";
    private static final String           ACCES_MEDECIN_3                            = "/panelMedecin/medecin3.jsp";

    private static final String           STR_CONSULTATION                           = "Consultation";
    private static final String           STR_MODIFICATION_DIAGNOSTIC                = "modifierDiagnostic";

    private static final String           ATT_MAP_CONSULTATION_DIAGN_PATIENT_MEDECIN = "mapConsulDiagPatientMedecin";

    private static final String           STR_MODIFICATION_PRESCRIPTION              = "modifierPrescription";

    private List<Medecin>                 listeMedecin;
    private List<Patient>                 listePatient;
    private Map<Consultation, Diagnostic> mapConsulDiagPatientMedecin;

    private PatientDao                    patientDao;
    private MedecinDao                    medecinDao;
    private ConsultationDao               consultationDao;
    private DiagnosticDao                 diagnosticDao;

    private Medecin                       medecin;
    private Patient                       patient;

    @Override
    public void init() throws ServletException {
        this.patientDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPatientDao();
        this.medecinDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMedecinDao();
        this.consultationDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getConsultationDao();
        this.diagnosticDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
                .getDiagnosticDao();

    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_MEDECIN ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_MEDECIN ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        }
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        PatientForm patientForm = new PatientForm( patientDao );
        MedecinForm medecinForm = new MedecinForm( medecinDao );
        ConsultationForm consultationForm = new ConsultationForm( consultationDao );
        DiagnosticForm diagnosticForm = new DiagnosticForm( diagnosticDao );

        String pageJsp = ACCES_CONNEXION;

        listeMedecin = medecinForm.listerMedecins();

        HttpSession session = request.getSession( true );

        request.setAttribute( ATT_PATIENT_FORM, patientForm );

        String action = request.getParameter( "action" );
        String page = request.getParameter( "page" );
        if ( page.equalsIgnoreCase( "1" ) ) {
            pageJsp = ACCES_MEDECIN;
        } else if ( page.equalsIgnoreCase( "2" ) ) {
            pageJsp = ACCES_MEDECIN_2;
        } else if ( page.equalsIgnoreCase( "3" ) ) {
            pageJsp = ACCES_MEDECIN_3;
        }

        medecin = (Medecin) session.getAttribute( ATT_SESSION_MEDECIN );
        if ( medecin == null ) {
            action = "";
        }

        listePatient = patientForm.listerPatientsDuMedecin( medecin.getId() );

        switch ( action ) {
        case STR_CONSULTATION:
            mapConsulDiagPatientMedecin = consultationForm.mapperDiagnosticsEtConsultationsPourUnPatient( request,
                    medecin.getId(), patientForm.listerPatients() );
            if ( mapConsulDiagPatientMedecin != null ) {
                int idPatient = Integer.parseInt( request.getParameter( "id-selected" ) );
                session.setAttribute( "patientSelection", patientForm.remplirInformationsPatientParId( idPatient ) );
            }
            break;
        case STR_MODIFICATION_DIAGNOSTIC:
            diagnosticForm.modifierDiagnostic( request );
            patient = (Patient) session.getAttribute( "patientSelection" );
            mapConsulDiagPatientMedecin = consultationForm.mapperDiagnosticsEtConsultationsPourUnPatient( request,
                    medecin.getId(), patientForm.listerPatients(), patient.getId() );

            pageJsp = ACCES_MEDECIN_2;
            break;
        case STR_MODIFICATION_PRESCRIPTION:
            diagnosticForm.modifierPrescription( request );
            patient = (Patient) session.getAttribute( "patientSelection" );
            mapConsulDiagPatientMedecin = consultationForm.mapperDiagnosticsEtConsultationsPourUnPatient( request,
                    medecin.getId(), patientForm.listerPatients(), patient.getId() );
            pageJsp = ACCES_MEDECIN_3;
            break;
        }

        if ( session.getAttribute( ATT_SESSION_MEDECIN ) != null ) {
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            session.setAttribute( ATT_LISTE_PATIENT, listePatient );
            session.setAttribute( ATT_DIAGNOSTIC_FORM, diagnosticForm );
            session.setAttribute( ATT_MAP_CONSULTATION_DIAGN_PATIENT_MEDECIN, mapConsulDiagPatientMedecin );
        }

        this.getServletContext().getRequestDispatcher( pageJsp ).forward( request, response );

    }

}
