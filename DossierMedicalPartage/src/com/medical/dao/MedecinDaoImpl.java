package com.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medical.beans.Medecin;
import com.medical.beans.Utilisateur;

public class MedecinDaoImpl implements MedecinDao {

    private DaoFactory          daoFactory;

    /* requête SELECT vérification medecin */
    private static final String STR_SELECT_IDENTIFICATION_MEDECIN        = "SELECT * FROM Medecin WHERE LoginMedecin=? AND MdpMedecin=?";

    private static final String STR_SELECT_IDENTIFICATION_MEDECIN_PAR_ID = "SELECT * FROM Medecin WHERE IdMedecin= ?";

    /* requête SELECT récupération liste medecins */
    private static final String STR_SELECT_LISTE_MEDECINS                = "SELECT * FROM Medecin";
    /* requête INSERT ajout medecin */
    private static final String STR_INSERT_MEDECIN                       = "INSERT INTO Medecin(LoginMedecin, NomMedecin, PrenomMedecin, SpecialiteMedecin, TelMedecin, EmailMedecin, CodePostalMedecin, RueMedecin, VilleMedecin, MdpMedecin) VALUES (?,?,?,?,?,?,?,?,?,?)";
    /* requête UPDATE modification medecin */
    private static final String STR_UPDATE_MEDECIN                       = "UPDATE Medecin SET NomMedecin = ?, PrenomMedecin = ?,SpecialiteMedecin = ?, TelMedecin = ?, EmailMedecin = ?, CodePostalMedecin = ?,RueMedecin=?, VilleMedecin=? WHERE IdMedecin = ?";
    /* requête DELETE suppression medecin */
    private static final String STR_DELETE_MEDECIN                       = "DELETE FROM Medecin WHERE IdMedecin = ? ";

    /* Nom des champs SQL de la table Medecin */
    private static final String CHAMP_ID_MEDECIN                         = "IdMedecin";
    private static final String CHAMP_LOGIN_MEDECIN                      = "LoginMedecin";
    private static final String CHAMP_NOM_MEDECIN                        = "NomMedecin";
    private static final String CHAMP_PRENOM_MEDECIN                     = "PrenomMedecin";
    private static final String CHAMP_SPE_MEDECIN                        = "SpecialiteMedecin";
    private static final String CHAMP_TEL_MEDECIN                        = "TelMedecin";
    private static final String CHAMP_EMAIL_MEDECIN                      = "EmailMedecin";
    private static final String CHAMP_CODE_POSTAL_MEDECIN                = "CodePostalMedecin";
    private static final String CHAMP_RUE_MEDECIN                        = "RueMedecin";
    private static final String CHAMP_VILLE_MEDECIN                      = "VilleMedecin";
    private static final String CHAMP_MDP_MEDECIN                        = "MdpMedecin";

