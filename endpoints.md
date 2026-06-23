### Documentation des endpoints

Ce document recense les endpoints REST actuellement exposés par le projet `Tooth-Office-API`, à partir des contrôleurs présents dans `src/main/java/org/odk/tooth_office/Controller`.

### Remarques générales

- Les endpoints sont majoritairement préfixés par `/api`, sauf `TraitementController` qui expose ses routes sous `/traitements`.
- Plusieurs contrôleurs renvoient des `ResponseEntity<...>` avec des codes HTTP explicites.
- `ConsultationController` renvoie un objet métier `Response` et encapsule les erreurs serveur dans un message générique.
- `PrestationController` contient des méthodes annotées, mais n’est **pas exposé** actuellement car il ne porte ni `@RestController` ni `@RequestMapping`.

### 1. Abonnements

Base path : `/api/abonnements`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `POST` | `/api/abonnements` | Crée un abonnement | Corps : `AbonnementDTO` | `201 Created` + `Abonnement` |
| `GET` | `/api/abonnements` | Récupère tous les abonnements | Aucune | `200 OK` + `List<Abonnement>` |
| `GET` | `/api/abonnements/{id}` | Récupère un abonnement par identifiant | `id` (`Integer`, path) | `200 OK` + `Abonnement`, ou `404 Not Found` |
| `GET` | `/api/abonnements/cabinet/{idCabinet}` | Liste les abonnements d’un cabinet | `idCabinet` (`int`, path) | `200 OK` + `List<Abonnement>` |
| `GET` | `/api/abonnements/plan/{idPlan}` | Liste les abonnements d’un plan | `idPlan` (`Long`, path) | `200 OK` + `List<Abonnement>` |
| `PUT` | `/api/abonnements/{id}` | Modifie un abonnement | `id` (`Integer`, path), corps : `AbonnementDTO` | `200 OK` + `Abonnement`, ou `404 Not Found` |
| `PATCH` | `/api/abonnements/{id}/statut` | Change le statut d’un abonnement | `id` (`Integer`, path), `nouveauStatut` (`EtatAbonnement`, query param) | `200 OK` + `Abonnement`, ou `404 Not Found` |
| `DELETE` | `/api/abonnements/{id}` | Supprime un abonnement | `id` (`Integer`, path) | `204 No Content` |

### 2. Admins système

Base path : `/api/admins`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/admins` | Liste tous les admins système | Aucune | `200 OK` + `List<AdminSystemDTO>` |
| `GET` | `/api/admins/{id}` | Récupère un admin système par ID | `id` (`Long`, path) | `200 OK` + `AdminSystemDTO`, ou `404 Not Found` |
| `POST` | `/api/admins` | Crée un admin système | Corps : `AdminSystemDTO` | `201 Created` + `AdminSystemDTO` |
| `PUT` | `/api/admins/{id}` | Met à jour un admin système | `id` (`Long`, path), corps : `AdminSystemDTO` | `200 OK` + `AdminSystemDTO`, ou `404 Not Found` |
| `DELETE` | `/api/admins/{id}` | Supprime un admin système | `id` (`Long`, path) | `204 No Content`, ou `404 Not Found` |

### 3. Chefs de cabinet

Base path : `/api/chefs-cabinet`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/chefs-cabinet` | Liste tous les chefs de cabinet | Aucune | `200 OK` + `List<ChefCabinetDTO>` |
| `GET` | `/api/chefs-cabinet/{id}` | Récupère un chef de cabinet par ID | `id` (`Long`, path) | `200 OK` + `ChefCabinetDTO`, ou `404 Not Found` |
| `POST` | `/api/chefs-cabinet` | Crée un chef de cabinet | Corps : `ChefCabinetDTO` | `201 Created` + `ChefCabinetDTO` |
| `PUT` | `/api/chefs-cabinet/{id}` | Met à jour un chef de cabinet | `id` (`Long`, path), corps : `ChefCabinetDTO` | `200 OK` + `ChefCabinetDTO`, ou `404 Not Found` |
| `DELETE` | `/api/chefs-cabinet/{id}` | Supprime un chef de cabinet | `id` (`Long`, path) | `204 No Content`, ou `404 Not Found` |

### 4. Consultations

Base path : `/api`

> Particularité : ce contrôleur renvoie un objet `Response` personnalisé, pas un `ResponseEntity` standard.

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/consultation/{id}/patient` | Récupère les consultations liées à un patient | `id` (`Long`, path) | `Response` métier ; en cas d’erreur : message `Erreur au niveau du serveur` |
| `GET` | `/api/consultation/{id}/dentiste` | Récupère les consultations liées à un dentiste | `id` (`Long`, path) | `Response` métier ; en cas d’erreur : message `Erreur au niveau du serveur` |
| `POST` | `/api/consultation` | Crée une consultation | Corps : `Consultation` | `Response` métier |
| `GET` | `/api/consultations` | Liste toutes les consultations actives via le service | Aucune | `Response` métier |
| `PUT` | `/api/consultation` | Met à jour une consultation | Corps : `Consultation` | `Response` métier |
| `DELETE` | `/api/consultation/{id}` | Supprime logiquement une consultation | `id` (`Long`, path) | `Response` métier |

### 5. Dossiers médicaux

Base path : `/api/dossiers-medicaux`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `POST` | `/api/dossiers-medicaux` | Crée un dossier médical | Corps : `DossierMedicalDTO` validé (`@Valid`) | `201 Created` + `DossierMedicalDTO` |
| `GET` | `/api/dossiers-medicaux/{id}` | Récupère un dossier médical par ID | `id` (`Long`, path) | `200 OK` + `DossierMedicalDTO` |
| `GET` | `/api/dossiers-medicaux/patient/{patientId}` | Récupère le dossier médical d’un patient | `patientId` (`Long`, path) | `200 OK` + `DossierMedicalDTO` |
| `GET` | `/api/dossiers-medicaux` | Liste tous les dossiers médicaux | Aucune | `200 OK` + `List<DossierMedicalDTO>` |
| `PUT` | `/api/dossiers-medicaux/{id}` | Met à jour un dossier médical | `id` (`Long`, path), corps : `DossierMedicalDTO` validé | `200 OK` + `DossierMedicalDTO` |
| `DELETE` | `/api/dossiers-medicaux/{id}` | Supprime un dossier médical | `id` (`Long`, path) | `204 No Content` |

### 6. Patients

Base path : `/api/patients`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/patients` | Liste tous les patients | Aucune | `200 OK` + `List<PatientDTO>` |
| `GET` | `/api/patients/{id}` | Récupère un patient par ID | `id` (`Long`, path) | `200 OK` + `PatientDTO`, ou `404 Not Found` |
| `POST` | `/api/patients` | Crée un patient | Corps : `PatientDTO` | `201 Created` + `PatientDTO` |
| `PUT` | `/api/patients/{id}` | Met à jour un patient | `id` (`Long`, path), corps : `PatientDTO` | `200 OK` + `PatientDTO`, ou `404 Not Found` |
| `DELETE` | `/api/patients/{id}` | Supprime un patient | `id` (`Long`, path) | `204 No Content`, ou `404 Not Found` |

