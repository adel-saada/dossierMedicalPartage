package com.medical.dao;

import java.util.List;

import com.medical.beans.Patient;
import com.medical.beans.Utilisateur;

public interface PatientDao {

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
     * <b>Objectif</b> : Récuperer les informations d'un patient
     * 
     * <b>Particularité</b> : avec les propriétés login et pass contenu dans un
     * objet Patient, la méthode va remplir l'objet pour avoir toutes les
     * informations liées à ce patient.
     * 
     * 
     * @param patient
     * @throws DAOException
     */
    void recuperer( Patient patient ) throws DAOException;

    /**
     * <b>Objectif</b> : Récuperer les informations d'un patient (méthode
     * surchargée).
     * 
     * Particularité : avec l'idPatient, crée l'objet Patient et le retourner.
     * 
     * @param idPatient
     * @return
     * @throws DAOException
     */
    Patient recuperer( int idPatient ) throws DAOException;

    /**
     * <b>Objectif</b> : Retourner la liste des patients
     * 
     * @return
     * @throws DAOException
     */
    List<Patient> lister() throws DAOException;

    /**
     * <b>Objectif</b> : retourner la liste des patients d'un medecin
     * 
     * @param idMedecin
     * @return
     * @throws DAOException
     */
    List<Patient> lister( int idMedecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Ajouter un patient
     * 
     * @param patient
     * @throws DAOException
     */
    void ajouter( Patient patient ) throws DAOException;

    /**
     * <b>Objectif</b> : Modifier un patient
     * 
     * @param patient
     * @throws DAOException
     */
    void modifier( Patient patient ) throws DAOException;

    /**
     * <b>Objectif</b> : Supprimer un patient
     * 
     * @param idPatient
     * @throws DAOException
     */
    void supprimer( int idPatient ) throws DAOException;

}
