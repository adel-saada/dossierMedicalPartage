package com.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medical.beans.Patient;
import com.medical.beans.Utilisateur;

public class PatientDaoImpl implements PatientDao {

    private DaoFactory          daoFactory;

    /* requête SELECT vérification patient */
    private static final String STR_SELECT_IDENTIFICATION_PATIENT      = "SELECT * FROM Patient WHERE LoginPatient=? AND MdpPatient=?";
    private static final String STR_SELECT_RECUPERATION_PATIENT_PAR_ID = "SELECT * FROM Patient WHERE IdPatient=?";

    /* requête SELECT récupération liste patients */
    private static final String STR_SELECT_LISTE_PATIENTS              = "SELECT * FROM Patient";
    private static final String STR_SELECT_PATIENT                     = "SELECT * FROM Patient WHERE IdPatient=?";

    private static final String STR_SELECT_LISTE_PATIENTS_MEDECIN      = "SELECT IdPatient FROM Consultation WHERE IdMedecin=?";

    /* requête INSERT ajout patient */
    private static final String STR_INSERT_PATIENT                     = "INSERT INTO Patient(LoginPatient,NumeroSS,NomPatient,PrenomPatient,TelPatient,SexePatient,EmailPatient,CodePostalPatient,RuePatient,VillePatient,MdpPatient) VALUE(?,?,?,?,?,?,?,?,?,?,?)";
    /* requête UPDATE modification patient */
    private static final String STR_UPDATE_PATIENT                     = "UPDATE Patient SET NumeroSS = ?, NomPatient = ?, PrenomPatient = ?,TelPatient = ?, SexePatient=?, EmailPatient = ?, CodePostalPatient = ?,RuePatient=?, VillePatient=? WHERE IdPatient = ?";
    /* requête DELETE suppression patient */
    private static final String STR_DELETE_PATIENT                     = "DELETE FROM Patient WHERE IdPatient= ? ";

    /* Nom des champs SQL de la table Patient */
    private static final String CHAMP_ID_PATIENT                       = "IdPatient";
    private static final String CHAMP_LOGIN_PATIENT                    = "LoginPatient";
    private static final String CHAMP_NUMERO_SS                        = "NumeroSS";
    private static final String CHAMP_NOM_PATIENT                      = "NomPatient";
    private static final String CHAMP_PRENOM_PATIENT                   = "PrenomPatient";
    private static final String CHAMP_TEL_PATIENT                      = "TelPatient";
    private static final String CHAMP_SEXE_PATIENT                     = "SexePatient";
    private static final String CHAMP_EMAIL_PATIENT                    = "EmailPatient";
    private static final String CHAMP_CODE_POSTAL_PATIENT              = "CodePostalPatient";
    private static final String CHAMP_RUE_PATIENT                      = "RuePatient";
    private static final String CHAMP_VILLE_PATIENT                    = "VillePatient";
    private static final String CHAMP_MDP_PATIENT                      = "MdpPatient";

