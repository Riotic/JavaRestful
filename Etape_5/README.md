# Étape 5 : Mettre en place une interface web interrogeant l'API
Dans cette étape, nous avons créer l'interface web en Angular qui va interroger l'api sur le port 4200.  
Pour lancer le projet il faut au préalable installer les modules requis de l'interface web Angular en allant dans le dossier quest_web_angular et lancer les commandes suivantes.  
```
cd quest_web_angular
npm install
ng serve
```  
Il est important que l'API soit ouverte par la même occasion, l'api étant le dossier quest_web_java. Vous pouvez soit le lancer via un run and debug de votre IDE ou tout simplement le compilé en allant dans le dossier quest_web_java/ et lancer les commandes suivantes en ayant maven et java d'installés:  
```
# Compiler le projet
chmod +x mvnw && ./mvnw clean package -DskipTests
# Lancer le projet compilé
java -jar target/quest_web-0.0.1-SNAPSHOT.jar  
```
Il est important d'avoir l'API d'ouverte de plus de l'interface web pour le bon fonctionnement de l'application.  

## Ajout de nouvelle règle de CORS pour l'API  

Afin que l'application puisse communiquer au mieux avec l'api nous avons défini des règles de CORS permettant la communication au niveau du port 4200. Appliqué par derrière sur le SecurityFilterChain.


![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/CorsRules.png?raw=true)

## L'utilisateur peut s'enregistrer et se connecter

La route pour s'enregistrer a été défini dans /register.
Quand on arrive à l'application une redirection automatique à /login est défini tant que nous ne sommes pas connecté ou que nous avons pas de token JWT ayant une longueur de plus de deux caractères.

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/register.png?raw=true)

Une fois qu'on s'est enregistré il est possible de se connecter.  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/login.png?raw=true)

Une fois qu'on est connecté nous avons accés à l'application avec des alerts nous indiquant ce qui se passe dans l'application. Et on arrive sur le dashboard.
Pour vois les utilisateurs il faudra cliquer sur le User de la navbar pour voir tout les utilisateurs. L'utilisateur connecté si il n'a pas le rôle admin a la possibilité de supprimer son compte ou de le modifier.

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/userDemo1.png?raw=true)

## La connection est obligatoire  

Une règle au sein de l'interface web créer des redirections tant que le token est inférieur à deux caractères.  
![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/tokenAngular17Token.png?raw=true)

Ceci est défini par un interceptor et un auth.guard dans l'application qui redirige sur la page login.  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/authGuard.png?raw=true)

## On doit voir la liste des utilisateurs/ Clliquer sur un utilisateur l'application affiche ses addresses

Quand on clique sur voir les addresses dans la route /user ou les informations d'un utilisateur sont affichés, on est redirigé vers une route qui contient l'id de l'utilisateur concerné qui va montrer les addresses de cet utilisateur.   

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/addressUser.png?raw=true)  

## Un utilisateur normal peut modifier ou supprimer son profil  

Il est possible de modifier son nom d'utilisateur quand nous avons le role user.

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/modifNom.png?raw=true)  

Par logique après modification du nom d'utilisateur le token doit être renouvelé, on a donc une redirection sur la page login avec effacement du token.  

## Un utilisateur normal a le contrôle sur ses adresses (ajout / modification / suppression).

Il est possible de modifier/créer ou supprimer une de nos addresses en tant qu'utilisateurs.

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/listeAddresse.png?raw=true)

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/demoAddresse1.png?raw=true)

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/demoSupprAddr.png?raw=true)

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/listeAddresse.png?raw=true)

Il est bien sur impossible en tant qu'user d'intervenir sur des addresses ne lui appartenant pas.  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/demoNoAdmin.png?raw=true)  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/demoNoAdminAddress.png?raw=true)  

## Un administrateur a tout les droits 

En tant qu'admin vous disposez de tout les droits.  
Pour créer un compte admin il faudra le faire via postMan de préférence ou via curl en CLI si vous désirez. 

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/creationCompteAdmin.png?raw=true)   

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/admin.png?raw=true)  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/modifUser.png?raw=true)  

![alt text](https://github.com/Riotic/JavaRestful/tree/main/imageReadme/Etape_5/supprimerAddAdmin1.png?raw=true)  

