package com.medical.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.medical.beans.Consultation;
import com.medical.beans.Diagnostic;
import com.medical.beans.Medecin;

public class ConsultationDaoImpl implements ConsultationDao {

    /* requête SELECT récupération liste consultations */
    private static final String STR_SELECT_CONSULTATIONS                          = "SELECT * FROM Consultation";

    /*
     * requête SELECT récupération liste consultations d'un patient pour un
     * medecin spécifique
     */
    private static final String STR_SELECT_CONSULTATIONS_PATIENT_MEDECIN          = "SELECT * FROM Consultation WHERE IdPatient= ? AND IdMedecin = ?";

    /* Requête SELECT */
    private static final String STR_SELECT_DIAGNOSTIC                             = "SELECT * FROM Diagnostic WHERE idDiagnostic=?";

    private static final String STR_INSERT_DIAGNOSTIC_VIDE                        = "INSERT INTO Diagnostic(DescriptifDiagnostic,AllergiesDiagnostic,AntecedentsMedicauxDiagnostic,PrescriptionDiagnostic) VALUES(?,?,?,?)";

    /*
     * requête SELECT récupération liste consultations détaillée (avec info
     * medecin) d'un patient
     */
    private static final String STR_SELECT_CONSULTATIONS_MEDECINS_POUR_UN_PATIENT = "SELECT idDiagnostic, Medecin.IdMedecin, NomMedecin, PrenomMedecin,SpecialiteMedecin, TelMedecin, EmailMedecin, CodePostalMedecin,RueMedecin,VilleMedecin, DateConsultation, HeureConsultation "
            + " FROM Consultation "
            + " INNER JOIN Medecin "
            + " ON Consultation.idMedecin = Medecin.idMedecin "
            + " WHERE IdPatient= ? ";

    /* requête INSERT insertion consultation */
    private static final String STR_INSERT_CONSULTATION                           = "INSERT INTO Consultation(IdPatient,IdMedecin,DateConsultation,HeureConsultation, IdDiagnostic) VALUES (?,?,?,?,?)";
    /* requête UPDATE modification consultation */
    private static final String STR_UPDATE_CONSULTATION                           = "UPDATE Consultation SET DateConsultation=?,HeureConsultation=? WHERE IdMedecin=? AND DateConsultation=? AND HeureConsultation=? ";
    /* requête DELETE suppression consultation */
    private static final String STR_DELETE_CONSULTATION                           = "DELETE FROM Consultation  WHERE IdMedecin=? AND DateConsultation=? AND HeureConsultation=?  ";

    /* Nom des champs SQL de la table Consultation */
    private static final String CHAMP_DATE_CONSULTATION                           = "DateConsultation";
    private static final String CHAMP_HEURE_CONSULTATION                          = "HeureConsultation";
    private static final String CHAMP_ID_DIAGNOSTIC                               = "IdDiagnostic";

    /* Nom de quelques champs SQL de la table Medecin */
    private static final String CHAMP_ID_MEDECIN                                  = "IdMedecin";
    private static final String CHAMP_NOM_MEDECIN                                 = "NomMedecin";
    private static final String CHAMP_PRENOM_MEDECIN                              = "PrenomMedecin";
    private static final String CHAMP_SPE_MEDECIN                                 = "SpecialiteMedecin";
    private static final String CHAMP_TEL_MEDECIN                                 = "TelMedecin";
    private static final String CHAMP_EMAIL_MEDECIN                               = "EmailMedecin";
    private static final String CHAMP_CODE_POSTAL_MEDECIN                         = "CodePostalMedecin";
    private static final String CHAMP_RUE_MEDECIN                                 = "RueMedecin";
    private static final String CHAMP_VILLE_MEDECIN                               = "VilleMedecin";

    /* Nom de quelques champs SQL de la table Patient */
    private static final String CHAMP_ID_PATIENT                                  = "IdPatient";

    private DaoFactory          daoFactory;

