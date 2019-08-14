package com.medical.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.medical.beans.Medecin;
import com.medical.beans.Patient;
import com.medical.dao.AdminDao;
import com.medical.dao.DaoFactory;
import com.medical.dao.MedecinDao;
import com.medical.dao.PatientDao;
import com.medical.forms.AdminForm;
import com.medical.forms.MedecinForm;
import com.medical.forms.PatientForm;

@WebServlet( urlPatterns = "/PanelAdmin" )
public class AdminPanel extends HttpServlet {

    private static final long   serialVersionUID      = 7822049139741181258L;

    public static final String  CONF_DAO_FACTORY      = "daofactory";

    public static final String  ATT_SESSION_ADMIN     = "sessionAdmin";

    public static final String  ATT_ADMIN_FORM        = "adminForm";
    public static final String  ATT_ADMIN             = "admin";
    public static final String  ATT_LISTE_PATIENT     = "listePatient";
    private static final String ATT_LISTE_MEDECIN     = "listeMedecin";

    public static final String  ACCES_CONNEXION       = "/WEB-INF/connexion.jsp";
    public static final String  ACCES_ADMIN           = "/restreint/admin.jsp";
    public static final String  ACCES_ADMIN_2         = "/restreint/admin2.jsp";

    public static final String  STR_AJOUTER_PATIENT   = "ajouterPatient";
    public static final String  STR_MODIFIER_PATIENT  = "modifierPatient";
    public static final String  STR_SUPPRIMER_PATIENT = "supprimerPatient";

    public static final String  STR_AJOUTER_MEDECIN   = "ajouterMedecin";
    public static final String  STR_MODIFIER_MEDECIN  = "modifierMedecin";
    public static final String  STR_SUPPRIMER_MEDECIN = "supprimerMedecin";

    public static final String  ATT_PATIENT_FORM      = "patientForm";
    public static final String  ATT_MEDECIN_FORM      = "medecinForm";

    private AdminDao            adminDao;
    private PatientDao          patientDao;
    private MedecinDao          medecinDao;

    @Override
    public void init() throws ServletException {
        this.adminDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getAdminDao();
        this.patientDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPatientDao();
        this.medecinDao = ( (DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getMedecinDao();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ( session.getAttribute( ATT_SESSION_ADMIN ) != null ) {
            this.getServletContext().getRequestDispatcher( ACCES_ADMIN ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        }

    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        AdminForm adminForm = new AdminForm( adminDao );
        PatientForm patientForm = new PatientForm( patientDao );
        MedecinForm medecinForm = new MedecinForm( medecinDao );

        List<Patient> listePatient = patientForm.listerPatients();
        List<Medecin> listeMedecin = medecinForm.listerMedecins();

        String action = request.getParameter( "action" );

        switch ( action ) {
        case STR_AJOUTER_PATIENT:
            patientForm.ajouterPatient( request, listePatient );
            break;
        case STR_AJOUTER_MEDECIN:
            medecinForm.ajouterMedecin( request, listeMedecin );
            break;
        case STR_MODIFIER_PATIENT:
            patientForm.modifierPatient( request, listePatient );
            break;
        case STR_MODIFIER_MEDECIN:
            medecinForm.modifierMedecin( request, listeMedecin );
            break;
        case STR_SUPPRIMER_PATIENT:
            patientForm.supprimerPatient( request, listePatient );
            break;
        case STR_SUPPRIMER_MEDECIN:
            medecinForm.supprimerMedecin( request, listeMedecin );
            break;
        }

        // récupérer la liste actualisée (action effectuée quoi qu'il arrive)
        listePatient = patientForm.listerPatients();
        listeMedecin = medecinForm.listerMedecins();

        request.setAttribute( ATT_ADMIN_FORM, adminForm );
        request.setAttribute( ATT_PATIENT_FORM, patientForm );
        request.setAttribute( ATT_MEDECIN_FORM, medecinForm );

        HttpSession session = request.getSession( true );

        if ( session.getAttribute( ATT_SESSION_ADMIN ) == null ) {
            this.getServletContext().getRequestDispatcher( ACCES_CONNEXION ).forward( request, response );
        } else if ( action.endsWith( "Patient" ) ) {
            session.setAttribute( ATT_LISTE_PATIENT, listePatient );
            this.getServletContext().getRequestDispatcher( ACCES_ADMIN ).forward( request, response );
        } else if ( action.endsWith( "Medecin" ) ) {
            session.setAttribute( ATT_LISTE_MEDECIN, listeMedecin );
            this.getServletContext().getRequestDispatcher( ACCES_ADMIN_2 ).forward( request, response );
        }

    }

}
