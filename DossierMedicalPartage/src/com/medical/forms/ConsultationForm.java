package com.medical.forms;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.medical.beans.Consultation;
import com.medical.beans.Diagnostic;
import com.medical.beans.Medecin;
import com.medical.beans.Patient;
import com.medical.beans.Utilisateur;
import com.medical.dao.ConsultationDao;
import com.medical.dao.DAOException;

public class ConsultationForm {

    /* paramètres medecins et consultations */
    private static final String PARAM_ID_SELECTED                 = "id-selected";
    private static final String PARAM_ID_MEDECIN_ACTUEL           = "id-medecin-actuel";
    private static final String PARAM_DATE_CONSULTATION_ACTUELLE  = "date-consultation-actuelle";
    private static final String PARAM_HEURE_CONSULTATION_ACTUELLE = "heure-consultation-actuelle";
    private static final String PARAM_DATE_CONSULTATION_MODIF     = "date-consultation-modif";
    private static final String PARAM_HEURE_CONSULTATION_MODIF    = "heure-consultation-modif";

    /* messages */
    private static final String MSG_SUCCES_AJOUT                  = "Ajout effectué";
    private static final String MSG_SUCCES_MODIF                  = "Modification effectuée";
    private static final String MSG_SUCCES_SUPPRESSION            = "Suppression effectuée";

    /* champs consultation */
    private static final String CHAMP_ID                          = "id";
    private static final String CHAMP_HEURE                       = "heure";
    private static final String CHAMP_DATE                        = "date";
    private static final String CHAMP_UNICITE_CONSULTATION        = "consultation";

    /* messages */
    private static final String MSG_CLE_ERREUR                    = "imprévu";
    private static final String MSG_ERREUR_HEURE_NULL             = "merci de selectionner une heure";
    private static final String MSG_ERREUR_DATE_NULL              = "merci de selectionner une date";

    /* Expressions Régulières */
    private static final String REGEX_DATE                        = "[0-9]{4}-[0-1][0-9]-[0-3][0-9]";     // YYYY-MM-DD
    private static final String REGEX_HEURE                       = "([0-9]+):([0-5][0-9]):([0-5][0-9])"; // HH-MM-SS

    private String              resultat;
    private Map<String, String> erreurs                           = new HashMap<String, String>();

    private ConsultationDao     consultationDao;

    public ConsultationForm( ConsultationDao consultationDao ) {
        this.consultationDao = consultationDao;
    }

    /**
     * <b>Objectif</b> : Lister toutes les consultations.
     * 
     * @return Liste de toutes les consultations de la BDD
     */
    public List<Consultation> listerConsultations() {
        return consultationDao.lister();
    }

