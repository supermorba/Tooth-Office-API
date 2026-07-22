# 📚 Notions ORM / JPA — Guide d'Apprentissage Complet

> Ce document explique en profondeur **toutes les notions ORM** utilisées dans les entités du projet Tooth Office.  
> Pour chaque notion, tu trouveras : la définition, le pourquoi, le code Java du projet, et le SQL qui en résulte.  
> **Public cible :** développeurs apprenant Spring Boot / JPA / Hibernate.

---

## Table des matières

1. [C'est quoi un ORM ?](#1-cest-quoi-un-orm-)
2. [@Entity et @Table](#2-entity-et-table)
3. [@Id et @GeneratedValue — La clé primaire](#3-id-et-generatedvalue--la-clé-primaire)
4. [@Column — Contraintes sur les colonnes](#4-column--contraintes-sur-les-colonnes)
5. [@Enumerated — Stocker un Enum](#5-enumerated--stocker-un-enum)
6. [L'héritage JPA — @Inheritance(JOINED)](#6-lhéritage-jpa--inheritancejoined)
7. [@PrimaryKeyJoinColumn — La clé de l'héritage JOINED](#7-primarykeyjoincolumn--la-clé-de-lhéritage-joined)
8. [@ManyToOne — Relation N:1](#8-manytoone--relation-n1)
9. [@OneToMany — Relation 1:N](#9-onetomany--relation-1n)
10. [@OneToOne — Relation 1:1](#10-onetoone--relation-11)
11. [@ManyToMany — Relation N:N](#11-manytomany--relation-nn)
12. [@JoinColumn — Définir la clé étrangère](#12-joincolumn--définir-la-clé-étrangère)
13. [@JoinTable — Table de jointure M2M](#13-jointable--table-de-jointure-m2m)
14. [mappedBy — Côté propriétaire vs côté inverse](#14-mappedby--côté-propriétaire-vs-côté-inverse)
15. [FetchType — LAZY vs EAGER](#15-fetchtype--lazy-vs-eager)
16. [CascadeType — Propagation des opérations](#16-cascadetype--propagation-des-opérations)
17. [orphanRemoval — Suppression des orphelins](#17-orphanremoval--suppression-des-orphelins)
18. [@EmbeddedId et @Embeddable — Clé primaire composite](#18-embeddedid-et-embeddable--clé-primaire-composite)
19. [@MapsId — Lier la clé composite aux entités](#19-mapsid--lier-la-clé-composite-aux-entités)
20. [Bidirectionnel vs Unidirectionnel](#20-bidirectionnel-vs-unidirectionnel)
21. [Lombok dans les entités](#21-lombok-dans-les-entités)
22. [Récapitulatif visuel](#22-récapitulatif-visuel)

---

## 1. C'est quoi un ORM ?

**ORM** = **Object-Relational Mapping** (Mapping Objet-Relationnel).

C'est un outil qui fait le **pont entre le monde Java (objets) et le monde SQL (tables)**.

Sans ORM, tu écris du SQL à la main :
```sql
-- Sans ORM
INSERT INTO Cabinet (nom_cabinet, tel) VALUES ('Cabinet Sow', '771234567');
SELECT * FROM Cabinet WHERE id_cabinet = 1;
```

Avec ORM (JPA + Hibernate), tu travailles uniquement avec des objets Java :
```java
// Avec ORM
Cabinet cabinet = new Cabinet();
cabinet.setNomCabinet("Cabinet Sow");
cabinet.setTel("771234567");
cabinetRepository.save(cabinet); // Hibernate génère le SQL tout seul
```

**JPA** (Java Persistence API) est la spécification (le contrat / l'interface).  
**Hibernate** est l'implémentation concrète utilisée par Spring Boot.

```
┌──────────────┐        ┌──────────────┐        ┌──────────────┐
│  Code Java   │ ──────► │  Hibernate   │ ──────► │  Base SQL    │
│  (Objets)    │        │  (Traduit)   │        │  (Tables)    │
└──────────────┘        └──────────────┘        └──────────────┘
```

---

## 2. @Entity et @Table

### `@Entity`
Déclare qu'une classe Java est une **entité persistante** — c'est-à-dire qu'elle sera mappée sur une table en base de données.

```java
// Dans Cabinet.java
@Entity                     // ← Cette classe = une table en BDD
@Table(name = "Cabinet")    // ← Le nom exact de la table SQL
public class Cabinet {
    ...
}
```

Sans `@Entity`, Hibernate **ignore complètement** la classe.

### `@Table(name = "...")`
Permet de choisir le **nom exact de la table SQL**.  
Sans `@Table`, Hibernate utilise le nom de la classe comme nom de table (ex : `Cabinet` → table `cabinet` ou `Cabinet` selon la config).

| Annotation Java         | Table SQL créée          |
|-------------------------|--------------------------|
| `@Table(name = "Cabinet")` | `Cabinet`             |
| `@Table(name = "Rendez_vous")` | `Rendez_vous`     |
| `@Table(name = "SERVICES")` | `SERVICES`           |
| `@Table(name = "Plan_Abonnement")` | `Plan_Abonnement` |

> **Bonne pratique :** Toujours spécifier `@Table(name = "...")` pour rester maître du nom SQL, surtout si les conventions diffèrent entre Java (CamelCase) et SQL (snake_case ou MAJUSCULES).

---

## 3. @Id et @GeneratedValue — La clé primaire

### `@Id`
Marque l'attribut comme **clé primaire (PK)** de la table.

```java
// Dans Cabinet.java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_cabinet")
private Integer idCabinet;
```

### `@GeneratedValue`
Définit **comment la PK est générée automatiquement**.

| Stratégie              | Fonctionnement                                               | Utilisé dans le projet |
|------------------------|--------------------------------------------------------------|------------------------|
| `IDENTITY`             | La BDD auto-incrémente (ex : MySQL `AUTO_INCREMENT`)         | ✅ Partout             |
| `SEQUENCE`             | Utilise une séquence SQL (PostgreSQL, Oracle)                | ❌                     |
| `TABLE`                | Hibernate gère un compteur dans une table dédiée            | ❌                     |
| `AUTO`                 | Hibernate choisit selon la BDD                              | ❌                     |

SQL généré par `IDENTITY` :
```sql
CREATE TABLE Cabinet (
    id_cabinet INT AUTO_INCREMENT PRIMARY KEY,
    ...
);
```

> **Remarque :** `Utilisateur` utilise `Long` pour sa PK car les sous-classes héritent de cet ID. Les autres entités autonomes (`Cabinet`, `Consultation`, etc.) utilisent `Integer`.

---

## 4. @Column — Contraintes sur les colonnes

`@Column` permet de **configurer finement la colonne SQL** correspondant à un attribut Java.

```java
// Dans Utilisateur.java
@Column(unique = true, nullable = false, length = 100)
private String email;

@Column(length = 255)
private String adresse;

@Column(name = "date_naissance")
private LocalDate dateNaissance;  // Dans Patient.java

@Column(columnDefinition = "TEXT")
private String notes;  // Dans RendezVous.java
```

### Paramètres importants de `@Column`

| Paramètre          | Effet SQL                                | Exemple du projet                       |
|--------------------|------------------------------------------|-----------------------------------------|
| `name = "..."`     | Renomme la colonne SQL                   | `name = "date_rdv"` → colonne `date_rdv` |
| `nullable = false` | Ajoute `NOT NULL` en SQL                 | `email`, `nom`, `prenom`               |
| `unique = true`    | Ajoute une contrainte `UNIQUE`           | `email`, `tel` du Cabinet              |
| `length = 50`      | Définit `VARCHAR(50)`                    | `String nom` → `VARCHAR(50)`           |
| `columnDefinition = "TEXT"` | Force le type SQL `TEXT`       | `notes`, `historique`, `commentaire`   |

SQL résultant pour `email` :
```sql
email VARCHAR(100) NOT NULL UNIQUE
```

> **Sans `@Column`**, Hibernate applique des valeurs par défaut : `nullable = true`, `length = 255` pour les String, nom de colonne = nom de l'attribut Java.

---

## 5. @Enumerated — Stocker un Enum

Les Enums Java ne sont pas directement un type SQL. `@Enumerated` dit à Hibernate **comment les convertir**.

```java
// Dans Utilisateur.java
@Enumerated(EnumType.STRING)   // ← Stocke "ACTIF", "SUSPENDU"...
private StatutCompte statutCompte;

// Dans RendezVous.java
@Enumerated(EnumType.STRING)
@Column(name = "etat_rdv")
private EtatRdv etatRdv = EtatRdv.EN_ATTENTE;  // valeur par défaut
```

### `EnumType.STRING` vs `EnumType.ORDINAL`

| Mode              | Valeur stockée en BDD     | Problème             |
|-------------------|---------------------------|----------------------|
| `ORDINAL`         | `0`, `1`, `2`...          | ⚠️ Si tu réordonnes l'enum, les données en BDD deviennent fausses ! |
| `STRING`          | `"ACTIF"`, `"SUSPENDU"`   | ✅ Stable, lisible, sûr |

```sql
-- Avec STRING : colonne ENUM ou VARCHAR
etat_rdv VARCHAR(20) DEFAULT 'EN_ATTENTE'
```

> **Règle d'or :** Utilise **toujours `EnumType.STRING`** en production. C'est le choix fait dans tout ce projet.

---

## 6. L'héritage JPA — @Inheritance(JOINED)

L'héritage en Java (`extends`) peut être représenté en BDD de **3 façons** différentes avec JPA. Le projet utilise `JOINED`.

### Les 3 stratégies d'héritage

| Stratégie              | Structure SQL                              | Avantages / Inconvénients           |
|------------------------|--------------------------------------------|-------------------------------------|
| `SINGLE_TABLE`         | 1 seule table pour toute la hiérarchie     | Rapide, mais beaucoup de colonnes nulles |
| `JOINED`               | 1 table par classe (parente + enfants)     | ✅ Propre, normalisé, mais nécessite des JOINs |
| `TABLE_PER_CLASS`      | 1 table complète par sous-classe           | Pas de JOINs, mais duplication des colonnes communes |

### Ce que fait `JOINED` dans ce projet

```java
// Dans Utilisateur.java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // ← Stratégie choisie
@Table(name = "Utilisateur")
public class Utilisateur {
    @Id
    private Long id_utilisateur;
    private String nom;
    private String email;
    ...
}
```

```java
// Dans Patient.java
@Entity
@Table(name = "Patient")
@PrimaryKeyJoinColumn(name = "id_patient")  // ← Clé de liaison
public class Patient extends Utilisateur {
    private LocalDate dateNaissance;
    ...
}
```

**Tables SQL générées :**
```sql
CREATE TABLE Utilisateur (
    id_utilisateur BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(20),
    ...
);

CREATE TABLE Patient (
    id_patient BIGINT PRIMARY KEY,           -- PK = FK vers Utilisateur
    date_naissance DATE,
    FOREIGN KEY (id_patient) REFERENCES Utilisateur(id_utilisateur)
);
```

**Requête générée par Hibernate quand on charge un `Patient` :**
```sql
SELECT u.nom, u.email, p.date_naissance
FROM Patient p
JOIN Utilisateur u ON p.id_patient = u.id_utilisateur
WHERE p.id_patient = 1;
```

> **Résumé :** Pour récupérer un `Patient` complet, Hibernate fait un `JOIN` automatique entre `Patient` et `Utilisateur`. C'est le coût de la stratégie `JOINED` : plus propre mais une requête plus lourde.

---

## 7. @PrimaryKeyJoinColumn — La clé de l'héritage JOINED

```java
// Dans Dentiste.java
@PrimaryKeyJoinColumn(name = "id_dentiste")
public class Dentiste extends Utilisateur {
```

Cette annotation spécifie le **nom de la colonne PK/FK** dans la table enfant.

- `id_dentiste` est la PK de la table `Dentiste`
- `id_dentiste` est **aussi** une FK vers `Utilisateur.id_utilisateur`
- Ce n'est PAS un `AUTO_INCREMENT` : la valeur vient de la table `Utilisateur`

```sql
-- Insertion d'un dentiste :
-- 1. D'abord dans Utilisateur (génère l'ID, ex: 42)
INSERT INTO Utilisateur (nom, email, ...) VALUES ('Diallo', 'diallo@mail.com', ...);
-- 2. Ensuite dans Dentiste avec le MÊME id (42)
INSERT INTO Dentiste (id_dentiste, specialite, id_cabinet) VALUES (42, 'Orthodontiste', 3);
```

---

## 8. @ManyToOne — Relation N:1

`@ManyToOne` représente la relation **"plusieurs vers un"**.  
C'est la relation la plus courante. Elle génère une **clé étrangère (FK)** dans la table de l'entité qui porte l'annotation.

### Exemple : Dentiste → Cabinet

```java
// Dans Dentiste.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet", nullable = false)
private Cabinet cabinet;
```

**Lecture :** "Plusieurs Dentistes appartiennent à UN seul Cabinet."

```sql
-- Table Dentiste aura une colonne FK
ALTER TABLE Dentiste ADD COLUMN id_cabinet INT NOT NULL;
ALTER TABLE Dentiste ADD FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet);
```

### Autres exemples dans le projet

```java
// Secretaire → Cabinet ET → ChefCabinet
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet")
private Cabinet cabinet;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_chef_cabinet")
private ChefCabinet chefCabinet;

// RendezVous → Patient, Dentiste, Secretaire, Creneau
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_patient", nullable = false)
private Patient patient;

// Traitement → Consultation
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_consultation", nullable = false)
private Consultation consultation;
```

> **Règle :** `@ManyToOne` génère toujours une FK **dans la table de la classe qui le déclare**.

---

## 9. @OneToMany — Relation 1:N

`@OneToMany` est **l'inverse** de `@ManyToOne`. Elle représente le côté "un" de la relation.

```java
// Dans Cabinet.java
@OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = false)
private List<Dentiste> dentistes = new ArrayList<>();
```

**Lecture :** "Un Cabinet a PLUSIEURS Dentistes."

### Points essentiels

1. **`@OneToMany` ne crée PAS de colonne** dans la table `Cabinet`.  
   La FK (`id_cabinet`) est dans la table `Dentiste`, déclarée par le `@ManyToOne`.

2. **`mappedBy = "cabinet"`** indique que c'est l'attribut `cabinet` dans `Dentiste` qui porte la FK.  
   Sans `mappedBy`, Hibernate créerait une table de jointure inutile.

3. L'initialisation à `new ArrayList<>()` est une bonne pratique pour éviter les `NullPointerException`.

```java
// Dans DossierMedicale.java
@OneToMany(mappedBy = "dossierMedicale", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Consultation> consultations = new ArrayList<>();
```

```java
// Dans Creneau.java
@OneToMany(mappedBy = "creneau", cascade = CascadeType.ALL)
private List<RendezVous> rendezVousList = new ArrayList<>();
```

---

## 10. @OneToOne — Relation 1:1

Relation **exclusive entre deux entités** : chaque instance de A correspond à exactement une instance de B.

### Cas 1 : DossierMedicale → Patient (propriétaire de la FK)

```java
// Dans DossierMedicale.java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_patient", nullable = false, unique = true)
private Patient patient;
```

Ici `DossierMedicale` **possède** la FK. La colonne `id_patient` (UNIQUE + NOT NULL) est dans la table `DossierMedicale`.

```sql
CREATE TABLE DossierMedicale (
    id_dossier INT PRIMARY KEY,
    id_patient BIGINT NOT NULL UNIQUE,   -- FK unique → relation 1:1
    FOREIGN KEY (id_patient) REFERENCES Patient(id_patient)
);
```

### Cas 2 : RendezVous → Consultation (bidirectionnel)

```java
// Dans RendezVous.java (côté inverse, pas de FK ici)
@OneToOne(mappedBy = "rendezVous", cascade = CascadeType.ALL)
private Consultation consultation;

// Dans Consultation.java (côté propriétaire, la FK est ici)
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_rendez_vous")
private RendezVous rendezVous;
```

La FK `id_rendez_vous` est dans la table `Consultation`.

> **Règle :** Dans un `@OneToOne` bidirectionnel, **l'entité qui a `@JoinColumn` possède la FK**. L'autre a `mappedBy`.

---

## 11. @ManyToMany — Relation N:N

Relation **plusieurs-à-plusieurs** : A peut être lié à plusieurs B, et B peut être lié à plusieurs A.  
En SQL, cela nécessite **obligatoirement une table de jointure** intermédiaire.

### Cas simple : Patient ↔ Cabinet

```java
// Dans Patient.java (côté propriétaire de la jointure)
@ManyToMany
@JoinTable(
    name = "PATIENT_CABINET",
    joinColumns = @JoinColumn(name = "id_patient"),
    inverseJoinColumns = @JoinColumn(name = "id_cabinet")
)
private List<Cabinet> cabinets = new ArrayList<>();

// Dans Cabinet.java (côté inverse)
@ManyToMany(mappedBy = "cabinets")
private List<Patient> patients = new ArrayList<>();
```

```sql
-- Table de jointure générée
CREATE TABLE PATIENT_CABINET (
    id_patient BIGINT,
    id_cabinet INT,
    PRIMARY KEY (id_patient, id_cabinet),
    FOREIGN KEY (id_patient) REFERENCES Patient(id_patient),
    FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet)
);
```

### Cas avancé : M2M enrichie avec entité intermédiaire

Quand la table de jointure a des **colonnes supplémentaires** (ex: `prix`, `description`), on ne peut pas utiliser un simple `@ManyToMany`. On crée une **entité intermédiaire** :

```java
// CabinetService.java — entité intermédiaire entre Cabinet et Service
@Entity
@Table(name = "ASSIGNATION_CAB_SER")
public class CabinetService {
    @EmbeddedId
    private CabinetServiceId id;   // PK composite

    @ManyToOne @MapsId("idService")
    private Service service;

    @ManyToOne @MapsId("idCabinet")
    private Cabinet cabinet;

    private Integer prix;          // ← Attribut supplémentaire
    private String description;    // ← Attribut supplémentaire
}
```

```sql
CREATE TABLE ASSIGNATION_CAB_SER (
    id_service INT,
    id_cabinet INT,
    prix INT NOT NULL,
    description VARCHAR(200),
    PRIMARY KEY (id_service, id_cabinet)
);
```

> **Règle :** Dès qu'une table de jointure a des colonnes supplémentaires, transforme-la en **entité à part entière** avec `@EmbeddedId`.

---

## 12. @JoinColumn — Définir la clé étrangère

`@JoinColumn` précise le **nom de la colonne FK** dans la table SQL et ses contraintes.

```java
// Dans Dentiste.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet", nullable = false)  // FK obligatoire
private Cabinet cabinet;

// Dans Secretaire.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_chef_cabinet")   // FK optionnelle (nullable = true par défaut)
private ChefCabinet chefCabinet;
```

| Paramètre          | Effet                                            |
|--------------------|--------------------------------------------------|
| `name = "..."`     | Nom de la colonne FK en SQL                     |
| `nullable = false` | La FK ne peut pas être nulle (`NOT NULL`)        |
| `unique = true`    | Contrainte UNIQUE sur la FK (pour `@OneToOne`)  |

Sans `@JoinColumn`, Hibernate génère un nom automatique peu lisible (ex: `cabinet_id_cabinet`).

---

## 13. @JoinTable — Table de jointure M2M

`@JoinTable` configure **manuellement la table de jointure** créée pour un `@ManyToMany`.

```java
// Dans ChefCabinet.java
@ManyToMany
@JoinTable(
    name = "CHEFCABINET_CABINET",                            // Nom de la table de jointure
    joinColumns = @JoinColumn(name = "id_chef_cabinet"),    // FK vers la table courante
    inverseJoinColumns = @JoinColumn(name = "id_cabinet")  // FK vers l'autre table
)
private List<Cabinet> cabinets = new ArrayList<>();
```

```sql
CREATE TABLE CHEFCABINET_CABINET (
    id_chef_cabinet BIGINT,
    id_cabinet INT,
    PRIMARY KEY (id_chef_cabinet, id_cabinet),
    FOREIGN KEY (id_chef_cabinet) REFERENCES Chef_Cabinet(id_chef_cabinet),
    FOREIGN KEY (id_cabinet) REFERENCES Cabinet(id_cabinet)
);
```

---

## 14. mappedBy — Côté propriétaire vs côté inverse

C'est l'une des notions les plus importantes (et les plus confuses) de JPA.

### Le problème

Dans une relation **bidirectionnelle**, les deux classes se référencent mutuellement. Mais en SQL, la FK n'existe que **dans une seule table**. JPA doit savoir qui est responsable de cette FK.

### La règle

- **Côté propriétaire** (`owner`) : celui qui a `@JoinColumn` → **possède la FK** → contrôle réellement la relation en BDD.
- **Côté inverse** (`inverse`) : celui qui a `mappedBy` → **ne gère pas la FK** → sert uniquement à la navigation en Java.

```java
// Dans Cabinet.java (côté INVERSE — pas de FK ici)
@OneToMany(mappedBy = "cabinet",   // ← "cabinet" = nom de l'attribut dans Dentiste
           cascade = CascadeType.ALL)
private List<Dentiste> dentistes;

// Dans Dentiste.java (côté PROPRIÉTAIRE — la FK est ici)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet", nullable = false)  // ← FK dans la table Dentiste
private Cabinet cabinet;
```

### ⚠️ Erreur classique

Si tu ajoutes un `Dentiste` uniquement du côté `Cabinet` (côté inverse), la relation **ne sera pas sauvegardée** en BDD !

```java
// ❌ MAUVAIS : Modification du côté inverse — Hibernate l'ignore
cabinet.getDentistes().add(dentiste);
cabinetRepository.save(cabinet);  // La FK id_cabinet dans Dentiste reste null !

// ✅ BON : Modification du côté propriétaire
dentiste.setCabinet(cabinet);
dentisteRepository.save(dentiste);  // La FK id_cabinet est mise à jour
```

---

## 15. FetchType — LAZY vs EAGER

Définit **quand** Hibernate charge les données liées depuis la BDD.

### `FetchType.LAZY` (chargement paresseux)

```java
// Dans Dentiste.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet", nullable = false)
private Cabinet cabinet;
```

Avec `LAZY` : quand tu charges un `Dentiste`, Hibernate **ne charge pas** le `Cabinet` immédiatement.  
Le `Cabinet` n'est chargé que si tu appelles `dentiste.getCabinet()` (déclenchement d'une nouvelle requête SQL).

```sql
-- Chargement du Dentiste (LAZY)
SELECT * FROM Dentiste WHERE id_dentiste = 1;  -- Cabinet PAS chargé

-- Plus tard, si tu appelles dentiste.getCabinet() :
SELECT * FROM Cabinet WHERE id_cabinet = 3;    -- Chargé seulement maintenant
```

### `FetchType.EAGER` (chargement immédiat)

```sql
-- Chargement du Dentiste (EAGER)
SELECT d.*, c.*
FROM Dentiste d
JOIN Cabinet c ON d.id_cabinet = c.id_cabinet
WHERE d.id_dentiste = 1;   -- Cabinet chargé D'EMBLÉE
```

### Valeurs par défaut JPA

| Annotation    | Défaut si non précisé |
|---------------|-----------------------|
| `@ManyToOne`  | `EAGER`               |
| `@OneToOne`   | `EAGER`               |
| `@OneToMany`  | `LAZY`                |
| `@ManyToMany` | `LAZY`                |

> **Bonne pratique :** Utilise **toujours `FetchType.LAZY`** explicitement comme dans ce projet. `EAGER` peut causer de gros problèmes de performance (ex : charger 10 000 patients quand on veut juste le nom du cabinet).

---

## 16. CascadeType — Propagation des opérations

`CascadeType` dit à Hibernate : **"quand tu fais X sur l'entité parent, fais-le aussi sur les enfants"**.

```java
// Dans DossierMedicale.java
@OneToMany(mappedBy = "dossierMedicale", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Consultation> consultations = new ArrayList<>();
```

### Les types de cascade

| CascadeType   | Propagation                                             | Exemple dans le projet                        |
|---------------|---------------------------------------------------------|-----------------------------------------------|
| `PERSIST`     | `save()` sur le parent → `save()` sur les enfants      | —                                             |
| `MERGE`       | `update()` sur le parent → `update()` sur les enfants  | —                                             |
| `REMOVE`      | `delete()` sur le parent → `delete()` sur les enfants  | —                                             |
| `REFRESH`     | `refresh()` sur le parent → `refresh()` sur les enfants| —                                             |
| `DETACH`      | `detach()` sur le parent → `detach()` sur les enfants  | —                                             |
| `ALL`         | Toutes les opérations ci-dessus                        | `DossierMedicale → Consultation`, `Consultation → Traitement` |

### Exemples dans le projet

```java
// Cabinet → CabinetService : cascade ALL + orphanRemoval
// Si on supprime Cabinet, tous ses CabinetService sont supprimés
@OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = true)
private List<CabinetService> services;

// Cabinet → Dentiste : cascade ALL MAIS pas orphanRemoval
// Si on supprime Cabinet, les Dentiste sont supprimés MAIS
// si on retire un Dentiste de la liste, il reste en BDD (orphanRemoval = false)
@OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL, orphanRemoval = false)
private List<Dentiste> dentistes;

// RendezVous → Consultation : cascade ALL
// Créer un RendezVous peut aussi créer sa Consultation
@OneToOne(mappedBy = "rendezVous", cascade = CascadeType.ALL)
private Consultation consultation;
```

---

## 17. orphanRemoval — Suppression des orphelins

`orphanRemoval = true` supprime automatiquement un enfant de la BDD lorsqu'il est **retiré de la collection du parent**.

```java
// Dans Consultation.java
@OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Traitement> traitements = new ArrayList<>();
```

### Différence entre `cascade = REMOVE` et `orphanRemoval`

```java
// Cas 1 : Supprimer la Consultation → supprime aussi les Traitements
// (cascade = REMOVE le fait)
consultationRepository.delete(consultation);   // → DELETE FROM Traitement WHERE id_consultation = X

// Cas 2 : Retirer UN Traitement de la liste (orphanRemoval)
consultation.getTraitements().remove(traitement);  // avec orphanRemoval = true
consultationRepository.save(consultation);          // → DELETE FROM Traitement WHERE id_traitement = Y
```

Sans `orphanRemoval`, le `remove()` de la liste n'aurait **aucun effet en BDD** : le traitement resterait en base avec une FK `id_consultation` orpheline.

> **Règle :** Utilise `orphanRemoval = true` quand les enfants **n'ont pas de sens sans leur parent** (un traitement sans consultation, une consultation sans dossier médical…).

---

## 18. @EmbeddedId et @Embeddable — Clé primaire composite

Quand une table a une **PK composée de plusieurs colonnes**, on utilise `@EmbeddedId`.

### Étape 1 : Créer la classe de la clé composite (`@Embeddable`)

```java
// CabinetServiceId.java
@Embeddable                  // ← Peut être intégrée dans une entité
@EqualsAndHashCode           // ← OBLIGATOIRE pour que JPA fonctionne correctement
@NoArgsConstructor
@AllArgsConstructor
public class CabinetServiceId implements Serializable {  // ← OBLIGATOIRE
    private Integer idService;
    private Integer idCabinet;
}
```

> `implements Serializable` est **exigé par la spec JPA** pour les classes embeddables servant de clé.  
> `@EqualsAndHashCode` est **exigé** pour que Hibernate puisse comparer les clés (savoir si deux entités sont identiques).

### Étape 2 : L'utiliser dans l'entité

```java
// CabinetService.java
@Entity
@Table(name = "ASSIGNATION_CAB_SER")
public class CabinetService {

    @EmbeddedId                     // ← PK composite intégrée
    private CabinetServiceId id;

    @ManyToOne @MapsId("idService")
    private Service service;

    @ManyToOne @MapsId("idCabinet")
    private Cabinet cabinet;

    private Integer prix;
}
```

```sql
CREATE TABLE ASSIGNATION_CAB_SER (
    id_service  INT,
    id_cabinet  INT,
    prix        INT NOT NULL,
    description VARCHAR(200),
    PRIMARY KEY (id_service, id_cabinet)  -- ← PK composite
);
```

---

## 19. @MapsId — Lier la clé composite aux entités

`@MapsId` fait le lien entre **un champ de la PK composite** et **la FK vers l'entité associée**.

```java
// Dans CabinetService.java
@EmbeddedId
private CabinetServiceId id;  // contient idService et idCabinet

@ManyToOne(fetch = FetchType.LAZY)
@MapsId("idService")          // ← "idService" = nom du champ dans CabinetServiceId
@JoinColumn(name = "id_service")
private Service service;

@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@MapsId("idCabinet")          // ← "idCabinet" = nom du champ dans CabinetServiceId
@JoinColumn(name = "id_cabinet")
private Cabinet cabinet;
```

### Pourquoi `@MapsId` ?

Sans `@MapsId`, tu aurais deux colonnes séparées :
- `id.idService` (dans la PK)  
- `service.id_service` (la FK)

Ce serait une duplication. `@MapsId` dit à Hibernate : **"la FK et la partie de la PK, c'est la même colonne"**.

### Utilisation pratique

```java
// Pour créer un CabinetService :
CabinetServiceId csId = new CabinetServiceId(service.getIdService(), cabinet.getIdCabinet());
CabinetService cs = new CabinetService();
cs.setId(csId);          // Setter la PK composite
cs.setService(service);  // @MapsId synchronise automatiquement cs.id.idService
cs.setCabinet(cabinet);  // @MapsId synchronise automatiquement cs.id.idCabinet
cs.setPrix(15000);
cabinetServiceRepository.save(cs);
```

---

## 20. Bidirectionnel vs Unidirectionnel

### Relation Unidirectionnelle

Un seul côté voit l'autre. Exemple :

```java
// Avis.java — unidirectionnelle : Avis voit Patient, mais Patient ne voit pas Avis
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_patient", nullable = false)
private Patient patient;
```

`Patient` n'a **aucun attribut** `List<Avis> avis`. On ne peut naviguer que dans un sens : `avis.getPatient()`.

### Relation Bidirectionnelle

Les deux côtés se voient mutuellement. Exemple :

```java
// Cabinet.java (côté inverse)
@OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL)
private List<Dentiste> dentistes;

// Dentiste.java (côté propriétaire)
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_cabinet", nullable = false)
private Cabinet cabinet;
```

On peut naviguer dans les deux sens :
- `cabinet.getDentistes()` → liste des dentistes du cabinet
- `dentiste.getCabinet()` → le cabinet du dentiste

### Tableau récapitulatif dans le projet

| Relation                          | Type              | Bidirectionnel ? |
|-----------------------------------|-------------------|------------------|
| `Cabinet ↔ Dentiste`              | 1:N               | ✅ Oui           |
| `Cabinet ↔ Secretaire`            | 1:N               | ✅ Oui           |
| `Cabinet ↔ CabinetService`        | 1:N               | ✅ Oui           |
| `Cabinet ↔ Patient`               | N:N               | ✅ Oui           |
| `Patient → Avis`                  | N:1 (Avis côté)   | ❌ Non           |
| `Cabinet → Avis`                  | N:1 (Avis côté)   | ❌ Non           |
| `DossierMedicale ↔ Patient`       | 1:1               | ❌ Non (DM → Patient) |
| `RendezVous ↔ Consultation`       | 1:1               | ✅ Oui           |
| `PlanAbonnement ↔ Abonnement`     | 1:N               | ✅ Oui           |
| `Abonnement ↔ PaiementAbonnement` | 1:N               | ✅ Oui           |
| `Creneau ↔ RendezVous`            | 1:N               | ✅ Oui           |

---

## 21. Lombok dans les entités

Lombok génère automatiquement du code Java répétitif (boilerplate). Voici les annotations utilisées dans ce projet :

```java
// Présent sur TOUTES les entités
@NoArgsConstructor   // Génère : public Cabinet() {}
@AllArgsConstructor  // Génère : public Cabinet(Integer id, String nom, ...) {...}
@Getter              // Génère : getNomCabinet(), getTel(), getAdresse()...
@Setter              // Génère : setNomCabinet(...), setTel(...)...
```

### `@EqualsAndHashCode` — sur les classes de PK composite uniquement

```java
// CabinetServiceId.java et DentisteServiceId.java
@EqualsAndHashCode  // Génère equals() et hashCode() basés sur tous les champs
public class CabinetServiceId implements Serializable {
    private Integer idService;
    private Integer idCabinet;
}
```

`@EqualsAndHashCode` est **indispensable** sur les clés composites. JPA en a besoin pour :
- Comparer deux entités dans ses caches internes
- Vérifier si une entité existe déjà

> **Attention :** N'utilise pas `@EqualsAndHashCode` sur les entités JPA elles-mêmes (classes avec `@Entity`). Ça peut causer des problèmes de boucles infinies si les relations sont bidirectionnelles.

---

## 22. Récapitulatif visuel

### Tableau de toutes les annotations ORM du projet

| Annotation                    | Rôle                                              | Où ?                              |
|-------------------------------|---------------------------------------------------|-----------------------------------|
| `@Entity`                     | Déclare une table JPA                             | Toutes les entités                |
| `@Table(name="...")`          | Nomme la table SQL                                | Toutes les entités                |
| `@Id`                         | Clé primaire                                      | Toutes les entités                |
| `@GeneratedValue(IDENTITY)`   | Auto-incrément BDD                                | Toutes les entités (sauf sous-classes JOINED) |
| `@Column(...)`                | Contraintes de colonne                            | Partout                           |
| `@Enumerated(STRING)`         | Stockage texte d'un Enum                          | `Utilisateur`, `RendezVous`, `Abonnement`, `PaiementAbonnement` |
| `@Inheritance(JOINED)`        | Stratégie d'héritage                              | `Utilisateur`                     |
| `@PrimaryKeyJoinColumn`       | PK/FK de la sous-classe JOINED                    | `Patient`, `Dentiste`, `Secretaire`, `ChefCabinet`, `AdminSystem` |
| `@ManyToOne`                  | Relation N:1 (FK dans cette table)                | `Dentiste`, `Secretaire`, `Creneau`, `RendezVous`, `Traitement`, `Abonnement`, `PaiementAbonnement`, `Avis`, `CabinetService`, `DentisteService` |
| `@OneToMany`                  | Relation 1:N (FK dans l'autre table)              | `Cabinet`, `DossierMedicale`, `Consultation`, `Creneau`, `PlanAbonnement`, `Abonnement` |
| `@OneToOne`                   | Relation 1:1                                      | `DossierMedicale`, `Consultation`, `RendezVous` |
| `@ManyToMany`                 | Relation N:N                                      | `Patient`, `Cabinet`, `ChefCabinet` |
| `@JoinColumn(name="...")`     | Définit la colonne FK                             | Avec `@ManyToOne`, `@OneToOne`    |
| `@JoinTable(...)`             | Définit la table de jointure M2M                  | `Patient`, `ChefCabinet`          |
| `mappedBy = "..."`            | Côté inverse d'une relation bidirectionnelle      | `Cabinet`, `Creneau`, `RendezVous`, `PlanAbonnement`, `Abonnement`, `DossierMedicale`, `Consultation` |
| `FetchType.LAZY`              | Chargement différé                                | Tous les `@ManyToOne`             |
| `cascade = ALL`               | Propagation de toutes les opérations              | Plusieurs `@OneToMany`, `@OneToOne` |
| `orphanRemoval = true`        | Supprime les enfants retirés de la collection     | `Cabinet → CabinetService`, `DossierMedicale → Consultation`, `Consultation → Traitement`, `PlanAbonnement → Abonnement`, `Abonnement → PaiementAbonnement` |
| `@EmbeddedId`                 | PK composite dans une entité                      | `CabinetService`, `DentisteService` |
| `@Embeddable`                 | Classe utilisable comme PK composite              | `CabinetServiceId`, `DentisteServiceId` |
| `@MapsId("...")`              | Lie un champ de la PK composite à une FK          | `CabinetService`, `DentisteService` |

---

### Flux de lecture pour comprendre une entité

Quand tu lis un fichier `.java` d'entité, voici l'ordre d'analyse recommandé :

```
1. @Entity + @Table          → Quel est le nom de la table ?
2. @Id + @GeneratedValue     → Comment est gérée la PK ?
3. @PrimaryKeyJoinColumn     → Est-ce une sous-classe héritée ?
4. @Column(...)              → Quelles contraintes sur les colonnes ?
5. @Enumerated(...)          → Y a-t-il des Enums ?
6. @ManyToOne / @JoinColumn  → Quelles FK cette table possède-t-elle ?
7. @OneToMany (mappedBy)     → Quelles collections navigables ?
8. @OneToOne                 → Y a-t-il une relation exclusive ?
9. @ManyToMany / @JoinTable  → Y a-t-il une table de jointure ?
10. FetchType + cascade      → Comment se comportent les chargements/suppressions ?
```
