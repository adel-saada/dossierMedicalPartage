package com.medical.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.medical.beans.Patient;
import com.medical.beans.Utilisateur;
import com.medical.dao.DAOException;
import com.medical.dao.PatientDao;

public class PatientForm extends UtilisateurForm {

    public static final String  PARAM_LOGIN                  = "login-username";
    public static final String  PARAM_PASS                   = "login-password";

    /* Champ en commun (patient ou medecin) */
    private static final String CHAMP_ID                     = "id";
    private static final String CHAMP_EMAIL                  = "email";
    private static final String CHAMP_TELEPHONE              = "telephone";
    private static final String CHAMP_PRENOM                 = "prenom";
    private static final String CHAMP_NOM                    = "nom";
    private static final String CHAMP_RUE                    = "rue";
    private static final String CHAMP_VILLE                  = "ville";
    private static final String CHAMP_CODE_POSTAL            = "codePostal";

    /* Paramètre Patient */
    private static final String PARAM_ID_PATIENT             = "id-patient";
    private static final String PARAM_NUMERO_SS              = "numeroSS";
    private static final String PARAM_NOM_PATIENT            = "nom-patient";
    private static final String PARAM_PRENOM_PATIENT         = "prenom-patient";
    private static final String PARAM_TEL_PATIENT            = "tel-patient";
    private static final String PARAM_SEXE_PATIENT           = "sexe";
    private static final String PARAM_SEXE_HOMME_PATIENT     = "sexeH";
    private static final String PARAM_EMAIL_PATIENT          = "email-patient";
    private static final String PARAM_VILLE_PATIENT          = "ville-patient";
    private static final String PARAM_ADRESSE_PATIENT        = "adresse-patient";
    private static final String PARAM_CP_PATIENT             = "cp-patient";
    private static final String CHAMP_NUMERO_SS              = "numeroSS";

    /* messages */

    private static final String MSG_SUCCES_AJOUT             = "Ajout effectué";
    private static final String MSG_SUCCES_MODIF             = "Modification effectuée";
    private static final String MSG_SUCCES_SUPPRESSION       = "Suppression effectuée";

    private static final String MSG_CLE_ERREUR               = "imprévu";
    private static final String MSG_CONNEXION_VALIDE         = "Connexion validée";
    private static final String MSG_ERREUR_CONNEXION_PATIENT = "Erreur de connexion patient";

    /* Messages Erreur Patient */
    private static final String MSG_ERREUR_NUM_SECU_MIN      = "Le numero de sécurité social doit contenir 15 chiffres";

    private String              resultat;
    private Map<String, String> erreurs                      = new HashMap<String, String>();

    private PatientDao          patientDao;

    public PatientForm( PatientDao patientDao ) {
        this.patientDao = patientDao;
    }

    /**
     * <b>Objectif</b> : Vérifie si le patient saisie dans le formulaire existe
     * 
     * <p>
     * Si le login/pass existe : Remplir le Patient avec les informations
     * complètes contenues dans la BDD
     * </p>
     * dans la BDD
     * 
     * @param request
     * @return booleen
     */
    public Patient connecterPatient( HttpServletRequest request ) {

        String login = UtilitaireForm.getValeurParametre( request, PARAM_LOGIN );
        String motDePasse = UtilitaireForm.getValeurParametre( request, PARAM_PASS );

        Patient patient = new Patient();
        patient.setLogin( login );
        patient.setMotDePasse( motDePasse );

        boolean estValide = patientDao.estValideIdentification( patient );
        if ( estValide ) {
            resultat = MSG_CONNEXION_VALIDE;
        } else {
            resultat = MSG_ERREUR_CONNEXION_PATIENT;
            setErreur( PARAM_LOGIN, "Login ou mot de passe invalide" );
        }
        return patient;
    }

    /**
     * <b>Objectif</b> : Remplir l'objet Patient(via login) par les informations
     * complètes
     * 
     * @param patient
     *            (ne contenant que login et pass)
     */
    public void remplirInformationsPatient( Patient patient ) {
        patientDao.recuperer( patient );
    }

    public Patient remplirInformationsPatientParId( int idPatient ) {
        return patientDao.recuperer( idPatient );
    }

    /**
     * <b>Objectif</b> : Retourner la liste des patients
     */
    public List<Patient> listerPatients() {
        List<Patient> listePatient = patientDao.lister();
        return listePatient;
    }

    public List<Patient> listerPatientsDuMedecin( int idMedecin ) {
        return patientDao.lister( idMedecin );
    }

