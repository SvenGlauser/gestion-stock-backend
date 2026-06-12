# Module de Filtres de Persistance

Ce module est responsable de la traduction des requêtes de recherche du domaine en requêtes de base de données. Il utilise les spécifications JPA pour construire dynamiquement des requêtes SQL.

## Rôle et responsabilités

Le module `persistence-filters` a pour mission de :

-   **Traduire les `SearchQuery` en spécifications JPA** : Il convertit les requêtes de recherche du domaine en `Specification<T>` pour une utilisation avec Spring Data JPA.
-   **Gérer la pagination** : Il prend en charge la pagination des résultats de recherche.
-   **Appliquer les filtres à la couche de persistance** : Il intègre les filtres de recherche directement dans les requêtes de base de données pour des performances optimales.

## Technologies utilisées

-   **Java** : Langage de programmation principal.
-   **Spring Data JPA** : Pour l'utilisation des spécifications JPA.
-   **Lombok** : Pour réduire le code boilerplate.

## Comment utiliser ce module

Ce module est une bibliothèque qui est utilisée par `gestion-stock-persistence` pour implémenter les fonctionnalités de recherche. Il n'est pas destiné à être utilisé directement par les autres modules.

## Dépendances

Ce module dépend des modules suivants :

-   `validation` : Fournit la logique de validation.
-   `domain-filters` : Fournit les structures de requêtes de recherche.
