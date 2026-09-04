# Module de Persistance de Gestion de Stock

Ce module est responsable de l'accès aux données et de la persistance pour l'application de gestion de stock. Il utilise Spring Data JPA pour interagir avec la base de données.

## Rôle et responsabilités

Le module `gestion-stock-persistence` a pour mission de :

-   **Gérer les entités JPA** : Il définit les classes qui mappent les objets du domaine aux tables de la base de données.
-   **Fournir des repositories** : Il expose des interfaces Spring Data JPA pour effectuer des opérations CRUD et des requêtes personnalisées.
-   **Gérer les transactions** : Il assure l'intégrité des données lors des opérations de persistance.

## Technologies utilisées

-   **Spring Data JPA** : Framework pour simplifier l'accès aux données relationnelles.
-   **Hibernate** : Implémentation JPA utilisée par défaut.
-   **Lombok** : Pour réduire le code boilerplate.

## Comment utiliser ce module

Ce module est utilisé par `gestion-stock-application` pour accéder aux données. Il n'est pas destiné à être utilisé directement par les clients de l'API.

## Dépendances

Ce module dépend des modules suivants :

-   `gestion-stock-domain` : Fournit les objets du domaine à persister.
-   `persistence-filters` : Fournit des filtres pour les requêtes de persistance.