    /**
     * <b>Objectif</b> : Ajouter un patient
     * 
     * <ul>
     * <li>Traitement de l'email patient</li>
     * <li>Traitement du telephone patient</li>
     * <li>Traitement du nom patient</li>
     * <li>Traitement du prenom patient</li>
     * <li>Traitement de la rue du patient</li>
     * <li>Traitement du numero de sécurité sociale du patient</li>
     * <li>Traitement de la ville du patient</li>
     * <li>Traitement du code postal patient</li>
     * <li>Traitement du sexe patient</li>
     * </ul>
     * 
     * @param request
     * @param listePatient
     */
    public void ajouterPatient( HttpServletRequest request, List<Patient> listePatient ) {

        Patient patient = new Patient();

        String numeroSS = UtilitaireForm.getValeurParametre( request, PARAM_NUMERO_SS );
        String nom = UtilitaireForm.getValeurParametre( request, PARAM_NOM_PATIENT );
        String prenom = UtilitaireForm.getValeurParametre( request, PARAM_PRENOM_PATIENT );
        String tel = UtilitaireForm.getValeurParametre( request, PARAM_TEL_PATIENT );
        String idSexe = UtilitaireForm.getValeurParametre( request, PARAM_SEXE_PATIENT );
        char sexe = 'F';
        if ( idSexe.equalsIgnoreCase( PARAM_SEXE_HOMME_PATIENT ) ) {
            sexe = 'H';
        }
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_PATIENT );
        String ville = UtilitaireForm.getValeurParametre( request, PARAM_VILLE_PATIENT );
        String rue = UtilitaireForm.getValeurParametre( request, PARAM_ADRESSE_PATIENT );
        String codePostal = UtilitaireForm.getValeurParametre( request, PARAM_CP_PATIENT );

