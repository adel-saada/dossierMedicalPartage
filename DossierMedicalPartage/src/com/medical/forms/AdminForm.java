package com.medical.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.medical.beans.Admin;
import com.medical.dao.AdminDao;

public class AdminForm {

    public static final String  PARAM_LOGIN                = "login-username";
    public static final String  PARAM_PASS                 = "login-password";

    private static final String MSG_CONNEXION_VALIDE       = "Connexion validée";
    private static final String MSG_ERREUR_CONNEXION_ADMIN = "Erreur de connexion admin";

    private String              resultat;
    private Map<String, String> erreurs                    = new HashMap<String, String>();

    private AdminDao            adminDao;

    public AdminForm( AdminDao adminDao ) {
        this.adminDao = adminDao;
    }

    /**
     * <b>Objectif</b> : Vérifie si l'administrateur saisie dans le formulaire
     * existe dans la BDD
     * 
     * @param request
     * @return booleen
     */
    public Admin connecterAdmin( HttpServletRequest request ) {

        String login = UtilitaireForm.getValeurParametre( request, PARAM_LOGIN );
        String motDePasse = UtilitaireForm.getValeurParametre( request, PARAM_PASS );

        Admin admin = new Admin();
        admin.setLogin( login );
        admin.setMotDePasse( motDePasse );

        boolean estValide = adminDao.estValideIdentification( admin );
        if ( estValide ) {
            resultat = MSG_CONNEXION_VALIDE;
        } else {
            resultat = MSG_ERREUR_CONNEXION_ADMIN;
            setErreur( PARAM_LOGIN, "Login ou mot de passe invalide" );
        }
        return admin;
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