    MedecinDaoImpl( DaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean estValideIdentification( Utilisateur utilisateur ) throws DAOException {

        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_IDENTIFICATION_MEDECIN ) ) {

                preStatement.setString( 1, utilisateur.getLogin() );
                preStatement.setString( 2, utilisateur.getMotDePasse() );

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

    @Override
    public void recuperer( Medecin medecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_IDENTIFICATION_MEDECIN ) ) {

                preStatement.setString( 1, medecin.getLogin() );
                preStatement.setString( 2, medecin.getMotDePasse() );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    while ( resulSet.next() ) {
                        medecin.setId( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        medecin.setLogin( resulSet.getString( CHAMP_LOGIN_MEDECIN ) );
                        medecin.setNom( resulSet.getString( CHAMP_NOM_MEDECIN ) );
                        medecin.setPrenom( resulSet.getString( CHAMP_PRENOM_MEDECIN ) );
                        medecin.setSpecialite( resulSet.getString( CHAMP_SPE_MEDECIN ) );
                        medecin.setTel( resulSet.getString( CHAMP_TEL_MEDECIN ) );
                        medecin.setEmail( resulSet.getString( CHAMP_EMAIL_MEDECIN ) );
                        medecin.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_MEDECIN ) );
                        medecin.setRue( resulSet.getString( CHAMP_RUE_MEDECIN ) );
                        medecin.setVille( resulSet.getString( CHAMP_VILLE_MEDECIN ) );
                        medecin.setMotDePasse( resulSet.getString( CHAMP_MDP_MEDECIN ) );
                    }
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public Medecin recuperer( int idMedecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection
                    .prepareStatement( STR_SELECT_IDENTIFICATION_MEDECIN_PAR_ID ) ) {

                preStatement.setInt( 1, idMedecin );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    Medecin medecin = new Medecin();
                    if ( resulSet.next() ) {
                        medecin.setId( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        medecin.setLogin( resulSet.getString( CHAMP_LOGIN_MEDECIN ) );
                        medecin.setNom( resulSet.getString( CHAMP_NOM_MEDECIN ) );
                        medecin.setPrenom( resulSet.getString( CHAMP_PRENOM_MEDECIN ) );
                        medecin.setSpecialite( resulSet.getString( CHAMP_SPE_MEDECIN ) );
                        medecin.setTel( resulSet.getString( CHAMP_TEL_MEDECIN ) );
                        medecin.setEmail( resulSet.getString( CHAMP_EMAIL_MEDECIN ) );
                        medecin.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_MEDECIN ) );
                        medecin.setRue( resulSet.getString( CHAMP_RUE_MEDECIN ) );
                        medecin.setVille( resulSet.getString( CHAMP_VILLE_MEDECIN ) );
                        medecin.setMotDePasse( resulSet.getString( CHAMP_MDP_MEDECIN ) );
                    }
                    return medecin;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public List<Medecin> lister() throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_LISTE_MEDECINS ) ) {
                try ( ResultSet resulSet = preStatement.executeQuery() ) {

                    List<Medecin> listeMedecin = new ArrayList<>();
                    while ( resulSet.next() ) {
                        Medecin medecin = new Medecin();

                        medecin.setId( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        medecin.setLogin( resulSet.getString( CHAMP_LOGIN_MEDECIN ) );
                        medecin.setNom( resulSet.getString( CHAMP_NOM_MEDECIN ) );
                        medecin.setPrenom( resulSet.getString( CHAMP_PRENOM_MEDECIN ) );
                        medecin.setSpecialite( resulSet.getString( CHAMP_SPE_MEDECIN ) );
                        medecin.setTel( resulSet.getString( CHAMP_TEL_MEDECIN ) );
                        medecin.setEmail( resulSet.getString( CHAMP_EMAIL_MEDECIN ) );
                        medecin.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_MEDECIN ) );
                        medecin.setRue( resulSet.getString( CHAMP_RUE_MEDECIN ) );
                        medecin.setVille( resulSet.getString( CHAMP_VILLE_MEDECIN ) );
                        medecin.setMotDePasse( resulSet.getString( CHAMP_MDP_MEDECIN ) );

                        listeMedecin.add( medecin );
                    }
                    return listeMedecin;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void ajouter( Medecin medecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_INSERT_MEDECIN ) ) {
                // login = nom(pour le moment)
                preStatement.setString( 1, medecin.getNom() );
                preStatement.setString( 2, medecin.getNom() );
                preStatement.setString( 3, medecin.getPrenom() );
                preStatement.setString( 4, medecin.getSpecialite() );
                preStatement.setString( 5, medecin.getTel() );
                preStatement.setString( 6, medecin.getEmail() );
                preStatement.setString( 7, medecin.getCodePostal() );
                preStatement.setString( 8, medecin.getRue() );
                preStatement.setString( 9, medecin.getVille() );
                preStatement.setString( 10, "password" ); // password

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void modifier( Medecin medecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_UPDATE_MEDECIN ) ) {

                preStatement.setString( 1, medecin.getNom() );
                preStatement.setString( 2, medecin.getPrenom() );
                preStatement.setString( 3, medecin.getSpecialite() );
                preStatement.setString( 4, medecin.getTel() );
                preStatement.setString( 5, medecin.getEmail() );
                preStatement.setString( 6, medecin.getCodePostal() );
                preStatement.setString( 7, medecin.getRue() );
                preStatement.setString( 8, medecin.getVille() );
                preStatement.setInt( 9, medecin.getId() );

                preStatement.executeUpdate();
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void supprimer( int idMedecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_DELETE_MEDECIN ) ) {
                preStatement.setInt( 1, idMedecin );
                preStatement.executeUpdate();
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
}
