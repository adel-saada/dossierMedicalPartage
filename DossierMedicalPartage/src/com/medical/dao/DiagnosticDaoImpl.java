package com.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.medical.beans.Diagnostic;

public class DiagnosticDaoImpl implements DiagnosticDao {

    private static final String STR_UPDATE_DIAGNOSTIC   = "UPDATE Diagnostic SET DescriptifDiagnostic=?, AllergiesDiagnostic=?,AntecedentsMedicauxDiagnostic=? WHERE IdDiagnostic=?";

    private static final String STR_UPDATE_PRESCRIPTION = "UPDATE Diagnostic SET PrescriptionDiagnostic=? WHERE IdDiagnostic=?";

    private DaoFactory          daoFactory;

    DiagnosticDaoImpl( DaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void modifier( Diagnostic diagnostic ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_UPDATE_DIAGNOSTIC ) ) {

                preStatement.setString( 1, diagnostic.getDescriptifDiagnostic() );
                preStatement.setString( 2, diagnostic.getAllergiesDiagnostic() );
                preStatement.setString( 3, diagnostic.getAntecedentsMedicauxDiagnostic() );
                preStatement.setInt( 4, diagnostic.getIdDiagnostic() );

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void modifier( int idDiagnostic, String prescription ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_UPDATE_PRESCRIPTION ) ) {

                preStatement.setString( 1, prescription );
                preStatement.setInt( 2, idDiagnostic );

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

}
