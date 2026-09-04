# Module de Filtres de Domaine

Ce module fournit un système de filtrage avancé pour les requêtes sur les objets du domaine. Il permet de construire des requêtes de recherche dynamiques et complexes.

## Rôle et responsabilités

Le module `domain-filters` a pour mission de :

-   **Définir des structures de requêtes de recherche** : Il contient les classes qui permettent de construire des requêtes de recherche (`SearchQuery`).
-   **Gérer les filtres automatiques et manuels** : Il offre deux approches pour filtrer les données, chacune avec ses propres avantages.

## Technologies utilisées

-   **Java** : Langage de programmation principal.
-   **Lombok** : Pour réduire le code boilerplate.

## Comment utiliser ce module

Ce module est une bibliothèque qui est utilisée par d'autres modules, principalement `gestion-stock-domain` et `gestion-stock-persistence`.

### Recherches manuelles

Les recherches manuelles sont conçues pour des cas d'utilisation spécifiques où les champs de recherche sont connus à l'avance. Elles sont implémentées en étendant la classe abstraite `SearchQuery`.

**Avantages** :

-   **Clarté** : Les champs de recherche sont explicitement définis dans la classe de requête.
-   **Simplicité** : Facile à mettre en œuvre pour des recherches simples.

**Inconvénients** :

-   **Manque de flexibilité** : Ne convient pas aux recherches où les champs peuvent varier.

### Recherches automatiques

Les recherches automatiques sont conçues pour des cas d'utilisation où les champs de recherche sont dynamiques et peuvent être combinés de manière complexe. Elles sont implémentées en utilisant la classe `AutomaticSearchQuery`.

**Avantages** :

-   **Flexibilité** : Permet de construire des requêtes complexes avec des combinaisons de filtres (ET/OU).
-   **Puissance** : Idéal pour les écrans de recherche avancée où l'utilisateur peut choisir les champs à filtrer.

**Inconvénients** :

-   **Complexité** : Plus complexe à mettre en œuvre que les recherches manuelles.

### Différences clés

| Caractéristique | Recherche manuelle | Recherche automatique |
| --- | --- | --- |
| **Flexibilité** | Faible | Élevée |
| **Complexité** | Faible | Élevée |
| **Cas d'utilisation** | Recherches simples et prédéfinies | Recherches complexes et dynamiques |
| **Implémentation** | Étendre `SearchQuery` | Utiliser `AutomaticSearchQuery` |
