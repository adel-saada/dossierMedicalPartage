package com.medical.dao;

import com.medical.beans.Diagnostic;

public interface DiagnosticDao {

    /**
     * <b>Objectif</b> : Modifier un diagnostic
     * 
     * @param diagnostic
     * @throws DAOException
     */
    void modifier( Diagnostic diagnostic ) throws DAOException;

    /**
     * <b>Objectif</b> : Modifier un diagnostic (méthode surchargée)
     * 
     * Particularité : ne modifie que le champ "Prescription" de la table
     * diagnostic.
     * 
     * @param idDiagnostic
     * @param prescription
     * @throws DAOException
     */
    void modifier( int idDiagnostic, String prescription ) throws DAOException;

}