    /**
     * <b>Objectif</b> : Lister toutes les consultations d'un patient pour un
     * medecin spécifique.
     * 
     * <ul>
     * <li>Traitement de l'identifiant patient</li>
     * </ul>
     * 
     * @param request
     * @param idMedecin
     * @param listePatient
     * @return
     */
    public List<Consultation> listerConsultations( HttpServletRequest request, int idMedecin,
            List<Patient> listePatient ) {

        List<Consultation> listeConsultation = null;

        Consultation consultation = new Consultation();
        String chaineIdPatient = request.getParameter( "id-selected" );

        try {
            traiterIdentifiant( chaineIdPatient, listePatient, consultation );
            consultation.setIdMedecin( idMedecin );

            if ( erreurs.isEmpty() ) {
                listeConsultation = consultationDao.lister( consultation.getIdPatient(), consultation.getIdMedecin() );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
        return listeConsultation;
    }

    /**
     * <b>Objectif</b> : Récupérer la map contenant les medecins et les
     * consultations d'un patient.
     * 
     * @param patient
     * @return
     */
    public Map<Medecin, Consultation> mapperConsultationsMedecinsPourUnPatient( Patient patient ) {
        return consultationDao.mapperConsultationsMedecinsPourUnPatient( patient.getId() );
    }

    /**
     * Objectif : Récupérer un map contenant toutes les consultations et
     * diagnostics associés d'un patient.
     * 
     * <ul>
     * <li>Traitement de l'identifiant patient</li>
     * </ul>
     * 
     * @param request
     * @param idMedecin
     * @param listePatient
     * @return
     */
    public Map<Consultation, Diagnostic> mapperDiagnosticsEtConsultationsPourUnPatient( HttpServletRequest request,
            int idMedecin,
            List<Patient> listePatient ) {

        Map<Consultation, Diagnostic> mapConsultationDiagnostic = null;

        Consultation consultation = new Consultation();
        String chaineIdPatient = UtilitaireForm.getValeurParametre( request, "id-selected" );

        try {
            traiterIdentifiant( chaineIdPatient, listePatient, consultation );
            consultation.setIdMedecin( idMedecin );

            if ( erreurs.isEmpty() ) {
                mapConsultationDiagnostic = consultationDao
                        .mapperDiagnosticsEtConsultationsPatient( consultation.getIdPatient(), idMedecin );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
        return mapConsultationDiagnostic;
    }

    /**
     * <b>Objectif</b> : Récupérer la map contenant toutes les consultations et
     * diagnostics associés d'un patient et d'un medecin.
     * 
     * @param request
     * @param idMedecin
     * @param listePatient
     * @param idPatient
     * @return
     */
    public Map<Consultation, Diagnostic> mapperDiagnosticsEtConsultationsPourUnPatient( HttpServletRequest request,
            int idMedecin,
            List<Patient> listePatient, int idPatient ) {

        Map<Consultation, Diagnostic> mapConsultationDiagnostic = null;

        Consultation consultation = new Consultation();

        try {
            consultation.setIdPatient( idPatient );
            consultation.setIdMedecin( idMedecin );

            if ( erreurs.isEmpty() ) {
                mapConsultationDiagnostic = consultationDao
                        .mapperDiagnosticsEtConsultationsPatient( consultation.getIdPatient(), idMedecin );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
        return mapConsultationDiagnostic;
    }

    /**
     * <b>Objectif</b> : Ajouter une consultation
     * 
     * <ul>
     * <li>Traitement de la date</li>
     * <li>Traitement de l'heure</li>
     * <li>Traitement de l'identifiant Medecin</li>
     * <li>Traitement vérification unicité consultation</li>
     * </ul>
     * 
     * @param request
     * @param patient
     * @param listeMedecin
     */
    public void ajouterConsultation( HttpServletRequest request, Patient patient, List<Medecin> listeMedecin ) {

        Consultation consultation = new Consultation();

        String chaineIdMedecin = UtilitaireForm.getValeurParametre( request, "id-medecin-clique" );
        String chaineDateConsultation = UtilitaireForm.getValeurParametre( request, "date-consultation" );
        String selectedHeureConsultation = UtilitaireForm.getValeurParametre( request, "heure-consultation" );

        String chaineHeureConsultation = UtilitaireForm.getHeureConsultation( selectedHeureConsultation );

        int idPatient = patient.getId();

        try {
            traiterDate( chaineDateConsultation, consultation );
            traiterHeure( chaineHeureConsultation, consultation );
            traiterIdentifiant( chaineIdMedecin, listeMedecin, consultation );
            traiterUniciteConsultation( consultation );

            consultation.setIdPatient( idPatient );

            if ( erreurs.isEmpty() ) {
                consultationDao.ajouter( consultation );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * <b>Objectif</b> : Modification de la consultation
     * 
     * L'id du medecin et la date/heure à modifier sont contenu dans un objet
     * consultation.
     * 
     * Transmission de cette objet consultation + la date/heure actuelle à la
     * DAO.
     * 
     * <ul>
     * <li>Traitement de la date</li>
     * <li>Traitement de l'heure</li>
     * <li>Traitement de l'identifiant Medecin</li>
     * <li>Traitement vérification unicité consultation</li>
     * </ul>
     * 
     * @param request
     * @param patient
     * @param listeMedecin
     */
    public void modifierConsultation( HttpServletRequest request, List<Medecin> listeMedecin ) {

        Consultation consultation = new Consultation();

        String chaineIdSelected = UtilitaireForm.getValeurParametre( request, PARAM_ID_SELECTED );

        String chaineIdMedecin = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_ID_MEDECIN_ACTUEL );
        String chaineDateConsultation = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_DATE_CONSULTATION_ACTUELLE );
        String chaineHeureConsultation = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_HEURE_CONSULTATION_ACTUELLE );

        String chaineDateConsultationModif = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_DATE_CONSULTATION_MODIF );
        String selectedHeureConsultationModif = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_HEURE_CONSULTATION_MODIF );

        String chaineHeureConsultationModif = UtilitaireForm.getHeureConsultation( selectedHeureConsultationModif );

        try {
            // traitement des nouvelles dates
            traiterDate( chaineDateConsultationModif, consultation );
            traiterHeure( chaineHeureConsultationModif, consultation );
            traiterIdentifiant( chaineIdMedecin, listeMedecin, consultation );
            traiterUniciteConsultation( consultation );

            Date dateActuelle = Date.valueOf( chaineDateConsultation );
            Time heureActuelle = Time.valueOf( chaineHeureConsultation );

            if ( erreurs.isEmpty() ) {
                consultationDao.modifier( consultation, dateActuelle, heureActuelle );
                resultat = MSG_SUCCES_MODIF;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * <b>Objectif</b> : Supprimer une consultation
     * <ul>
     * <li>Traitement de la date</li>
     * <li>Traitement de l'heure</li>
     * <li>Traitement de l'identifiant Medecin</li>
     * </ul>
     * 
     * @param request
     * @param listeMedecin
     */
    public void supprimerConsultation( HttpServletRequest request, List<Medecin> listeMedecin ) {
        Consultation consultation = new Consultation();
        String chaineIdSelected = UtilitaireForm.getValeurParametre( request, PARAM_ID_SELECTED );

        String chaineIdMedecin = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_ID_MEDECIN_ACTUEL );
        String chaineDateConsultation = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_DATE_CONSULTATION_ACTUELLE );
        String chaineHeureConsultation = UtilitaireForm.getValeurParametre( request,
                chaineIdSelected + PARAM_HEURE_CONSULTATION_ACTUELLE );

        try {
            traiterDate( chaineDateConsultation, consultation );
            traiterHeure( chaineHeureConsultation, consultation );
            traiterIdentifiant( chaineIdMedecin, listeMedecin, consultation );
            if ( erreurs.isEmpty() ) {
                consultationDao.supprimer( consultation );
                resultat = MSG_SUCCES_SUPPRESSION;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- TRAITEMENTS -------------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    /**
     * <b>Objectif</b> : Traitement d'une chaine contenant la date.
     * 
     * @param chaineDateConsultation
     * @param consultation
     */
    private void traiterDate( String chaineDateConsultation, Consultation consultation ) {
        try {
            validationDate( chaineDateConsultation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_DATE, e.getMessage() );
        }
        consultation.setDateConsultation( Date.valueOf( chaineDateConsultation ) );
    }

    /**
     * <b>Objectif</b> : Traitement d'une chaine contenant l'heure.
     * 
     * @param chaineHeureConsultation
     * @param consultation
     */
    private void traiterHeure( String chaineHeureConsultation, Consultation consultation ) {
        try {
            validationHeure( chaineHeureConsultation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_HEURE, e.getMessage() );
        }
        consultation.setHeureConsultation( Time.valueOf( chaineHeureConsultation ) );
    }

    /**
     * <b>Objectif</b> :Traitement d'un identifiant Utilisateur et ajout dans un
     * objet Consultation
     * 
     * @param chaineIdMedecin
     * @param listeMedecin
     * @param consultation
     */
    private void traiterIdentifiant( String chaineIdUtilisateur, List<? extends Utilisateur> listeUtilisateurs,
            Consultation consultation ) {
        int idUtilisateur = -1;
        Object item = "";
        try {
            idUtilisateur = Integer.parseInt( chaineIdUtilisateur );
            validationId( idUtilisateur, listeUtilisateurs );
            item = listeUtilisateurs.get( 0 );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_ID, e.getMessage() );
        } catch ( NumberFormatException e ) {
            setErreur( CHAMP_ID, "number format exception" );
        }

        if ( item instanceof Patient ) {
            consultation.setIdPatient( idUtilisateur );
        } else if ( item instanceof Medecin ) {
            consultation.setIdMedecin( idUtilisateur );
        } else {
            setErreur( "probleme", "probleme utilisation traitement identifiant" );
        }

    }

    /**
     * <b>Objectif</b> : Traitement de l'unicité d'une consultation.
     * 
     * @param consultation
     */
    private void traiterUniciteConsultation( Consultation consultation ) {
        try {
            validationConsultation( consultation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_UNICITE_CONSULTATION, e.getMessage() );
        }
    }

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- VERIFICATIONS -----------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    /**
     * <b>Objectif</b> : Vérification d'une chaine contenant une date.
     * 
     * <p>
     * Doit respecter ce format : YYYY-MM-DD [via un REGEX]
     * </p>
     * 
     * @param chaineDateConsultation
     * @throws FormValidationException
     */
    private void validationDate( String chaineDateConsultation ) throws FormValidationException {
        if ( chaineDateConsultation != null ) {
            if ( chaineDateConsultation.matches( REGEX_DATE ) ) {
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_DATE_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification d'une chaine contenant une heure.
     * 
     * <p>
     * Doit respecter ce format : HH:MM:SS [via un REGEX]
     * </p>
     * <ul>
     * <li>avec HH compris entre 08 et 18 (heures)</li>
     * <li>avec MM qui est égal à 00 ou 30 (minutes)</li>
     * <li>avec SS toujours égal à 00 (secondes)</li>
     * </ul>
     * 
     * Explication : les horaires du medecin vont de 08h30 à 18h30 (par STEP de
     * 30min)
     * 
     * @param chaineHeureConsultation
     * @throws FormValidationException
     */
    private void validationHeure( String chaineHeureConsultation ) throws FormValidationException {
        if ( chaineHeureConsultation != null ) {
            if ( chaineHeureConsultation.matches( REGEX_HEURE ) ) {
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_HEURE_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification d'un identifiant. Vérifie si l'identifiant
     * est bien contenu dans la liste.
     * 
     * @param id
     * @param listeUtilisateur
     * @throws FormValidationException
     */
    private void validationId( int id, List<? extends Utilisateur> listeUtilisateur )
            throws FormValidationException {

        boolean existenceId = false;
        for ( Utilisateur utilisateur : listeUtilisateur ) {
            if ( utilisateur.getId() == id ) {
                existenceId = true;
                break;
            }
        }
        if ( !existenceId ) {
            throw new FormValidationException( "l'identifiant n'existe pas dans la base" );
        }
    }

    /**
     * <b>Objectif</b> : Vérifie s'il n'y a pas de consultation déjà existence.
     * (avec l'id medecin, la date et l'heure)
     * 
     * Il récupère la liste des consultations dans la base de donnée puis
     * vérifie si la clé primaire est la même. (idMedecin,date,heure)
     * 
     * @param consultation
     * @throws FormValidationException
     */
    private void validationConsultation( Consultation consultation ) throws FormValidationException {

        List<Consultation> listConsultation = consultationDao.lister();

        int idMedecin = consultation.getIdMedecin();
        Date dateConsultation = consultation.getDateConsultation();
        Time heureConsultation = consultation.getHeureConsultation();

        for ( Consultation rdv : listConsultation ) {
            if ( idMedecin == rdv.getIdMedecin()
                    && ( dateConsultation.compareTo( rdv.getDateConsultation() ) == 0 )
                    && ( heureConsultation.compareTo( rdv.getHeureConsultation() ) == 0 ) ) {
                throw new FormValidationException( "Rendez vous déjà pris" );
            }
        }
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
