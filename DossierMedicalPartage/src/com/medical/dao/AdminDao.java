package com.medical.dao;

import com.medical.beans.Admin;

public interface AdminDao {

    /**
     * <b>Objectif</b> : Vérifier identification administrateur
     * 
     * @param admin
     *            (propriété : login et pass)
     * @return booleen
     * @throws DAOException
     */
    boolean estValideIdentification( Admin admin ) throws DAOException;

}
