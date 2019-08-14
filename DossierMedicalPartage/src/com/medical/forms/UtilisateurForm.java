package com.medical.forms;

import java.util.List;

import com.medical.beans.Utilisateur;

public abstract class UtilisateurForm {

    /* messages erreurs */
    private static final String MSG_ERREUR_EMAIL_INVALIDE   = "Merci de saisir une adresse mail valide.";
    private static final String MSG_ERREUR_EMAIL_EXISTANT   = "Email déjà existant";
    private static final String MSG_ERREUR_EMAIL_NULL       = "Merci de saisir une adresse mail.";
    private static final String MSG_ERREUR_TEL_INVALIDE     = "Le numéro de téléphone doit uniquement contenir des chiffres.";
    private static final String MSG_ERREUR_TEL_MIN_CHIFFRE  = "Le numéro de téléphone doit contenir au moins 4 chiffres.";
    private static final String MSG_ERREUR_TEL_NULL         = "Merci d'entrer un numéro de téléphone.";
    private static final String MSG_ERREUR_NOM_MIN          = "Le nom doit contenir au moins 2 caractères.";
    private static final String MSG_ERREUR_NOM_NULL         = "Merci d'entrer un nom.";
    private static final String MSG_ERREUR_PRENOM_MIN       = "Le prénom doit contenir au moins 2 caractères.";
    private static final String MSG_ERREUR_RUE_MIN          = "L'adresse doit contenir au moins 10 caractères.";
    private static final String MSG_ERREUR_RUE_NULL         = "Merci d'entrer une adresse.";
    private static final String MSG_ERREUR_VILLE_MIN        = "La ville doit contenir au moins 4 caractères.";
    private static final String MSG_ERREUR_VILLE_NULL       = "Merci d'entrer une ville.";
    private static final String MSG_ERREUR_CODE_POSTAL_MIN  = "Le code postal doit contenir 5 chiffres.";
    private static final String MSG_ERREUR_CODE_POSTAL_NULL = "Merci d'entrer un code postal.";

    /* Expressions Régulières */

