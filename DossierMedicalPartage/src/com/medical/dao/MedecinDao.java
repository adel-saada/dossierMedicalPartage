package com.medical.dao;

import java.util.List;

import com.medical.beans.Medecin;
import com.medical.beans.Utilisateur;

public interface MedecinDao {

    /**
     * <b>Objectif</b> : Vérifier identification Utilisateur
     * 
     * @param utilisateur
     *            (propriété : login et pass)
     * @return booleen
     * @throws DAOException
     */
    boolean estValideIdentification( Utilisateur utilisateur ) throws DAOException;

    /**
     * <b>Objectif</b> : Retourner la liste des Medecins
     * 
     * 
     * @return List<Medecin>
     * @throws DAOException
     */
    List<Medecin> lister() throws DAOException;

    /**
     * <b>Objectif</b> : Récuperer les informations d'un medecin
     * 
     * 
     * <b>Particularité</b> : avec les propriétés login et pass contenu dans un
     * objet Medecin, la méthode va remplir l'objet pour avoir toutes les
     * informations liées à ce medecin.
     * 
     * @param medecin
     *            (login et pass)
     * @throws DAOException
     */
    void recuperer( Medecin medecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Récuperer les informations d'un medecin (méthode
     * surchargée).
     * 
     * Particularité : avec l'idMedecin, crée l'objet Medecin et le retourner.
     * 
     * @param idMedecin
     * @return Medecin
     * @throws DAOException
     */
    Medecin recuperer( int idMedecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Ajouter un medecin
     * 
     * @param medecin
     * @throws DAOException
     */
    void ajouter( Medecin medecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Modifier un medecin
     * 
     * @param medecin
     * @throws DAOException
     */
    void modifier( Medecin medecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Supprimer un medecin
     * 
     * @param idMedecin
     * @throws DAOException
     */
    void supprimer( int idMedecin ) throws DAOException;
}
