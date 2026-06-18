-- Base de donnees: Tooth_office
-- Structure moderne avec gestion complete du cabinet dentaire

CREATE DATABASE IF NOT EXISTS Tooth_office;
USE Tooth_office;

-- =============================================
-- Table principale des utilisateurs
-- =============================================
CREATE TABLE Utilisateur (
                             id_utilisateur INT PRIMARY KEY AUTO_INCREMENT,
                             nom VARCHAR(50) NOT NULL,
                             prenom VARCHAR(50) NOT NULL,
                             email VARCHAR(100) UNIQUE NOT NULL,
                             mpd VARCHAR(100) NOT NULL,
                             adresse VARCHAR(255),
                             role ENUM('CHEF_CABINET','PATIENT','SECRETAIRE','DENTISTE') DEFAULT 'PATIENT',
                             telephone VARCHAR(20),
                             statutCompte ENUM('VALIDE','SUSPENDU','SUPPRIMER') DEFAULT 'VALIDE',
                             createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =============================================
-- Table Cabinet
-- =============================================
CREATE TABLE Cabinet (
                         id_cabinet INT PRIMARY KEY AUTO_INCREMENT,
                         nom_cabinet VARCHAR(50) NOT NULL,
                         tel VARCHAR(50) UNIQUE,
                         adresse VARCHAR(60),
                         logo VARCHAR(200),
                         description VARCHAR(200)
);

-- =============================================
-- Table Chef de Cabinet
-- =============================================
CREATE TABLE Chef_Cabinet (
                              id_chef_cabinet INT PRIMARY KEY,
                              FOREIGN KEY (id_chef_cabinet) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

-- =============================================
-- Table Secretaire
-- =============================================
CREATE TABLE Secretaire (
                            id_secretaire INT PRIMARY KEY,
                            id_cabinet INT,
                            id_chef_cabinet INT,
                            FOREIGN KEY (id_secretaire) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE,
                            FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet) ON DELETE SET NULL,
                            FOREIGN KEY (id_chef_cabinet) REFERENCES Chef_Cabinet(id_chef_cabinet) ON DELETE SET NULL
);

-- =============================================
-- Table Dentiste
-- =============================================
CREATE TABLE Dentiste (
                          id_dentiste INT PRIMARY KEY,
                          specialite VARCHAR(100),
                          id_cabinet INT,
                          id_chef_cabinet INT,
                          FOREIGN KEY (id_dentiste) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE,
                          FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet) ON DELETE CASCADE,
                          FOREIGN KEY (id_chef_cabinet) REFERENCES Chef_Cabinet(id_chef_cabinet) ON DELETE SET NULL
);

-- =============================================
-- Table Patient
-- =============================================
CREATE TABLE Patient (
                         id_patient INT PRIMARY KEY,
                         date_naissance DATE,
                         FOREIGN KEY (id_patient) REFERENCES Utilisateur(id_utilisateur) ON DELETE CASCADE
);

-- =============================================
-- Table Creneau
-- =============================================
CREATE TABLE Creneau (
                         id_creneau INT PRIMARY KEY AUTO_INCREMENT,
                         date DATE NOT NULL,
                         heure_debut TIME NOT NULL,
                         heure_fin TIME NOT NULL,
                         disponible BOOLEAN DEFAULT TRUE,
                         id_dentiste INT,
                         FOREIGN KEY (id_dentiste) REFERENCES Dentiste(id_dentiste) ON DELETE SET NULL
);

-- =============================================
-- Table Rendez-vous
-- =============================================
CREATE TABLE Rendez_vous (
                             id_rendez_vous INT PRIMARY KEY AUTO_INCREMENT,
                             date_RDV DATETIME NOT NULL,
                             motif VARCHAR(50),
                             note TEXT,
                             etat_RDV ENUM('EN_ATTENTE','VALIDE','FAIT','ANNULE','REPORTE') DEFAULT 'EN_ATTENTE',
                             type_rdv ENUM('ENLIGNE','SURPLACE') DEFAULT 'ENLIGNE',
                             id_patient INT NOT NULL,
                             id_dentiste INT NOT NULL,
                             id_secretaire INT,
                             id_creneau INT,
                             FOREIGN KEY (id_patient) REFERENCES Patient(id_patient) ON DELETE CASCADE,
                             FOREIGN KEY (id_dentiste) REFERENCES Dentiste(id_dentiste) ON DELETE RESTRICT,
                             FOREIGN KEY (id_secretaire) REFERENCES Secretaire(id_secretaire),
                             FOREIGN KEY (id_creneau) REFERENCES Creneau(id_creneau)
);

-- =============================================
-- Table Dossier Medical
-- =============================================
CREATE TABLE DossierMedicale (
                                 id_dossier INT PRIMARY KEY AUTO_INCREMENT,
                                 antecedents VARCHAR(100),
                                 allergies VARCHAR(100),
                                 historique TEXT,
                                 id_patient INT NOT NULL,
                                 FOREIGN KEY (id_patient) REFERENCES Patient(id_patient) ON DELETE CASCADE
);

