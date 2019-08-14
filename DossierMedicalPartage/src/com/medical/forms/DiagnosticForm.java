package com.medical.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.medical.beans.Diagnostic;
import com.medical.dao.DAOException;
import com.medical.dao.DiagnosticDao;

public class DiagnosticForm {

    /* paramètres diagnostic */
    private static final String PARAM_ID_DIAGNOSTIC_CLIQUE      = "id-diagnostic-clique";
    private static final String PARAM_DESCRIPTIF_DIAGNOSTIC     = "descriptif-diagnostic";
    private static final String PARAM_ALLERGIE_DIAGNOSTIC       = "allergie";
    private static final String PARAM_ANTECEDENT_DIAGNOSTIC     = "antecedent";
    private static final String PARAM_PRESCRIPTION_DIAGNOSTIC   = "prescription";

    /* messages */
    private static final String MSG_SUCCES_MODIF                = "Modification effectuée";

    /* messages */
    private static final String MSG_CLE_ERREUR                  = "imprévu";

    private static final String CHAMP_ID_DIAGNOSTIC             = "idDiagnostic";

    private static final String MSG_ERREUR_CONVERSION_CHAINE_ID = "l'identifiant n'est pas valide (!= nombre)";

    private String              resultat;
    private Map<String, String> erreurs                         = new HashMap<String, String>();

    private DiagnosticDao       diagnosticDao;

    public DiagnosticForm( DiagnosticDao diagnosticDao ) {
        this.diagnosticDao = diagnosticDao;
    }

    public void modifierDiagnostic( HttpServletRequest request ) {

        Diagnostic diagnostic = new Diagnostic();

        String chaineIdDiagnostic = UtilitaireForm.getValeurParametre( request, PARAM_ID_DIAGNOSTIC_CLIQUE );

        String descriptifDiagnostic = request.getParameter( PARAM_DESCRIPTIF_DIAGNOSTIC );
        String allergieDiagnostic = request.getParameter( PARAM_ALLERGIE_DIAGNOSTIC );
        String antecedentDiagnostic = request.getParameter( PARAM_ANTECEDENT_DIAGNOSTIC );

        try {
            traiterIdentifiant( chaineIdDiagnostic, diagnostic );
            diagnostic.setDescriptifDiagnostic( descriptifDiagnostic );
            diagnostic.setAllergiesDiagnostic( allergieDiagnostic );
            diagnostic.setAntecedentsMedicauxDiagnostic( antecedentDiagnostic );

            if ( erreurs.isEmpty() ) {
                diagnosticDao.modifier( diagnostic );
                resultat = MSG_SUCCES_MODIF;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        } catch ( FormValidationException e ) {
            setErreur( MSG_ERREUR_CONVERSION_CHAINE_ID, "l'identifiant n'est pas valide (!= nombre)" );
            e.printStackTrace();
        }
    }

    public void modifierPrescription( HttpServletRequest request ) {

        Diagnostic diagnostic = new Diagnostic();

        String chaineIdDiagnostic = request.getParameter( PARAM_ID_DIAGNOSTIC_CLIQUE );

        String prescriptionDiagnostic = request.getParameter( PARAM_PRESCRIPTION_DIAGNOSTIC );

        try {
            traiterIdentifiant( chaineIdDiagnostic, diagnostic );
            diagnostic.setPrescriptionDiagnostic( prescriptionDiagnostic );

            if ( erreurs.isEmpty() ) {
                diagnosticDao.modifier( diagnostic.getIdDiagnostic(), diagnostic.getPrescriptionDiagnostic() );
                resultat = MSG_SUCCES_MODIF;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        } catch ( FormValidationException e ) {
            setErreur( MSG_ERREUR_CONVERSION_CHAINE_ID, "l'identifiant n'est pas valide (!= nombre)" );
            e.printStackTrace();
        }
    }

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- TRAITEMENTS -------------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    private void traiterIdentifiant( String chaineId, Diagnostic diagnostic ) throws FormValidationException {
        int id = -1;
        try {
            id = Integer.parseInt( chaineId );
        } catch ( NumberFormatException e ) {
            setErreur( CHAMP_ID_DIAGNOSTIC, "Le diagnostic n'existe pas dans la base" );
        }
        diagnostic.setIdDiagnostic( id );
    }

    /*-------------------GETTERS SETTERS -------------------*/

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     * 
     * @param champ
     * @param message
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