    private static final String REGEX_EMAIL                 = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)";
    private static final String REGEX_TELEPHONE             = "^\\d+$";
    private static final String REGEX_CODE_POSTAL           = "[0-9]{5,5}";

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- TRAITEMENTS -------------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    /**
     * <b>Objectif</b> : Traitement de l'identifiant Utilisateur et ajout dans
     * l'objet Utilisateur.
     * 
     * @param chaineId
     * @param utilisateur
     * @param listeUtilisateur
     */
    protected void traiterIdentifiant( String chaineId, Utilisateur utilisateur,
            List<? extends Utilisateur> listeUtilisateur ) {
    }

    /**
     * <b>Objectif</b> : Traitement de l'identifiant Utilisateur. Surcharge de
     * la méthode traiterIdentifiant -> vérifie l'id et l'email(double
     * vérification) -> ne prend pas en paramètre un utilisateur, retourne
     * l'identifiant.
     * 
     * @param chaineId
     * @param email
     * @param listeUtilisateur
     * @return identifiant (int)
     */

    protected abstract int traiterIdentifiant( String chaineId, String email,
            List<? extends Utilisateur> listeUtilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine email et ajout dans l'objet
     * Utilisateur.
     * 
     * (SANS VERIFICATION EXISTENCE email)
     * 
     * @param email
     * @param utilisateur
     */
    protected abstract void traiterEmail( String email, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine email et ajout dans l'objet
     * Utilisateur. (AVEC VERIFICATION EXISTENCE email)
     * 
     * @param email
     * @param utilisateur
     * @param listeUtilisateur
     */
    protected abstract void traiterEmail( String email, Utilisateur utilisateur,
            List<? extends Utilisateur> listeUtilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine téléphone et ajout dans l'objet
     * Utilisateur.
     * 
     * @param tel
     * @param utilisateur
     */
    protected abstract void traiterTelephone( String tel, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine nom et ajout dans l'objet
     * Utilisateur.
     * 
     * @param nom
     * @param utilisateur
     */
    protected abstract void traiterNom( String nom, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de le la chaine prenom et ajout dans l'objet
     * Utilisateur.
     * 
     * @param prenom
     * @param utilisateur
     */
    protected abstract void traiterPrenom( String prenom, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine rue et ajout dans l'objet
     * Utilisateur.
     * 
     * @param rue
     * @param utilisateur
     */
    protected abstract void traiterRue( String rue, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine ville et ajout dans l'objet
     * Utilisateur.
     * 
     * @param ville
     * @param utilisateur
     */
    protected abstract void traiterVille( String ville, Utilisateur utilisateur );

    /**
     * <b>Objectif</b> : Traitement de la chaine Code Postal et ajout dans
     * l'objet Utilisateur.
     * 
     * @param cp
     * @param utilisateur
     */
    protected abstract void traiterCodePostal( String cp, Utilisateur utilisateur );

    /*---------------------------------------------------------------------------------------*/
    /*------------------------------------- VERIFICATIONS -----------------------------------*/
    /*---------------------------------------------------------------------------------------*/

    /**
     * <b>Objectif</b> : Validation de l'identifiant, vérification existence
     * dans la liste Utilisateur.
     * 
     * @param id
     * @param listeUtilisateur
     * @throws FormValidationException
     */
    protected void validationId( int id, List<? extends Utilisateur> listeUtilisateur )
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
     * <b>Objectif</b> : Validation de l'identifiant, verification existence
     * dans la liste Utilisateur (avec une double vérification sur l'email)
     * 
     * Détail : (surcharge)
     * 
     * @param id
     * @param email
     * @param listeUtilisateur
     * @throws FormValidationException
     */
    protected void validationId( int id, String email, List<? extends Utilisateur> listeUtilisateur )
            throws FormValidationException {

        if ( listeUtilisateur.isEmpty() ) {
            throw new FormValidationException( "La liste " + listeUtilisateur.getClass().getName() + " est vide" );
        }

        boolean existenceId = false;
        for ( Utilisateur utilisateur : listeUtilisateur ) {
            if ( utilisateur.getId() == id && utilisateur.getEmail().equals( email ) ) {
                existenceId = true;
                break;
            }
        }
        if ( !existenceId ) {
            throw new FormValidationException( "le couple identifiant/email n'existe pas dans la base" );
        }
    }

    /**
     * <b>Objectif</b> : Vérification d'une chaine contenant l'email (SANS
     * vérification existence BDD)
     * 
     * <p>
     * Doit respecter ce format : ([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)
     * [REGEX] exemple : "a.monemail@eded.fr".
     * </p>
     * 
     * @param email
     * @throws FormValidationException
     */
    protected void validationEmail( String email )
            throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( REGEX_EMAIL ) ) {
                throw new FormValidationException( MSG_ERREUR_EMAIL_INVALIDE );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_EMAIL_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification d'une chaine contenant l'email (AVEC
     * vérification existence BDD)
     * 
     * <p>
     * Doit respecter ce format : ([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)
     * [REGEX] exemple : "a.monemail@eded.fr"
     * </p>
     * 
     * @param email
     * @param listeUtilisateur
     * @throws FormValidationException
     */
    protected void validationEmail( String email, List<? extends Utilisateur> listeUtilisateur )
            throws FormValidationException {
        if ( email != null ) {
            if ( !email.matches( REGEX_EMAIL ) ) {
                throw new FormValidationException( MSG_ERREUR_EMAIL_INVALIDE );
            }

            for ( Utilisateur utilisateur : listeUtilisateur ) {
                if ( utilisateur.getEmail().equals( email ) ) {
                    throw new FormValidationException( MSG_ERREUR_EMAIL_EXISTANT );
                }
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_EMAIL_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine téléphone
     * 
     * <p>
     * Doit respecter ce format : [0-9] [REGEX]
     * </p>
     * <p>
     * Doit contenir au moins 10 chiffres. XX-XX-XX-XX-XX
     * </p>
     * 
     * @param telephone
     * @throws FormValidationException
     */
    protected void validationTelephone( String telephone ) throws FormValidationException {
        if ( telephone != null ) {
            if ( !telephone.replaceAll( " ", "" ).matches( REGEX_TELEPHONE ) ) {
                throw new FormValidationException( MSG_ERREUR_TEL_INVALIDE );
            } else if ( telephone.replaceAll( " ", "" ).length() < 10 ) {
                throw new FormValidationException( MSG_ERREUR_TEL_MIN_CHIFFRE );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_TEL_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine nom
     * 
     * <p>
     * Doit contenir au moins 2 caractères.
     * </p>
     * 
     * @param nom
     * @throws FormValidationException
     */
    protected void validationNom( String nom ) throws FormValidationException {
        if ( nom != null ) {
            if ( nom.length() < 2 ) {
                throw new FormValidationException( MSG_ERREUR_NOM_MIN );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_NOM_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine prenom
     * 
     * <p>
     * Doit contenir au moins 2 caractères
     * </p>
     * 
     * @param prenom
     * @throws FormValidationException
     */
    protected void validationPrenom( String prenom ) throws FormValidationException {
        if ( prenom != null && prenom.length() < 2 ) {
            throw new FormValidationException( MSG_ERREUR_PRENOM_MIN );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine rue.
     * 
     * <p>
     * Doit contenir au moins 10 caractères
     * </p>
     * 
     * @param rue
     * @throws FormValidationException
     */
    protected void validationRue( String rue ) throws FormValidationException {
        if ( rue != null ) {
            if ( rue.length() < 10 ) {
                throw new FormValidationException( MSG_ERREUR_RUE_MIN );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_RUE_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine ville.
     * 
     * <p>
     * Doit contenir au moins 4 caractères.
     * </p>
     * 
     * @param ville
     * @throws FormValidationException
     */
    protected void validationVille( String ville ) throws FormValidationException {
        if ( ville != null ) {
            if ( ville.length() < 4 ) {
                throw new FormValidationException( MSG_ERREUR_VILLE_MIN );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_VILLE_NULL );
        }
    }

    /**
     * <b>Objectif</b> : Vérification de la chaine codePostal
     * 
     * <p>
     * Doit respecter ce format : XXXXX avec X nombre allant de 0 à 9.
     * </p>
     * <p>
     * [0-9]{5,5} [REGEX]
     * 
     * @param cp
     * @throws FormValidationException
     */
    protected void validationCodePostal( String cp ) throws FormValidationException {
        if ( cp != null ) {
            if ( !cp.trim().matches( REGEX_CODE_POSTAL ) ) {
                throw new FormValidationException( MSG_ERREUR_CODE_POSTAL_MIN );
            }
        } else {
            throw new FormValidationException( MSG_ERREUR_CODE_POSTAL_NULL );
        }
    }

}
