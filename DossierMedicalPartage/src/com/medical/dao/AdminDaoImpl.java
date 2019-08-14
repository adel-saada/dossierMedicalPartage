package com.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.medical.beans.Admin;

public class AdminDaoImpl implements AdminDao {

    private DaoFactory          daoFactory;

    /* requête SELECT vérification admin */
    private static final String STR_SELECT_IDENTIFICATION_ADMIN = "SELECT * FROM Admin WHERE LoginAdmin=? AND MdpAdmin=?";

    AdminDaoImpl( DaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean estValideIdentification( Admin admin ) throws DAOException {

        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_IDENTIFICATION_ADMIN ) ) {

                preStatement.setString( 1, admin.getLogin() );
                preStatement.setString( 2, admin.getMotDePasse() );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    if ( resulSet.next() ) {
                        return true;
                    }
                    return false;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
}
