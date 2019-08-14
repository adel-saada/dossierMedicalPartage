package com.medical.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.medical.beans.Medecin;
import com.medical.beans.Utilisateur;
import com.medical.dao.DAOException;
import com.medical.dao.MedecinDao;

public class MedecinForm extends UtilisateurForm {

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
    private static final String CHAMP_SPECIALITE             = "specialite";

    /* Paramètre Medecin */
    private static final String PARAM_ID_MEDECIN             = "id-medecin";
    private static final String PARAM_NOM_MEDECIN            = "nom-medecin";
    private static final String PARAM_PRENOM_MEDECIN         = "prenom-medecin";
    private static final String PARAM_SPECIALITE             = "specialiste-select";

    private static final String PARAM_TEL_MEDECIN            = "tel-medecin";
    private static final String PARAM_EMAIL_MEDECIN          = "email-medecin";
    private static final String PARAM_VILLE_MEDECIN          = "ville-medecin";
    private static final String PARAM_ADRESSE_MEDECIN        = "adresse-medecin";
    private static final String PARAM_CP_MEDECIN             = "cp-medecin";

    /* messages */
    private static final String MSG_SUCCES_AJOUT             = "Ajout effectué";
    private static final String MSG_SUCCES_MODIF             = "Modification effectuée";
    private static final String MSG_SUCCES_SUPPRESSION       = "Suppression effectuée";

    private static final String MSG_CLE_ERREUR               = "imprévu";
    private static final String MSG_CONNEXION_VALIDE         = "Connexion validée";
    private static final String MSG_ERREUR_CONNEXION_MEDECIN = "Erreur de connexion medecin";

    private String              resultat;
    private Map<String, String> erreurs                      = new HashMap<String, String>();

    private MedecinDao          medecinDao;

    public MedecinForm( MedecinDao medecinDao ) {
        this.medecinDao = medecinDao;
    }

    /**
     * <b>Objectif</b> : Vérifie si le medecin saisie dans le formulaire existe
     * dans la BDD
     * 
     * @param request
     * @return booleen
     */
    public Medecin connecterMedecin( HttpServletRequest request ) {

        String login = UtilitaireForm.getValeurParametre( request, PARAM_LOGIN );
        String motDePasse = UtilitaireForm.getValeurParametre( request, PARAM_PASS );

        Medecin medecin = new Medecin();
        medecin.setLogin( login );
        medecin.setMotDePasse( motDePasse );

        boolean estValide = medecinDao.estValideIdentification( medecin );
        if ( estValide ) {
            resultat = MSG_CONNEXION_VALIDE;
        } else {
            resultat = MSG_ERREUR_CONNEXION_MEDECIN;
            setErreur( PARAM_LOGIN, "Login ou mot de passe invalide" );
        }
        return medecin;
    }

    /**
     * <b>Objectif</b> : Remplir l'objet Medecin(via login) par les informations
     * complètes
     * 
     * @param medecin
     *            (ne contenant que login et pass)
     */
    public void remplirInformationsMedecin( Medecin medecin ) {
        medecinDao.recuperer( medecin );
    }

    /**
     * <b>Objectif</b> : Remplir un objet medecin correspondant à l'id medecin
     * transmis(en paramètre) et le retourner.
     * 
     * @param idMedecin
     * @return Medecin
     */
    public Medecin remplirInformationsMedecinParId( int idMedecin ) {
        return medecinDao.recuperer( idMedecin );
    }

    /**
     * <b>Objectif</b> : Retourne la liste des médécins contenu dans la BDD
     */
    public List<Medecin> listerMedecins() {
        return medecinDao.lister();
    }

    /**
     * <b>Objectif</b> : Ajouter un medecin
     * 
     * <ul>
     * <li>Traitement de l'email medecin</li>
     * <li>Traitement du telephone medecin</li>
     * <li>Traitement du nom medecin</li>
     * <li>Traitement du prenom medecin</li>
     * <li>Traitement de la rue du medecin</li>
     * <li>Traitement de la ville du medecin</li>
     * <li>Traitement du code postal medecin</li>
     * <li>Traitement de la specialite medecin</li>
     * </ul>
     * 
     * @param request
     * @param listeMedecin
     */
    public void ajouterMedecin( HttpServletRequest request, List<Medecin> listeMedecin ) {

        Medecin medecin = new Medecin();

        String nom = UtilitaireForm.getValeurParametre( request, PARAM_NOM_MEDECIN );
        String prenom = UtilitaireForm.getValeurParametre( request, PARAM_PRENOM_MEDECIN );

        String selectedMedecin = UtilitaireForm.getValeurParametre( request, PARAM_SPECIALITE );

        String tel = UtilitaireForm.getValeurParametre( request, PARAM_TEL_MEDECIN );
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_MEDECIN );
        String ville = UtilitaireForm.getValeurParametre( request, PARAM_VILLE_MEDECIN );
        String rue = UtilitaireForm.getValeurParametre( request, PARAM_ADRESSE_MEDECIN );
        String codePostal = UtilitaireForm.getValeurParametre( request, PARAM_CP_MEDECIN );

