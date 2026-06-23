-- Jeu de données initial pour Tooth Office
-- Idempotent: relancer le script ne doit pas dupliquer les enregistrements clés.

-- 1) Plans d'abonnement
INSERT INTO plan_abonnement
    (id_plan, nom, prix_mensuel, prix_annuel, max_cabinet, max_dentistes, max_secretaires, description)
VALUES
    (1, 'STARTER', 49.99, 499.99, 1, 3, 2, 'Plan de démarrage pour petit cabinet'),
    (2, 'PRO', 99.99, 999.99, 3, 10, 6, 'Plan professionnel multi-cabinets'),
    (3, 'ENTERPRISE', 199.99, 1999.99, 10, 30, 20, 'Plan entreprise pour réseau de cliniques')
ON DUPLICATE KEY UPDATE id_plan = id_plan;

-- 2) Cabinets
INSERT INTO cabinet
    (id_cabinet, nom_cabinet, tel, adresse, logo, description)
VALUES
    (1, 'Cabinet Smile Center', '243810000001', 'Kinshasa - Gombe', 'smile-center.png', 'Cabinet dentaire principal'),
    (2, 'Cabinet Dent Plus', '243810000002', 'Kinshasa - Ngaliema', 'dent-plus.png', 'Cabinet secondaire'),
    (3, 'Cabinet Oral Care', '243810000003', 'Kinshasa - Limete', 'oral-care.png', 'Cabinet orienté soins esthétiques')
ON DUPLICATE KEY UPDATE id_cabinet = id_cabinet;

-- 3) Utilisateurs (table mère)
INSERT INTO utilisateur
    (id_utilisateur, nom, prenom, email, mpd, adresse, role, telephone, statut_compte, created_at, updated_at, created_by, updated_by)
VALUES
    (1, 'Mukendi', 'Sarah', 'chef@toothoffice.cd', 'pass123', 'Kinshasa', 'CHEF_CABINET', '243970000001', 'VALIDE', '2026-01-01', '2026-01-01 09:00:00', 'seed', 'seed'),
    (2, 'Kabasele', 'Jean', 'dentiste@toothoffice.cd', 'pass123', 'Kinshasa', 'DENTISTE', '243970000002', 'VALIDE', '2026-01-01', '2026-01-01 09:05:00', 'seed', 'seed'),
    (3, 'Ilunga', 'Nadia', 'secretaire@toothoffice.cd', 'pass123', 'Kinshasa', 'SECRETAIRE', '243970000003', 'VALIDE', '2026-01-01', '2026-01-01 09:10:00', 'seed', 'seed'),
    (4, 'Mwamba', 'David', 'patient@toothoffice.cd', 'pass123', 'Kinshasa', 'PATIENT', '243970000004', 'VALIDE', '2026-01-01', '2026-01-01 09:15:00', 'seed', 'seed'),
    (5, 'Kasongo', 'Aline', 'chef2@toothoffice.cd', 'pass123', 'Kinshasa', 'CHEF_CABINET', '243970000005', 'VALIDE', '2026-01-02', '2026-01-02 10:00:00', 'seed', 'seed'),
    (6, 'Tshibanda', 'Patrick', 'dentiste2@toothoffice.cd', 'pass123', 'Kinshasa', 'DENTISTE', '243970000006', 'VALIDE', '2026-01-02', '2026-01-02 10:05:00', 'seed', 'seed'),
    (7, 'Banza', 'Ruth', 'secretaire2@toothoffice.cd', 'pass123', 'Kinshasa', 'SECRETAIRE', '243970000007', 'VALIDE', '2026-01-02', '2026-01-02 10:10:00', 'seed', 'seed'),
    (8, 'Mpiana', 'Grace', 'patient2@toothoffice.cd', 'pass123', 'Kinshasa', 'PATIENT', '243970000008', 'VALIDE', '2026-01-02', '2026-01-02 10:15:00', 'seed', 'seed'),
    (9, 'Kimpa', 'Roger', 'patient3@toothoffice.cd', 'pass123', 'Kinshasa', 'PATIENT', '243970000009', 'VALIDE', '2026-01-02', '2026-01-02 10:20:00', 'seed', 'seed'),
    (10, 'Admin', 'Systeme', 'admin@toothoffice.cd', 'pass123', 'Kinshasa', 'CHEF_CABINET', '243970000010', 'VALIDE', '2026-01-02', '2026-01-02 10:25:00', 'seed', 'seed')
ON DUPLICATE KEY UPDATE id_utilisateur = id_utilisateur;

-- 4) Tables d'héritage JOINED
INSERT INTO chef_cabinet (id_chef_cabinet)
VALUES (1), (5)
ON DUPLICATE KEY UPDATE id_chef_cabinet = id_chef_cabinet;

INSERT INTO dentiste (id_dentiste, specialite, id_cabinet)
VALUES (2, 'Orthodontie', 1),
       (6, 'Endodontie', 2)
ON DUPLICATE KEY UPDATE id_dentiste = id_dentiste;

INSERT INTO secretaire (id_secretaire, id_cabinet, id_chef_cabinet)
VALUES (3, 1, 1),
       (7, 2, 5)
ON DUPLICATE KEY UPDATE id_secretaire = id_secretaire;

INSERT INTO patient (id_patient, date_naissance)
VALUES (4, '1995-04-14'),
       (8, '1992-08-20'),
       (9, '1988-02-11')
ON DUPLICATE KEY UPDATE id_patient = id_patient;

INSERT INTO admin_system (id_admin, niveau_privilege, date_derniere_connexion)
VALUES (10, 'SUPER_ADMIN', '2026-06-20 18:30:00')
ON DUPLICATE KEY UPDATE id_admin = id_admin;