-- =============================================
-- Table Consultation
-- =============================================
CREATE TABLE Consultation (
                              id_consultation INT PRIMARY KEY AUTO_INCREMENT,
                              date_consultation DATE NOT NULL,
                              diagnostic VARCHAR(50),
                              notes TEXT,
                              id_dossier INT NOT NULL,
                              id_rendez_vous INT,
                              FOREIGN KEY (id_dossier) REFERENCES DossierMedicale(id_dossier) ON DELETE CASCADE,
                              FOREIGN KEY (id_rendez_vous) REFERENCES Rendez_vous(id_rendez_vous) ON DELETE SET NULL
);

-- =============================================
-- Table Traitement
-- =============================================
CREATE TABLE Traitement (
                            id_traitement INT PRIMARY KEY AUTO_INCREMENT,
                            nom VARCHAR(50) NOT NULL,
                            description TEXT,
                            date_deb DATE,
                            date_fin DATE,
                            type_traitement VARCHAR(50),
                            id_consultation INT NOT NULL,
                            FOREIGN KEY (id_consultation) REFERENCES Consultation(id_consultation) ON DELETE CASCADE
);

-- =============================================
-- Table Avis
-- =============================================
CREATE TABLE Avis (
                      id_avis INT PRIMARY KEY AUTO_INCREMENT,
                      commentaire TEXT,
                      note DOUBLE CHECK (note BETWEEN 0 AND 5),
                      date_avis DATE DEFAULT (CURRENT_DATE),
                      id_patient INT NOT NULL,
                      id_cabinet INT NOT NULL,
                      FOREIGN KEY (id_patient) REFERENCES Patient(id_patient) ON DELETE CASCADE,
                      FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet) ON DELETE CASCADE
);

-- =============================================
-- Tables Abonnement & Paiement
-- =============================================
CREATE TABLE Plan_Abonnement (
                                 id_plan INT PRIMARY KEY AUTO_INCREMENT,
                                 nom VARCHAR(50) NOT NULL,
                                 prix_mensuel INT NOT NULL,
                                 prix_annuel INT NOT NULL,
                                 max_cabinet INT NOT NULL,
                                 max_dentiste INT NOT NULL,
                                 max_secretaire INT NOT NULL,
                                 description TEXT
);

CREATE TABLE Abonnement (
                            id_abonnement INT PRIMARY KEY AUTO_INCREMENT,
                            date_debut DATE NOT NULL,
                            date_fin DATE NOT NULL,
                            etat ENUM('ACTIF','SUSPENDU','EXPIRE') DEFAULT 'ACTIF',
                            type_paiement ENUM('mensuel','annuel') NOT NULL,
                            montant_total INT NOT NULL,
                            id_chef_cabinet INT NOT NULL,
                            id_plan INT NOT NULL,
                            FOREIGN KEY (id_chef_cabinet) REFERENCES Chef_Cabinet(id_chef_cabinet) ON DELETE CASCADE,
                            FOREIGN KEY (id_plan) REFERENCES Plan_Abonnement(id_plan) ON DELETE RESTRICT
);

CREATE TABLE Paiement_Abonnement (
                                     id_paiement INT PRIMARY KEY AUTO_INCREMENT,
                                     montant INT NOT NULL,
                                     mode_paiement ENUM('cash','carte_bancaire','mobile_money') DEFAULT 'cash',
                                     date_paiement DATETIME DEFAULT CURRENT_TIMESTAMP,
                                     id_abonnement INT NOT NULL,
                                     FOREIGN KEY (id_abonnement) REFERENCES Abonnement(id_abonnement) ON DELETE CASCADE
);

-- =============================================
-- Tables Services
-- =============================================
CREATE TABLE SERVICES (
                          id_service INT PRIMARY KEY AUTO_INCREMENT,
                          nom_service VARCHAR(50) NOT NULL,
                          dateCreation DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE ASSIGNATION_CAB_SER (
                                     prix INT NOT NULL,
                                     description VARCHAR(200),
                                     id_service INT NOT NULL,
                                     id_cabinet INT NOT NULL,
                                     PRIMARY KEY (id_service, id_cabinet),
                                     FOREIGN KEY (id_service) REFERENCES SERVICES(id_service) ON DELETE CASCADE,
                                     FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet) ON DELETE CASCADE
);

CREATE TABLE SERVICE_DENTISTE (
                                  id_service INT NOT NULL,
                                  id_dentiste INT NOT NULL,
                                  PRIMARY KEY (id_service, id_dentiste),
                                  FOREIGN KEY (id_service) REFERENCES SERVICES(id_service) ON DELETE CASCADE,
                                  FOREIGN KEY (id_dentiste) REFERENCES Dentiste(id_dentiste) ON DELETE CASCADE
);

-- =============================================
-- Table CHEFCABINET_CABINET
-- =============================================
CREATE TABLE CHEFCABINET_CABINET (
                                     id_chef_cabinet INT NOT NULL,
                                     id_cabinet INT NOT NULL,
                                     PRIMARY KEY (id_chef_cabinet, id_cabinet),
                                     FOREIGN KEY (id_chef_cabinet) REFERENCES Chef_Cabinet(id_chef_cabinet) ON DELETE CASCADE,
                                     FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet) ON DELETE CASCADE
);