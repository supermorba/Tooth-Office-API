# Structure du projet Spring Boot Tooth-Office-API

Ce projet est organisé selon une architecture en couches afin de séparer les responsabilités et de faciliter la maintenance du code.

```text
src/
└── main/
    ├── java/
    │   └── org.odk.tooth_office/
    │       ├── Controller/
    │       │   └── ServiceController.java
    │       ├── DTO/
    │       │   └── ServiceDTO.java
    │       ├── Entity/
    │       │   └── Service.java
    │       ├── Repository/
    │       │   └── ServiceRepository.java
    │       ├── Services/
    │       │   ├── Implementations/
    │       │   │   └── ServiceImplementation.java
    │       │   └── Interfaces/
    │       │       └── IService.java
    │       └── ToothOfficeApplication.java
    └── resources/
```

## Controller

Le dossier `Controller` contient les contrôleurs REST.

Ils reçoivent les requêtes HTTP provenant des clients (Postman), valident les données reçues et délèguent le traitement à la couche `Service`.

Exemple :

* `ServiceController.java`

Responsabilités :

* Définition des endpoints (`GET`, `POST`, `PUT`, `DELETE`)
* Réception des paramètres et du corps des requêtes
* Retour des réponses HTTP

---

## DTO (Data Transfer Object)

Le dossier `DTO` contient les objets utilisés pour transporter les données entre le client et le serveur.

Les DTO permettent d'éviter d'exposer directement les entités de la base de données.

Exemple :

* `ServiceDTO.java`

Responsabilités :

* Représentation des données échangées avec l'API
* Validation et adaptation des informations

---

## Entity

Le dossier `Entity` contient les classes représentant les tables de la base de données.

Chaque classe est de ce dossier est annotée avec `@Entity` et mappée sur une table.

Exemple :

* `Service.java`

Responsabilités :

* Définition du modèle de données
* Mapping objet–relationnel (ORM) avec JPA/Hibernate

---

## Repository

Le dossier `Repository` contient les interfaces d'accès aux données.

Ces interfaces héritent  de `JpaRepository`  et permettent d'effectuer les opérations sur la base de données.

Exemple :

* `ServiceRepository.java`

Responsabilités :

* Rechercher des données
* Enregistrer des données
* Modifier ou supprimer des enregistrements

---

## Services

La couche `Services` contient la logique métier de l'application.

Elle est divisée en deux parties :

### Interfaces

Le dossier `Interfaces` contient les méthodes disponibles sans préciser leur implémentation.

Exemple :

* `IService.java`


### Implementations

Le dossier `Implementations` contient les implémentations concrètes des interfaces.

Exemple :

* `ServiceImplementation.java`

Responsabilités :

* Application des règles métier
* Appel des repositories
* Transformation éventuelle entre `Entity` et `DTO`

---

## ToothOfficeApplication

Il s'agit de la classe principale du projet.

Elle est annotée avec `@SpringBootApplication` et constitue le point d'entrée de l'application.

Elle permet notamment de démarrer le serveur Spring Boot.

---

## resources

Le dossier `resources` contient les ressources nécessaires à l'application.

On y retrouve  :

* `application.properties` 
* Les fichiers de configuration
* Les ressources statiques
* Les templates éventuels

---

# Architecture globale

Le flux classique d'une requête est le suivant :

```
Client
    │
    ▼
Controller
    │
    ▼
Service (Interface → Implémentation)
    │
    ▼
Repository
    │
    ▼
Base de données
```

Cette séparation des responsabilités rend le projet plus lisible, plus maintenable et facilite les tests ainsi que les évolutions futures.