    PatientDaoImpl( DaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean estValideIdentification( Utilisateur utilisateur ) throws DAOException {

        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_IDENTIFICATION_PATIENT ) ) {

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
    public void recuperer( Patient patient ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_IDENTIFICATION_PATIENT ) ) {

                preStatement.setString( 1, patient.getLogin() );
                preStatement.setString( 2, patient.getMotDePasse() );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    while ( resulSet.next() ) {
                        patient.setId( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        patient.setNumeroSS( resulSet.getString( CHAMP_NUMERO_SS ) );
                        patient.setNom( resulSet.getString( CHAMP_NOM_PATIENT ) );
                        patient.setPrenom( resulSet.getString( CHAMP_PRENOM_PATIENT ) );
                        patient.setTel( resulSet.getString( CHAMP_TEL_PATIENT ) );
                        patient.setSexe( resulSet.getString( CHAMP_SEXE_PATIENT ).charAt( 0 ) );
                        patient.setEmail( resulSet.getString( CHAMP_EMAIL_PATIENT ) );
                        patient.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_PATIENT ) );
                        patient.setRue( resulSet.getString( CHAMP_RUE_PATIENT ) );
                        patient.setVille( resulSet.getString( CHAMP_VILLE_PATIENT ) );
                    }
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    public Patient recuperer( int idPatient ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection
                    .prepareStatement( STR_SELECT_RECUPERATION_PATIENT_PAR_ID ) ) {

                preStatement.setInt( 1, idPatient );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    Patient patient = new Patient();
                    if ( resulSet.next() ) {
                        patient.setId( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        patient.setNumeroSS( resulSet.getString( CHAMP_NUMERO_SS ) );
                        patient.setNom( resulSet.getString( CHAMP_NOM_PATIENT ) );
                        patient.setPrenom( resulSet.getString( CHAMP_PRENOM_PATIENT ) );
                        patient.setTel( resulSet.getString( CHAMP_TEL_PATIENT ) );
                        patient.setSexe( resulSet.getString( CHAMP_SEXE_PATIENT ).charAt( 0 ) );
                        patient.setEmail( resulSet.getString( CHAMP_EMAIL_PATIENT ) );
                        patient.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_PATIENT ) );
                        patient.setRue( resulSet.getString( CHAMP_RUE_PATIENT ) );
                        patient.setVille( resulSet.getString( CHAMP_VILLE_PATIENT ) );
                    }
                    return patient;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public List<Patient> lister() throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_LISTE_PATIENTS ) ) {
                try ( ResultSet resulSet = preStatement.executeQuery() ) {

                    List<Patient> listePatient = new ArrayList<>();
                    while ( resulSet.next() ) {
                        Patient patient = new Patient();

                        patient.setId( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        patient.setLogin( resulSet.getString( CHAMP_LOGIN_PATIENT ) );
                        patient.setNumeroSS( resulSet.getString( CHAMP_NUMERO_SS ) );
                        patient.setNom( resulSet.getString( CHAMP_NOM_PATIENT ) );
                        patient.setPrenom( resulSet.getString( CHAMP_PRENOM_PATIENT ) );
                        patient.setTel( resulSet.getString( CHAMP_TEL_PATIENT ) );
                        patient.setSexe( resulSet.getString( CHAMP_SEXE_PATIENT ).charAt( 0 ) );
                        patient.setEmail( resulSet.getString( CHAMP_EMAIL_PATIENT ) );
                        patient.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_PATIENT ) );
                        patient.setRue( resulSet.getString( CHAMP_RUE_PATIENT ) );
                        patient.setVille( resulSet.getString( CHAMP_VILLE_PATIENT ) );
                        patient.setMotDePasse( resulSet.getString( CHAMP_MDP_PATIENT ) );

                        listePatient.add( patient );

                    }
                    return listePatient;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public List<Patient> lister( int idMedecin ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_LISTE_PATIENTS_MEDECIN ) ) {

                preStatement.setInt( 1, idMedecin );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {

                    List<Patient> listePatient = new ArrayList<>();
                    while ( resulSet.next() ) {

                        int idPatient = resulSet.getInt( 1 );
                        try ( PreparedStatement preStatementPatient = connection
                                .prepareStatement( STR_SELECT_PATIENT ) ) {

                            preStatementPatient.setInt( 1, idPatient );

                            try ( ResultSet resultSetPatient = preStatementPatient.executeQuery() ) {

                                while ( resultSetPatient.next() ) {

                                    boolean existence = false;
                                    for ( Patient p : listePatient ) {
                                        if ( idPatient == p.getId() ) {
                                            existence = true;
                                            break;
                                        }
                                    }

                                    if ( !existence ) {
                                        Patient patient = new Patient();
                                        patient.setId( idPatient );
                                        patient.setLogin( resultSetPatient.getString( CHAMP_LOGIN_PATIENT ) );
                                        patient.setNumeroSS( resultSetPatient.getString( CHAMP_NUMERO_SS ) );
                                        patient.setNom( resultSetPatient.getString( CHAMP_NOM_PATIENT ) );
                                        patient.setPrenom( resultSetPatient.getString( CHAMP_PRENOM_PATIENT ) );
                                        patient.setTel( resultSetPatient.getString( CHAMP_TEL_PATIENT ) );
                                        patient.setSexe(
                                                resultSetPatient.getString( CHAMP_SEXE_PATIENT ).charAt( 0 ) );
                                        patient.setEmail( resultSetPatient.getString( CHAMP_EMAIL_PATIENT ) );
                                        patient.setCodePostal(
                                                resultSetPatient.getString( CHAMP_CODE_POSTAL_PATIENT ) );
                                        patient.setRue( resultSetPatient.getString( CHAMP_RUE_PATIENT ) );
                                        patient.setVille( resultSetPatient.getString( CHAMP_VILLE_PATIENT ) );
                                        patient.setMotDePasse( resultSetPatient.getString( CHAMP_MDP_PATIENT ) );

                                        listePatient.add( patient );

                                    }
                                }
                            }

                        }
                    }
                    return listePatient;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void ajouter( Patient patient ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_INSERT_PATIENT ) ) {

                preStatement.setString( 1, patient.getLogin() );
                preStatement.setString( 2, patient.getNumeroSS() );
                preStatement.setString( 3, patient.getNom() );
                preStatement.setString( 4, patient.getPrenom() );
                preStatement.setString( 5, patient.getTel() );
                preStatement.setString( 6, patient.getSexe() + "" );
                preStatement.setString( 7, patient.getEmail() );
                preStatement.setString( 8, patient.getCodePostal() );
                preStatement.setString( 9, patient.getRue() );
                preStatement.setString( 10, patient.getVille() );
                preStatement.setString( 11, "passwordParDefaut" ); // password
                                                                   // par defaut

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void modifier( Patient patient ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_UPDATE_PATIENT ) ) {

                preStatement.setString( 1, patient.getNumeroSS() );
                preStatement.setString( 2, patient.getNom() );
                preStatement.setString( 3, patient.getPrenom() );
                preStatement.setString( 4, patient.getTel() );
                String sexe = patient.getSexe() + "";
                preStatement.setString( 5, sexe );
                preStatement.setString( 6, patient.getEmail() );
                preStatement.setString( 7, patient.getCodePostal() );
                preStatement.setString( 8, patient.getRue() );
                preStatement.setString( 9, patient.getVille() );
                preStatement.setInt( 10, patient.getId() );

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void supprimer( int idPatient ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_DELETE_PATIENT ) ) {
                preStatement.setInt( 1, idPatient );
                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

}