### 7. Plans d’abonnement

Base path : `/api/plan_abonnement`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `POST` | `/api/plan_abonnement` | Crée un plan d’abonnement | Corps : `PlanAbonnementDTO` | Objet `PlanAbonnementDTO` |
| `PUT` | `/api/plan_abonnement/{id}` | Met à jour un plan d’abonnement | `id` (`Long`, path), corps : `PlanAbonnementDTO` | Objet `PlanAbonnement` |
| `DELETE` | `/api/plan_abonnement/{id}` | Supprime un plan d’abonnement | `id` (`Long`, path) | Aucune réponse explicite (`void`) |
| `GET` | `/api/plan_abonnement/{id}` | Récupère un plan d’abonnement par ID | `id` (`Long`, path) | Objet `PlanAbonnement`, sinon exception `RuntimeException` |
| `GET` | `/api/plan_abonnement` | Liste tous les plans d’abonnement | Aucune | `List<PlanAbonnement>` |

### 8. Secrétaires

Base path : `/api/secretaires`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/secretaires` | Liste toutes les secrétaires | Aucune | `200 OK` + `List<SecretaireDTO>` |
| `GET` | `/api/secretaires/{id}` | Récupère une secrétaire par ID | `id` (`Long`, path) | `200 OK` + `SecretaireDTO`, ou `404 Not Found` |
| `POST` | `/api/secretaires` | Crée une secrétaire | Corps : `SecretaireDTO` | `201 Created` + `SecretaireDTO` |
| `PUT` | `/api/secretaires/{id}` | Met à jour une secrétaire | `id` (`Long`, path), corps : `SecretaireDTO` | `200 OK` + `SecretaireDTO`, ou `404 Not Found` |
| `DELETE` | `/api/secretaires/{id}` | Supprime une secrétaire | `id` (`Long`, path) | `204 No Content`, ou `404 Not Found` |

### 9. Traitements

Base path : `/traitements`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/traitements` | Liste tous les traitements | Aucune | `List<Traitement>` |
| `GET` | `/traitements/{id}` | Récupère un traitement par ID | `id` (`int`, path) | `Traitement` |
| `POST` | `/traitements` | Crée un traitement | Corps : `Traitement` | Retourne l’objet envoyé |
| `PUT` | `/traitements/{id}` | Met à jour un traitement | Corps : `Traitement` ; `id` est présent dans l’URL mais non utilisé dans la signature | Retourne l’objet envoyé |
| `DELETE` | `/traitements/{id}` | Supprime un traitement | `id` (`int`, path) | Aucune réponse explicite (`void`) |

### 10. Utilisateurs

Base path : `/api/utilisateurs`

| Méthode | Endpoint | Description | Entrées | Sortie / Statut |
|---|---|---|---|---|
| `GET` | `/api/utilisateurs` | Liste tous les utilisateurs | Aucune | `200 OK` + `List<UtilisateurDTO>` |
| `GET` | `/api/utilisateurs/{id}` | Récupère un utilisateur par ID | `id` (`Long`, path) | `200 OK` + `UtilisateurDTO`, ou `404 Not Found` |
| `POST` | `/api/utilisateurs` | Crée un utilisateur | Corps : `UtilisateurDTO` | `201 Created` + `UtilisateurDTO` |
| `PUT` | `/api/utilisateurs/{id}` | Met à jour un utilisateur | `id` (`Long`, path), corps : `UtilisateurDTO` | `200 OK` + `UtilisateurDTO`, ou `404 Not Found` |
| `DELETE` | `/api/utilisateurs/{id}` | Supprime un utilisateur | `id` (`Long`, path) | `204 No Content`, ou `404 Not Found` |

### Endpoints présents dans le code mais non exposés

#### `PrestationController`

Le fichier `src/main/java/org/odk/tooth_office/Controller/PrestationController.java` contient les méthodes suivantes :

- `GET /{id}`
- `GET /`
- `PUT /{id}`
- `DELETE /{id}`

Mais cette classe n’est actuellement **pas accessible comme contrôleur REST** car :

- elle n’a pas l’annotation `@RestController` ;
- elle n’a pas de `@RequestMapping` de base ;
- la création (`POST`) est commentée.

Si tu veux, je peux ensuite te générer une version enrichie de ce document avec des exemples de requêtes/réponses JSON pour chaque endpoint.