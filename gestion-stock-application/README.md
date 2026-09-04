# Module d'Application de Gestion de Stock

Ce module est le point d'entrée principal de l'application de gestion de stock. Il s'agit d'une application Spring Boot qui expose des API REST pour interagir avec le système.

## Rôle et responsabilités

Le module `gestion-stock-application` a plusieurs responsabilités clés :

-   **Exposition des API REST** : Il contient les contrôleurs (`@RestController`) qui définissent les points de terminaison de l'API pour les clients.
-   **Sécurité** : Il intègre Spring Security pour sécuriser les API, en utilisant OAuth2 et un serveur de ressources.
-   **Configuration de l'application** : Il gère la configuration de l'application, y compris les profils Spring (`dev`, `prod`) et la connexion à la base de données.
-   **Point d'entrée de l'application** : Il contient la classe principale (`GestionStockApplication`) qui lance l'application.

## Technologies utilisées

-   **Spring Boot** : Framework principal pour le développement de l'application.
-   **Spring Web** : Pour la création des API REST.
-   **Spring Security** : Pour la gestion de la sécurité.
-   **Spring Data JPA** : (via le module `gestion-stock-persistence`) pour l'accès aux données.
-   **PostgreSQL** : Base de données utilisée en production.
-   **H2** : Base de données en mémoire pour les tests.
-   **Liquibase** : Pour la gestion des migrations de la base de données.
-   **Maven** : Pour la gestion des dépendances et le build du projet.

## Comment utiliser ce module

### Lancer l'application

Pour lancer l'application, vous pouvez utiliser la commande Maven suivante à la racine du projet :

```bash
mvn spring-boot:run -pl gestion-stock-application
```

### Profils Spring

Le module est configuré avec deux profils Spring :

-   `dev` : Profil par défaut, activé pour le développement. Il utilise généralement une configuration de base de données locale ou en mémoire.
-   `prod` : Profil pour la production. Il est activé lorsque l'application est déployée dans un environnement de production.

Pour lancer l'application avec un profil spécifique, vous pouvez utiliser la propriété `spring.profiles.active` :

```bash
mvn spring-boot:run -pl gestion-stock-application -Dspring-boot.run.profiles=prod
```

## Dépendances

Ce module dépend des autres modules du projet :

-   `gestion-stock-domain` : Contient les objets du domaine métier.
-   `gestion-stock-persistence` : Gère la persistance des données.
-   `gestion-stock-batch` : Contient les traitements batch de l'application.