        try {
            traiterEmail( email, patient, listePatient );
            traiterTelephone( tel, patient );
            traiterNom( nom, patient );
            traiterPrenom( prenom, patient );
            traiterRue( rue, patient );
            traiterNumeroSS( numeroSS, patient );
            traiterVille( ville, patient );
            traiterCodePostal( codePostal, patient );
            patient.setSexe( sexe );

            // le login est égal à nom+dernier Id, pour que le champ soit
            // unique.
            // on récupère le dernier id de la liste patient, on l'incremente et
            // on le concatene avec le nom.
            int dernierId = listePatient.get( listePatient.size() - 1 ).getId();

            patient.setLogin( patient.getNom() + ( dernierId + 1 ) );

            if ( erreurs.isEmpty() ) {
                patientDao.ajouter( patient );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * <b>Objectif</b> : modifier un patient
     * 
     * <ul>
     * <li>Traitement de l'identifiant patient</li>
     * <li>Traitement de l'email patient</li>
     * <li>Traitement du telephone patient</li>
     * <li>Traitement du nom patient</li>
     * <li>Traitement du prenom patient</li>
     * <li>Traitement de la rue du patient</li>
     * <li>Traitement du numero de sécurité sociale du patient</li>
     * <li>Traitement de la ville du patient</li>
     * <li>Traitement du code postal patient</li>
     * <li>Traitement du sexe patient</li>
     * </ul>
     * 
     * @param request
     * @param listePatient
     */
    public void modifierPatient( HttpServletRequest request, List<Patient> listePatient ) {

        Patient patient = new Patient();

        String chaineIdPatient = UtilitaireForm.getValeurParametre( request, PARAM_ID_PATIENT );

        String numeroSS = UtilitaireForm.getValeurParametre( request, PARAM_NUMERO_SS );
        String nom = UtilitaireForm.getValeurParametre( request, PARAM_NOM_PATIENT );
        String prenom = UtilitaireForm.getValeurParametre( request, PARAM_PRENOM_PATIENT );
        String tel = UtilitaireForm.getValeurParametre( request, PARAM_TEL_PATIENT );
        String idSexe = UtilitaireForm.getValeurParametre( request, PARAM_SEXE_PATIENT );
        char sexe = 'F';
        if ( idSexe.equalsIgnoreCase( PARAM_SEXE_HOMME_PATIENT ) ) {
            sexe = 'H';
        }
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_PATIENT );
        String ville = UtilitaireForm.getValeurParametre( request, PARAM_VILLE_PATIENT );
        String rue = UtilitaireForm.getValeurParametre( request, PARAM_ADRESSE_PATIENT );
        String codePostal = UtilitaireForm.getValeurParametre( request, PARAM_CP_PATIENT );

        try {
            traiterIdentifiant( chaineIdPatient, patient, listePatient );
            traiterEmail( email, patient );
            traiterTelephone( tel, patient );
            traiterNom( nom, patient );
            traiterPrenom( prenom, patient );
            traiterRue( rue, patient );
            traiterNumeroSS( numeroSS, patient );
            traiterVille( ville, patient );
            traiterCodePostal( codePostal, patient );
            patient.setSexe( sexe );

            if ( erreurs.isEmpty() ) {
                patientDao.modifier( patient );
                resultat = MSG_SUCCES_MODIF;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * <b>Objectif</b> : Supprimer un patient
     * 
     * <ul>
     * <li>Traitement de l'identifiant patient</li>
     * </ul>
     * 
     * @param request
     * @param listePatient
     */
    public void supprimerPatient( HttpServletRequest request, List<Patient> listePatient ) {
        String chaineIdPatient = UtilitaireForm.getValeurParametre( request, PARAM_ID_PATIENT );
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_PATIENT );
        try {
            int identifiant = traiterIdentifiant( chaineIdPatient, email, listePatient );

            if ( erreurs.isEmpty() ) {
                patientDao.supprimer( identifiant );
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

    @Override
    protected void traiterIdentifiant( String chaineId, Utilisateur utilisateur,
            List<? extends Utilisateur> listeUtilisateur ) {
        int id = -1;
        try {
            id = Integer.parseInt( chaineId );
            validationId( id, listeUtilisateur );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_ID, e.getMessage() );
        } catch ( NumberFormatException e ) {
            setErreur( CHAMP_ID, "L'Utilisateur n'existe pas dans la base" );
        }
        utilisateur.setId( id );
    }

    @Override
    protected int traiterIdentifiant( String chaineId, String email,
            List<? extends Utilisateur> listeUtilisateur ) {
        int id = -1;
        try {
            id = Integer.parseInt( chaineId );
            validationId( id, email, listeUtilisateur );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_ID, e.getMessage() );
        } catch ( NumberFormatException e ) {
            setErreur( CHAMP_ID, "L'Utilisateur n'existe pas dans la base" );
        }
        return id;
    }

    @Override
    protected void traiterEmail( String email, Utilisateur utilisateur ) {
        try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }

    @Override
    protected void traiterEmail( String email, Utilisateur utilisateur, List<? extends Utilisateur> listeUtilisateur ) {
        try {
            validationEmail( email, listeUtilisateur );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
    }

    @Override
    protected void traiterTelephone( String tel, Utilisateur utilisateur ) {
        try {
            validationTelephone( tel );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_TELEPHONE, e.getMessage() );
        }
        utilisateur.setTel( tel );
    }

    @Override
    protected void traiterNom( String nom, Utilisateur utilisateur ) {
        try {
            validationNom( nom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NOM, e.getMessage() );
        }
        utilisateur.setNom( nom );
    }

    @Override
    protected void traiterPrenom( String prenom, Utilisateur utilisateur ) {
        try {
            validationPrenom( prenom );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_PRENOM, e.getMessage() );
        }
        utilisateur.setPrenom( prenom );
    }

    @Override
    protected void traiterRue( String rue, Utilisateur utilisateur ) {
        try {
            validationRue( rue );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_RUE, e.getMessage() );
        }
        utilisateur.setRue( rue );
    }

    @Override
    protected void traiterVille( String ville, Utilisateur utilisateur ) {
        try {
            validationVille( ville );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_VILLE, e.getMessage() );
        }
        utilisateur.setVille( ville );
    }

    @Override
    protected void traiterCodePostal( String cp, Utilisateur utilisateur ) {
        try {
            validationCodePostal( cp );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_CODE_POSTAL, e.getMessage() );
        }
        utilisateur.setCodePostal( cp );
    }

    private void traiterNumeroSS( String numeroSS, Patient patient ) {
        try {
            validationNumeroSS( numeroSS );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NUMERO_SS, e.getMessage() );
        }
        patient.setNumeroSS( numeroSS );
    }

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- VERIFICATIONS -----------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    private void validationNumeroSS( String numeroSS ) throws FormValidationException {
        if ( numeroSS != null && numeroSS.length() != 15 ) {
            throw new FormValidationException( MSG_ERREUR_NUM_SECU_MIN );
        }
    }

    /*-------------------GETTERS SETTERS -------------------*/

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

}
