
# Étape 3 : Sécurisation de l'application avec JWT

Cette étape vise à sécuriser une application Spring Boot à l'aide de tokens JWT. Dans cette étape, nous mettons en place la sécurité de l'application en utilisant Spring Security et les tokens JWT.

## Configuration de l'environnement

Ajout les dépendances de securité dans le fichier `pom.xml` :

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-api</artifactId>
  <version>0.11.5</version>
</dependency>

<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-impl</artifactId>
  <version>0.11.5</version>
</dependency>

<dependency>
  <groupId>io.jsonwebtoken</groupId>
  <artifactId>jjwt-jackson</artifactId>
  <version>0.11.5</version>
</dependency>
```


1. **Création du package de configuration** :
   - Ajoutez un package nommé `com.quest.etna.config`.
   
2. **Ajout des classes de sécurité** :
   - Création une classe `JwtTokenUtil` pour la gestion des tokens JWT.
   - Création une classe `JwtUserDetails` pour le modèle d'utilisateur JWT.
   - Création une classe `JwtUserDetailsService` pour le service de détails d'utilisateur JWT.
   - Création une classe `JwtRequestFilter` pour le filtre JWT.
   - Création une classe `JwtAuthenticationEntryPoint` pour l'entrypoint d'authentification JWT.
   - Création une classe `SecurityConfig` pour la configuration de sécurité.

3. **Modification du contrôleur d'authentification** :
   - Ajoute une fonction `authenticate` dans `AuthenticationController` pour gérer l'authentification des utilisateurs et la génération de tokens JWT.

4. **Configuration de la sécurité** :
   - Configuration la classe `SecurityConfig` pour activer la sécurité HTTP, désactiver la vérification CSRF, autoriser l'accès aux routes `register` et `authenticate` sans restrictions, et ajouter l'entrypoint d'authentification, l'authentication provider et le filtre `JwtRequestFilter`.

## Accès aux fonctionnalités

- **Endpoint d'authentification** :
  - `POST /authenticate` : Endpoint pour l'authentification des utilisateurs et la génération de tokens JWT.

- **Endpoint pour obtenir les détails de l'utilisateur connecté** :
  - `GET /me` : Endpoint pour récupérer les détails de l'utilisateur connecté.

## Exécution du projet

Pour tester le projet, nous devons assurer que l'application Spring Boot est en cours d'exécution. Nous testons tous les endpoints à l'aide d'un outil tel que Postma pour assurer que tous les reuetes fonctionnent en suivi ces étapes :

### Register** 

*** Enregistre un a nouveau utilisateur : ***
Envoie une request POST avec `/register` endpoint avec username et password vide dans request body pour assurer de bien recevoir 409 :



![alt text](imageReadme/Etape_3/register.png)

Cela va creer un nouveau utilisateur dans la base de donnée.

*** Test de utilisateur déjà existant - 409 : ***

Envoie une request POST avec `/register` endpoint avec username et password dans request body :

![alt text](imageReadme/Etape_3/register-409.png)


### Authenticate 
Envoie une request POST avec `/authenticate` endpoint avec le credentials d'user dans le request body :
```json
POST /authenticate
```
![alt text](imageReadme/Etape_3/authenticate.png)

Celui-ci va returner un JWT token si les credentials sont corrects.

*** Utilise JWT Token pour Authorization ***
on recupere le JWT token de la response de la request authentication.
Envoie une request à l'endpoint protegé avec le JWT token in dans le header d'Authorization header 

Remplace `<JWT_TOKEN>` le JWT token qu'on obtient dans Etape 3.

Puis on verifie la reponse si le token est valid et a les permissions nécessaire, nous devons recevoir une response de l'enpoint protegé : 

![alt text](imageReadme/Etape_3/me.png)


### Invalid Tokens
Si le token est invalide ou expiré, nous devons recevoir une erreur d'autorisation (401). Dans ce cas, nous devons peut-être réauthentifier l'utilisateur ou gérer l'erreur en conséquence :


![alt text](imageReadme/Etape_3/me-401.png)

En suivant ces étapes, nous pouvons tester le flux d'authentification et d'autorisation de l'application en utilisant Postman. Assurez-vous de remplacer les URL des points de terminaison et les charges utiles des requêtes par les valeurs appropriées pour l'application.