        try {
            traiterEmail( email, medecin, listeMedecin );
            traiterTelephone( tel, medecin );
            traiterNom( nom, medecin );
            traiterPrenom( prenom, medecin );
            traiterRue( rue, medecin );
            traiterVille( ville, medecin );
            traiterCodePostal( codePostal, medecin );
            traiterSpecialite( selectedMedecin, medecin );

            // le login est égal à nom+dernier Id, pour que le champ soit
            // unique.
            // on récupère le dernier id de la liste patient, on l'incremente et
            // on le concatene avec le nom.
            int dernierId = listeMedecin.get( listeMedecin.size() - 1 ).getId();

            medecin.setLogin( medecin.getNom() + ( dernierId + 1 ) );

            if ( erreurs.isEmpty() ) {
                medecinDao.ajouter( medecin );
                resultat = MSG_SUCCES_AJOUT;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }

    }

    /**
     * <b>Objectif</b> : Modifier un medecin
     * 
     * <ul>
     * <li>Traitement de l'identifiant medecin</li>
     * <li>Traitement de l'email medecin</li>
     * <li>Traitement du telephone medecin</li>
     * <li>Traitement du nom medecin</li>
     * <li>Traitement du prenom medecin</li>
     * <li>Traitement de la rue du medecin</li>
     * <li>Traitement de la ville du medecin</li>
     * <li>Traitement du code postal medecin</li>
     * <li>Traitement de la specialite medecin</li>
     * 
     * @param request
     * @param listeMedecin
     */
    public void modifierMedecin( HttpServletRequest request, List<Medecin> listeMedecin ) {

        Medecin medecin = new Medecin();

        String chaineIdMedecin = UtilitaireForm.getValeurParametre( request, PARAM_ID_MEDECIN );

        String nom = UtilitaireForm.getValeurParametre( request, PARAM_NOM_MEDECIN );
        String prenom = UtilitaireForm.getValeurParametre( request, PARAM_PRENOM_MEDECIN );

        String selectedMedecin = UtilitaireForm.getValeurParametre( request, PARAM_SPECIALITE );

        String tel = UtilitaireForm.getValeurParametre( request, PARAM_TEL_MEDECIN );
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_MEDECIN );
        String ville = UtilitaireForm.getValeurParametre( request, PARAM_VILLE_MEDECIN );
        String rue = UtilitaireForm.getValeurParametre( request, PARAM_ADRESSE_MEDECIN );
        String codePostal = UtilitaireForm.getValeurParametre( request, PARAM_CP_MEDECIN );

        try {
            traiterIdentifiant( chaineIdMedecin, medecin, listeMedecin );
            traiterEmail( email, medecin );
            traiterTelephone( tel, medecin );
            traiterNom( nom, medecin );
            traiterPrenom( prenom, medecin );
            traiterRue( rue, medecin );
            traiterVille( ville, medecin );
            traiterCodePostal( codePostal, medecin );
            traiterSpecialite( selectedMedecin, medecin );

            if ( erreurs.isEmpty() ) {
                medecinDao.modifier( medecin );
                resultat = MSG_SUCCES_MODIF;
            } else {
            }
        } catch ( DAOException e ) {
            setErreur( MSG_CLE_ERREUR, e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * <b>Objectif</b> : Supprimer un medecin
     * 
     * <ul>
     * <li>Traitement identifiant medecin</li>
     * </ul>
     * 
     * @param request
     * @param listeMedecin
     */
    public void supprimerMedecin( HttpServletRequest request, List<Medecin> listeMedecin ) {
        String chaineIdMedecin = UtilitaireForm.getValeurParametre( request, PARAM_ID_MEDECIN );
        String email = UtilitaireForm.getValeurParametre( request, PARAM_EMAIL_MEDECIN );
        try {
            int identifiant = traiterIdentifiant( chaineIdMedecin, email, listeMedecin );

            if ( erreurs.isEmpty() ) {
                medecinDao.supprimer( identifiant );
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

    /**
     * <b>Objectif</b> : Traitement de la chaine specialite medecin et ajout
     * dans l'objet Medecin.
     * 
     * @param selectedMedecin
     * @param medecin
     */
    private void traiterSpecialite( String selectedMedecin, Medecin medecin ) {
        String specialite = selectedMedecin;
        try {
            specialite = UtilitaireForm.getSpecialite( selectedMedecin );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_SPECIALITE, e.getMessage() );
        }
        medecin.setSpecialite( specialite );

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
