-- Efface la base de donnée si elle existe
DROP DATABASE IF EXISTS BdDossierMedical;
-- Creer la base de donnée 
CREATE DATABASE BdDossierMedical CHARACTER SET utf8; 

-- Selectionner la base de donnée
USE BdDossierMedical;


#------------------------------------------------------------
#        Script MySQL : BdDossierMedical
#------------------------------------------------------------

#------------------------------------------------------------
# Table: Admin
#------------------------------------------------------------

CREATE TABLE Admin
(
	IdAdmin INT(5) AUTO_INCREMENT,
	LoginAdmin VARCHAR(20) NOT NULL,
	MdpAdmin VARCHAR(50) NOT NULL, 
	CONSTRAINT u_LoginAdmin UNIQUE(LoginAdmin),
	CONSTRAINT pk_IdAdmin PRIMARY KEY (IdAdmin)	
);

#------------------------------------------------------------
# Table: Patient
#------------------------------------------------------------

CREATE TABLE Patient
(
	IdPatient INT(5) AUTO_INCREMENT,
	LoginPatient VARCHAR(20) NOT NULL,
	NumeroSS CHAR(15) NOT NULL,
	NomPatient VARCHAR(50) NOT NULL,
	PrenomPatient VARCHAR(50) NOT NULL,
	TelPatient VARCHAR(15) NOT NULL,
	SexePatient CHAR(1) NOT NULL,
	EmailPatient VARCHAR(150) NOT NULL,
	CodePostalPatient CHAR(5) NOT NULL,
	RuePatient VARCHAR(200) NOT NULL,
	VillePatient VARCHAR(150) NOT NULL,
	MdpPatient VARCHAR(50) NOT NULL, 
	CONSTRAINT ck_SexePatient CHECK (SexePatient='H' OR SexePatient='F'),
	CONSTRAINT u_LoginPatient UNIQUE(LoginPatient),
	CONSTRAINT u_EmailPatient UNIQUE(EmailPatient),
	CONSTRAINT pk_IdPatient PRIMARY KEY (IdPatient)
);

#------------------------------------------------------------
# Table: Medecin
#------------------------------------------------------------

CREATE TABLE Medecin 
(
	IdMedecin INT(5) AUTO_INCREMENT,
	LoginMedecin VARCHAR(20) NOT NULL,
	NomMedecin VARCHAR(50) NOT NULL,
	PrenomMedecin VARCHAR(50) NOT NULL,
	SpecialiteMedecin VARCHAR(40) NOT NULL,
	TelMedecin VARCHAR(15) NOT NULL,
	EmailMedecin VARCHAR(150) NOT NULL,
	CodePostalMedecin CHAR(5) NOT NULL,
	RueMedecin VARCHAR(200) NOT NULL,
	VilleMedecin VARCHAR(150) NOT NULL,
	MdpMedecin VARCHAR(50) NOT NULL, 
	CONSTRAINT u_LoginMedecin UNIQUE(LoginMedecin),
	CONSTRAINT u_EmailMedecin UNIQUE(EmailMedecin),
	CONSTRAINT pk_IdMedecin PRIMARY KEY (IdMedecin) 	
);

#------------------------------------------------------------
# Table: Diagnostic
#------------------------------------------------------------

CREATE TABLE Diagnostic
(
	IdDiagnostic INT(5) AUTO_INCREMENT,
	DescriptifDiagnostic TEXT,
	AllergiesDiagnostic TEXT,
	AntecedentsMedicauxDiagnostic TEXT,
	PrescriptionDiagnostic TEXT,
	CONSTRAINT pk_IdDiagnostic PRIMARY KEY (IdDiagnostic)
); 


#------------------------------------------------------------
# Table: Consultation
#------------------------------------------------------------

CREATE TABLE Consultation
(
	IdPatient INT(5),
	IdMedecin INT(5),
	DateConsultation DATE,
	HeureConsultation TIME,
	IdDiagnostic INT(5), 
	CONSTRAINT ck_DateConsultation CHECK (DateConsultation >= CURRENT_DATE),
	CONSTRAINT pk_IdConsultation PRIMARY KEY (DateConsultation, HeureConsultation, IdMedecin),	
	CONSTRAINT fk_IdPatient FOREIGN KEY(IdPatient) REFERENCES Patient(IdPatient) ON DELETE CASCADE,
	CONSTRAINT fk_IdMedecin FOREIGN KEY(IdMedecin) REFERENCES Medecin(IdMedecin) ON DELETE CASCADE,
	CONSTRAINT fk_Diagnostic FOREIGN KEY(IdDiagnostic) REFERENCES Diagnostic(IdDiagnostic) ON DELETE CASCADE
); 


