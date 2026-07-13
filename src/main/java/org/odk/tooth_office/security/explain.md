****### Mise en place de la sécurité dans Tooth Office

Ce projet utilise maintenant `Spring Security + JWT`.

L'objectif est simple :

- permettre à un utilisateur de se connecter avec son `email` et son `mot de passe`
- lui retourner un `token JWT`
- utiliser ce token pour sécuriser les routes de l'API
- limiter l'accès selon le `rôle`

### 1. Qu'est-ce que Spring Security ?

`Spring Security` est le module de Spring qui protège une application.

Il sert à gérer :

- l'authentification : `qui es-tu ?`
- l'autorisation : `qu'as-tu le droit de faire ?`

Dans ce projet, il vérifie que l'utilisateur existe, que son mot de passe est correct, puis que son rôle permet bien d'accéder à une route.

### 2. Qu'est-ce qu'un JWT ?

`JWT` signifie `JSON Web Token`.

C'est une chaîne de caractères signée par le serveur. Elle contient des informations utiles, par exemple :

- l'email de l'utilisateur
- son identifiant
- son rôle

Quand l'utilisateur se connecte, l'API lui renvoie un token.
Ensuite, il doit envoyer ce token dans l'en-tête HTTP suivant :

```http
Authorization: Bearer VOTRE_TOKEN_ICI
```

### 3. Les classes ajoutées

#### `auth/AuthController.java`

Expose les routes :

- `POST /api/auth/login`
- `GET /api/auth/me`
- `POST /api/auth/change-password`

#### `auth/AuthService.java` et `auth/AuthServiceImplementation.java`

Contiennent la logique métier d'authentification :

- vérifier l'email
- vérifier le mot de passe
- vérifier que le compte est actif
- générer le token JWT
- changer le mot de passe

#### `security/SecurityConfig.java`

C'est la configuration principale de sécurité.

Elle définit :

- les routes publiques
- les routes protégées
- le mode `stateless` (pas de session serveur)
- le filtre JWT
- l'encodeur de mot de passe `BCrypt`

#### `security/JwtAuthenticationFilter.java`

Ce filtre s'exécute sur chaque requête.

Il fait ceci :

1. lire l'en-tête `Authorization`
2. vérifier qu'il commence par `Bearer `
3. extraire le token
4. lire l'email contenu dans le token
5. charger l'utilisateur
6. placer l'utilisateur dans le contexte de sécurité

#### `security/JwtService.java`

Cette classe s'occupe de :

- créer les tokens
- extraire les informations d'un token
- vérifier qu'un token est encore valide

#### `security/CustomUserDetailsService.java`

Cette classe relie Spring Security à la base de données.

Elle utilise `UtilisateurRepository.findByEmail(...)` pour charger un utilisateur.

#### `security/CustomUserPrincipal.java`

Spring Security attend un objet spécial appelé `UserDetails`.
Cette classe adapte l'entité `Utilisateur` à ce format.

#### `security/PasswordService.java`

Cette classe aide à :

- hacher les mots de passe
- comparer un mot de passe brut avec un mot de passe haché
- migrer progressivement les anciens mots de passe stockés en clair

#### `security/SecurityExceptionHandler.java`

Elle permet de renvoyer des erreurs JSON plus propres pour :

- `401 Unauthorized`
- `403 Forbidden`

### 4. Pourquoi les mots de passe sont hachés ?

Un mot de passe ne doit jamais être stocké en clair dans la base.

Ici, on utilise `BCrypt`.

Cela veut dire :

- on ne stocke pas le mot de passe original
- on stocke une version transformée et sécurisée
- même si quelqu'un lit la base, il ne voit pas le vrai mot de passe

### 5. Que fait le projet avec les anciens mots de passe en clair ?

Le projet contenait déjà des données de démonstration avec `pass123` en clair.

Pour éviter de casser la connexion immédiatement, la sécurité fait une migration douce :

- si le mot de passe est déjà haché, il est vérifié normalement
- s'il est encore en clair et qu'il correspond, il est automatiquement re-haché au moment de la connexion

Cela permet une transition plus simple pour un projet en cours de construction.

### 6. Les rôles utilisés

Les rôles actuels sont :

- `ADMIN_SYSTEM`
- `CHEF_CABINET`
- `DENTISTE`
- `SECRETAIRE`
- `PATIENT`

Un rôle est transformé par Spring Security en autorité du type :

```text
ROLE_ADMIN_SYSTEM
ROLE_CHEF_CABINET
```

### 7. Les règles d'accès de base

Quelques exemples mis en place :

- `/api/auth/**` : public
- `/swagger-ui/**` et `/v3/api-docs/**` : public
- `/api/admins/**` : réservé à `ADMIN_SYSTEM`
- `/api/utilisateurs/**` : réservé à `ADMIN_SYSTEM`
- `/api/chefs-cabinet/**` : `ADMIN_SYSTEM` ou `CHEF_CABINET`
- `/api/dentistes/**` et `/api/secretaires/**` : `ADMIN_SYSTEM` ou `CHEF_CABINET`

Le reste nécessite au minimum un utilisateur authentifié.

### 8. Différence entre authentification et autorisation

#### Authentification

Répond à la question : `qui est connecté ?`

Exemple :

- email correct
- mot de passe correct
- compte actif

#### Autorisation

Répond à la question : `qu'a-t-il le droit de faire ?`

Exemple :

- un admin système peut gérer les utilisateurs
- un chef de cabinet peut gérer ses équipes
- un patient ne doit pas accéder à l'administration globale

### 9. Comment tester rapidement

#### Étape 1 : se connecter

Faire un `POST` sur :

```text
/api/auth/login
```

avec un body JSON :

```json
{
  "email": "admin@toothoffice.cd",
  "motDePasse": "pass123"
}
```

#### Étape 2 : récupérer le token

La réponse contient un champ `token`.

#### Étape 3 : appeler une route protégée

Ajouter dans les headers :

```http
Authorization: Bearer VOTRE_TOKEN
```

Puis appeler par exemple :

```text
/api/utilisateurs
```

### 10. Concepts importants à retenir

- `stateless` : le serveur ne garde pas de session en mémoire pour chaque utilisateur
- `Bearer token` : le token JWT envoyé dans l'en-tête `Authorization`
- `BCrypt` : algorithme de hashage des mots de passe
- `filter` : composant qui intercepte les requêtes HTTP avant le contrôleur
- `UserDetails` : format attendu par Spring Security pour représenter un utilisateur connecté
- `SecurityContext` : endroit où Spring garde l'utilisateur authentifié pour la requête en cours

### 11. Limites actuelles et améliorations futures

Cette première version sécurise bien la base, mais on pourra aller plus loin :

- ajouter un endpoint de `refresh token`
- ajouter des permissions plus fines que les rôles
- empêcher qu'un utilisateur consulte les données d'un autre utilisateur du même type
- masquer davantage les champs sensibles dans certains DTO métier

### 12. Résumé simple

En une phrase :

`l'utilisateur se connecte, reçoit un JWT, envoie ce JWT sur les requêtes suivantes, et Spring Security décide s'il peut accéder à la ressource selon son rôle.`