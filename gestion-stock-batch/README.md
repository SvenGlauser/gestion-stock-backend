# Module Batch de Gestion de Stock

Ce module est responsable de l'exécution des traitements par lots (batch processing) pour l'application de gestion de stock. Il utilise Spring Batch pour gérer les tâches longues et répétitives.

## Rôle et responsabilités

Le module `gestion-stock-batch` a pour mission de :

-   **Exécuter des tâches planifiées** : Il permet de lancer des traitements à des moments précis (par exemple, la nuit).
-   **Traiter de gros volumes de données** : Il est conçu pour gérer efficacement des opérations sur de grandes quantités de données.
-   **Assurer la fiabilité des traitements** : Il offre des mécanismes de reprise sur erreur et de suivi de l'exécution des tâches.

## Technologies utilisées

-   **Spring Batch** : Framework pour le traitement par lots.
-   **Spring Boot** : Pour la configuration et l'exécution des tâches.
-   **Lombok** : Pour réduire le code boilerplate.

## Comment utiliser ce module

Ce module est intégré à l'application principale (`gestion-stock-application`) et est exécuté en arrière-plan. Les tâches batch peuvent être déclenchées manuellement ou planifiées via des cron jobs.

## Dépendances

Ce module dépend des modules suivants :

-   `gestion-stock-domain` : Fournit les objets du domaine à traiter.