-- 5) Relations M:N de gestion de cabinet
INSERT INTO chefcabinet_cabinet (id_chef_cabinet, id_cabinet)
VALUES (1, 1), (1, 2), (5, 2), (5, 3)
ON DUPLICATE KEY UPDATE id_chef_cabinet = id_chef_cabinet;

INSERT INTO patient_cabinet (id_patient, id_cabinet)
VALUES (4, 1), (8, 1), (8, 2), (9, 3)
ON DUPLICATE KEY UPDATE id_patient = id_patient;

-- 6) Dossier médical et rendez-vous
INSERT INTO dossier_medical
    (id, antecedents, allergies, historiques, id_patient)
VALUES
    (1, 'Aucun antécédent majeur', 'Aucune allergie connue', 'Première visite annuelle', 4),
    (2, 'Hypertension légère', 'Pénicilline', 'Suivi trimestriel', 8),
    (3, 'Diabète type 2', 'Aspirine', 'Contrôles réguliers', 9)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO rendez_vous
    (id_rendez_vous, date_rdv, motif, notes, etat_rdv, type_rdv, id_patient, id_dentiste, created_at, updated_at)
VALUES
    (1, '2026-06-20 10:00:00', 'Contrôle', 'Contrôle semestriel', 'EN_ATTENTE', 'ENLIGNE', 4, 2, '2026-06-18 08:00:00', '2026-06-18 08:00:00'),
    (2, '2026-06-21 11:30:00', 'Détartrage', 'Patient sensible au froid', 'VALIDE', 'SURPLACE', 8, 2, '2026-06-18 09:00:00', '2026-06-19 08:10:00'),
    (3, '2026-06-22 14:00:00', 'Douleur molaire', 'Urgence modérée', 'FAIT', 'SURPLACE', 9, 6, '2026-06-19 07:45:00', '2026-06-22 15:00:00')
ON DUPLICATE KEY UPDATE id_rendez_vous = id_rendez_vous;

INSERT INTO creneau
    (id_creneau, date, heure_debut, heure_fin, disponible, dentiste_id)
VALUES
    (1, '2026-06-20', '10:00:00', '10:30:00', false, 2),
    (2, '2026-06-21', '11:30:00', '12:00:00', false, 2),
    (3, '2026-06-22', '14:00:00', '14:45:00', false, 6),
    (4, '2026-06-23', '09:00:00', '09:30:00', true, 6)
ON DUPLICATE KEY UPDATE id_creneau = id_creneau;

INSERT INTO consultation
    (id, date_consultation, update_at, diagnostic, notes, is_enabled, dossier_medical_id, rendez_vous_id_rendez_vous, dentiste_id_dentiste)
VALUES
    (1, '2026-06-20 10:20:00', '2026-06-20 10:45:00', 'RAS', 'Contrôle correct, hygiène recommandée', true, 1, 1, 2),
    (2, '2026-06-21 11:50:00', '2026-06-21 12:10:00', 'Tartre léger', 'Planifier un contrôle dans 6 mois', true, 2, 2, 2),
    (3, '2026-06-22 14:20:00', '2026-06-22 15:10:00', 'Carie profonde', 'Traitement requis', true, 3, 3, 6)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO traitement
    (id_traitement, type, description, duree)
VALUES
    (1, 'DETARTRAGE', 'Nettoyage complet des surfaces dentaires', 30),
    (2, 'SOIN_CARIE', 'Traitement d\'une carie molaire', 45),
    (3, 'CONTROLE', 'Examen clinique standard', 20)
ON DUPLICATE KEY UPDATE id_traitement = id_traitement;

INSERT INTO prestation
    (id, nom_prestation, date_creation)
VALUES
    (1, 'Consultation générale', '2026-01-01'),
    (2, 'Blanchiment', '2026-01-01'),
    (3, 'Extraction dentaire', '2026-01-01')
ON DUPLICATE KEY UPDATE id_prestation = id_prestation;

INSERT INTO cabinet_prestation
    (id, prix, description, cabinet_id_cabinet, prestation_id_prestation)
VALUES
    (1, 25.0, 'Consultation standard', 1, 1),
    (2, 120.0, 'Blanchiment premium', 2, 2),
    (3, 80.0, 'Extraction simple', 3, 3)
ON DUPLICATE KEY UPDATE id = id;

INSERT INTO avis
    (id, note, description, create_at, id_cabinet, id_patient)
VALUES
    (1, 5, 'Très bon accueil et prise en charge rapide.', '2026-06-20 16:00:00', 1, 4),
    (2, 4, 'Soin efficace, délai d\'attente raisonnable.', '2026-06-21 17:00:00', 2, 8),
    (3, 5, 'Dentiste très professionnel.', '2026-06-22 18:00:00', 3, 9)
ON DUPLICATE KEY UPDATE id = id;

-- 7) Abonnement actif pour le cabinet principal
INSERT INTO abonnement
    (id_abonnement, date_debut, date_fin, etat_abonnement, type_paiement, montant_total, id_plan, id_cabinet)
VALUES
    (1, '2026-01-01', '2026-12-31', 'ACTIF', 'ANNUEL', 999, 2, 1),
    (2, '2026-02-01', '2027-01-31', 'ACTIF', 'MENSUEL', 1200, 3, 2),
    (3, '2026-03-01', '2027-02-28', 'EXPIRE', 'ANNUEL', 499, 1, 3)
ON DUPLICATE KEY UPDATE id_abonnement = id_abonnement;
