# Module Domaine de Gestion de Stock

Ce module contient le cœur de la logique métier de l'application de gestion de stock. Il définit les objets du domaine, les services et les règles qui régissent le comportement du système.

## Rôle et responsabilités

Le module `gestion-stock-domain` est responsable de :

-   **Définir les objets du domaine** : Il contient les classes qui représentent les concepts clés de l'application (par exemple, `Produit`, `Stock`, `Commande`).
-   **Implémenter la logique métier** : Il contient les services qui orchestrent les opérations sur les objets du domaine.
-   **Valider les données** : Il assure que les données du domaine sont cohérentes et valides.

## Technologies utilisées

-   **Java** : Langage de programmation principal.
-   **Lombok** : Pour réduire le code boilerplate (getters, setters, etc.).
-   **JUnit** & **AssertJ** : Pour les tests unitaires.

## Comment utiliser ce module

Ce module est une bibliothèque qui est utilisée par d'autres modules, principalement `gestion-stock-application` et `gestion-stock-persistence`. Il n'est pas destiné à être exécuté de manière autonome.

## Dépendances

Ce module dépend des modules suivants :

-   `utilities` : Fournit des classes utilitaires communes.
-   `validation` : Contient la logique de validation.
-   `domain-filters` : Fournit des fonctionnalités de filtrage pour les objets du domaine.