    ConsultationDaoImpl( DaoFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter( Consultation consultation ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_INSERT_CONSULTATION ) ) {

                Date date = consultation.getDateConsultation();
                Time heure = consultation.getHeureConsultation();
                int idPatient = consultation.getIdPatient();
                int idMedecin = consultation.getIdMedecin();
                int dernierIdDiagnostic = 0;
                try ( PreparedStatement preStatementDiagnostic = connection
                        .prepareStatement( STR_INSERT_DIAGNOSTIC_VIDE, Statement.RETURN_GENERATED_KEYS ) ) {

                    preStatementDiagnostic.setString( 1, null );
                    preStatementDiagnostic.setString( 2, null );
                    preStatementDiagnostic.setString( 3, null );
                    preStatementDiagnostic.setString( 4, null );

                    preStatementDiagnostic.executeUpdate();

                    try ( ResultSet resultSet = preStatementDiagnostic.getGeneratedKeys() ) {
                        if ( resultSet.next() ) {
                            dernierIdDiagnostic = resultSet.getInt( 1 );
                        }
                    }
                }
                preStatement.setInt( 1, idPatient );
                preStatement.setInt( 2, idMedecin );
                preStatement.setDate( 3, date );
                preStatement.setTime( 4, heure );
                preStatement.setInt( 5, dernierIdDiagnostic );
                preStatement.executeUpdate();
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public List<Consultation> lister() throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_SELECT_CONSULTATIONS ) ) {
                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    List<Consultation> listeConsultation = new ArrayList<>();
                    while ( resulSet.next() ) {
                        Consultation consultation = new Consultation();
                        consultation.setIdPatient( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        consultation.setIdMedecin( resulSet.getInt( CHAMP_ID_MEDECIN ) );

                        consultation.setDateConsultation( resulSet.getDate( CHAMP_DATE_CONSULTATION ) );
                        consultation.setHeureConsultation( resulSet.getTime( CHAMP_HEURE_CONSULTATION ) );
                        consultation.setIdDiagnostic( resulSet.getInt( CHAMP_ID_DIAGNOSTIC ) );
                        listeConsultation.add( consultation );
                    }
                    return listeConsultation;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public List<Consultation> lister( int idPatient, int idMedecin )
            throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection
                    .prepareStatement( STR_SELECT_CONSULTATIONS_PATIENT_MEDECIN ) ) {

                preStatement.setInt( 1, idPatient );
                preStatement.setInt( 2, idMedecin );
                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    List<Consultation> listeConsultation = new ArrayList<>();
                    while ( resulSet.next() ) {
                        Consultation consultation = new Consultation();
                        consultation.setIdPatient( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        consultation.setIdMedecin( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        consultation.setDateConsultation( resulSet.getDate( CHAMP_DATE_CONSULTATION ) );
                        consultation.setHeureConsultation( resulSet.getTime( CHAMP_HEURE_CONSULTATION ) );
                        consultation.setIdDiagnostic( resulSet.getInt( CHAMP_ID_DIAGNOSTIC ) );
                        listeConsultation.add( consultation );
                    }
                    return listeConsultation;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public Map<Consultation, Diagnostic> mapperDiagnosticsEtConsultationsPatient( int idPatient, int idMedecin )
            throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection
                    .prepareStatement( STR_SELECT_CONSULTATIONS_PATIENT_MEDECIN ) ) {

                preStatement.setInt( 1, idPatient );
                preStatement.setInt( 2, idMedecin );
                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    Map<Consultation, Diagnostic> listeConsultEtDiagnostic = new LinkedHashMap<>();
                    while ( resulSet.next() ) {
                        Consultation consultation = new Consultation();
                        consultation.setIdPatient( resulSet.getInt( CHAMP_ID_PATIENT ) );
                        consultation.setIdMedecin( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        consultation.setDateConsultation( resulSet.getDate( CHAMP_DATE_CONSULTATION ) );
                        consultation.setHeureConsultation( resulSet.getTime( CHAMP_HEURE_CONSULTATION ) );
                        consultation.setIdDiagnostic( resulSet.getInt( CHAMP_ID_DIAGNOSTIC ) );

                        try ( PreparedStatement preStatementDiagnostic = connection
                                .prepareStatement( STR_SELECT_DIAGNOSTIC ) ) {

                            preStatementDiagnostic.setInt( 1, consultation.getIdDiagnostic() );

                            try ( ResultSet resulSetDiagnostic = preStatementDiagnostic.executeQuery() ) {
                                Diagnostic diagnostic = new Diagnostic();
                                if ( resulSetDiagnostic.next() ) {

                                    diagnostic.setIdDiagnostic( consultation.getIdDiagnostic() );
                                    diagnostic.setAllergiesDiagnostic(
                                            resulSetDiagnostic.getString( "AllergiesDiagnostic" ) );
                                    diagnostic.setDescriptifDiagnostic(
                                            resulSetDiagnostic.getString( "DescriptifDiagnostic" ) );
                                    diagnostic.setAntecedentsMedicauxDiagnostic(
                                            resulSetDiagnostic.getString( "AntecedentsMedicauxDiagnostic" ) );
                                    diagnostic.setPrescriptionDiagnostic(
                                            resulSetDiagnostic.getString( "PrescriptionDiagnostic" ) );
                                }
                                listeConsultEtDiagnostic.put( consultation, diagnostic );
                            }
                        }
                    }
                    return listeConsultEtDiagnostic;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public Map<Medecin, Consultation> mapperConsultationsMedecinsPourUnPatient( int idPatient ) {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection
                    .prepareStatement( STR_SELECT_CONSULTATIONS_MEDECINS_POUR_UN_PATIENT ) ) {

                preStatement.setInt( 1, idPatient );

                try ( ResultSet resulSet = preStatement.executeQuery() ) {
                    Map<Medecin, Consultation> mapConsultationsPatient = new LinkedHashMap<>();
                    while ( resulSet.next() ) {
                        // remplissage Consultation
                        Consultation consultation = new Consultation();
                        consultation.setIdPatient( idPatient );
                        consultation.setIdMedecin( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        consultation.setDateConsultation( resulSet.getDate( CHAMP_DATE_CONSULTATION ) );
                        consultation.setHeureConsultation( resulSet.getTime( CHAMP_HEURE_CONSULTATION ) );
                        consultation.setIdDiagnostic( resulSet.getInt( CHAMP_ID_DIAGNOSTIC ) );

                        // remplissage Medecin
                        Medecin medecin = new Medecin();
                        medecin.setId( resulSet.getInt( CHAMP_ID_MEDECIN ) );
                        medecin.setNom( resulSet.getString( CHAMP_NOM_MEDECIN ) );
                        medecin.setPrenom( resulSet.getString( CHAMP_PRENOM_MEDECIN ) );
                        medecin.setSpecialite( resulSet.getString( CHAMP_SPE_MEDECIN ) );
                        medecin.setTel( resulSet.getString( CHAMP_TEL_MEDECIN ) );
                        medecin.setEmail( resulSet.getString( CHAMP_EMAIL_MEDECIN ) );
                        medecin.setCodePostal( resulSet.getString( CHAMP_CODE_POSTAL_MEDECIN ) );
                        medecin.setRue( resulSet.getString( CHAMP_RUE_MEDECIN ) );
                        medecin.setVille( resulSet.getString( CHAMP_VILLE_MEDECIN ) );

                        mapConsultationsPatient.put( medecin, consultation );

                    }
                    return mapConsultationsPatient;
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void modifier( Consultation consultation, Date dateActuelle, Time heureActuelle )
            throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_UPDATE_CONSULTATION ) ) {

                preStatement.setDate( 1, consultation.getDateConsultation() );
                preStatement.setTime( 2, consultation.getHeureConsultation() );

                preStatement.setInt( 3, consultation.getIdMedecin() );
                preStatement.setDate( 4, dateActuelle );
                preStatement.setTime( 5, heureActuelle );

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

    @Override
    public void supprimer( Consultation consultation ) throws DAOException {
        try ( Connection connection = daoFactory.getConnection() ) {
            try ( PreparedStatement preStatement = connection.prepareStatement( STR_DELETE_CONSULTATION ) ) {

                preStatement.setInt( 1, consultation.getIdMedecin() );
                preStatement.setDate( 2, consultation.getDateConsultation() );
                preStatement.setTime( 3, consultation.getHeureConsultation() );

                preStatement.executeUpdate();

            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }

}
