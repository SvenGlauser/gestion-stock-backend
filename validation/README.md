# Module de Validation

Ce module est responsable de la validation des données pour l'application de gestion de stock. Il fournit des outils pour s'assurer que les données sont correctes et cohérentes.

## Rôle et responsabilités

Le module `validation` a pour mission de :

-   **Définir des règles de validation** : Il contient des classes qui implémentent des règles de validation spécifiques (par exemple, format d'email, plage de valeurs).
-   **Valider les objets du domaine** : Il est utilisé pour valider les objets du domaine avant leur persistance ou leur traitement.

## Technologies utilisées

-   **Java** : Langage de programmation principal.
-   **Apache Commons Validator** : Pour les règles de validation communes.
-   **Lombok** : Pour réduire le code boilerplate.

## Comment utiliser ce module

Ce module est une bibliothèque qui est utilisée par d'autres modules, principalement `gestion-stock-domain`. Il n'est pas destiné à être exécuté de manière autonome.

## Dépendances

Ce module dépend du module suivant :

-   `utilities` : Fournit des classes utilitaires communes.
