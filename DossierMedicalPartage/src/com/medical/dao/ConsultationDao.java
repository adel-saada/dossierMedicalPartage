package com.medical.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import com.medical.beans.Consultation;
import com.medical.beans.Diagnostic;
import com.medical.beans.Medecin;

public interface ConsultationDao {

    /**
     * <b>Objectif</b> : Ajouter une consultation
     * 
     * Particularité : il va crée un diagnostic vide pour lui associer la
     * nouvelle id auto incrémenté.
     * 
     * @param consultation
     * @throws DAOException
     */
    void ajouter( Consultation consultation ) throws DAOException;

    /**
     * <b>Objectif</b> : Modifier une consultation
     * 
     * Pour modifier une consultation il faut la date et l'heure actuelle de
     * cette consultation ainsi que l'id medecin. La nouvelle date et la
     * nouvelle heure est contenu dans l'objet consultation.
     * 
     * 
     * @param consultation
     *            (contenant la date et l'heure du nouveau rendez vous)
     * @param dateActuelle
     *            (la date qu'il faut modifier)
     * @param heureActuelle
     *            (l'heure qu'il faut modifier)
     * @throws DAOException
     */
    void modifier( Consultation consultation, Date dateActuelle, Time heureActuelle ) throws DAOException;

    /**
     * <b>Objectif</b> : Retourner la liste des consultations
     * 
     * @return List<Consultation>
     * @throws DAOException
     */
    List<Consultation> lister() throws DAOException;

    /**
     * <b>Objectif</b> : Retourner la liste des consultations d'un patient chez
     * un medecin specifié. (méthode surchargée)
     * 
     * @param idPatient
     * @param idMedecin
     * @return
     * @throws DAOException
     */
    List<Consultation> lister( int idPatient, int idMedecin ) throws DAOException;

    /**
     * <b>Objectif</b> : Supprimer une consultation
     * 
     * @param consultation
     * @throws DAOException
     */
    void supprimer( Consultation consultation ) throws DAOException;

    /**
     * <b>Objectif</b> : Retourner une Map contenant les consultations et les
     * diagnostics associés d'un patient chez un medecin spécifique.
     * 
     * Particularité : avoir toutes les consultations et diagnostics d'un
     * medecin pour un patient ciblé.
     * 
     * @param idPatient
     * @param idMedecin
     * @return Map<Consultation,Diagnostic>
     * @throws DAOException
     */
    Map<Consultation, Diagnostic> mapperDiagnosticsEtConsultationsPatient( int idPatient, int idMedecin )
            throws DAOException;

    /**
     * <b>Objectif</b> : Retourner une Map contenant les medecins et leurs
     * consultations pour un patient.
     * 
     * Particularité : avoir toutes les consultations ainsi que les medecins qui
     * l'ont écrit pour un patient ciblé.
     * 
     * @param idPatient
     * @return
     * @throws DAOException
     */
    Map<Medecin, Consultation> mapperConsultationsMedecinsPourUnPatient( int idPatient ) throws DAOException;

}
