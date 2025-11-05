# Gestion Stock - Backend

## SonarQube Quality Gate Status
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=bugs)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=coverage)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=svenglauser_gestion-stock-backend&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=svenglauser_gestion-stock-backend)

## Résumé
- Projet Java multi-module géré avec Maven.
- Modules principaux : `gestion-stock-application`, `gestion-stock-domain`, `gestion-stock-persistence`, `gestion-stock-batch`, `utilities`, `validation`, `report-aggregate`.
- Base de données : PostgreSQL. Conteneurs et scripts disponibles dans `postgresql_scripts`.

## Prérequis
- Java 25+
- Maven 3.9+
- Docker & Docker Compose
- Windows (développement local) — scripts `.bat` fournis

## Structure du dépôt
- `gestion-stock-application/` : application principale (jar exécutable)
- `gestion-stock-domain/` : entités et logique métier
- `gestion-stock-persistence/` : accès aux données
- `gestion-stock-batch/` : traitements batch
- `postgresql_scripts/` : sauvegarde / restauration PostgreSQL

## Build & tests
- Run un conteneur PostgreSQL pour le développement local :  
  ```bash
  docker run --name gestion-stock-db-local -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=root -p 5432:5432 -d postgres
  ```
- Compiler et packager tout le projet :
  ```bash
  mvn -DskipTests=false clean package
  ```
- Compiler sans tests (CI rapide) :
  ```bash
  mvn -DskipTests=true clean package
  ```
- Lancer les tests :
  ```bash
  mvn test
  ```

## Exécution
- Exécuter le jar de l'application :  
  `java -jar gestion-stock-application/target/gestion-stock-application-1.0-SNAPSHOT.jar --spring.profiles.active=dev`
- Avec Docker Compose :  
  `docker-compose up --build`
- Base de données locale : consulter `postgresql_scripts` pour backup / restore (`.bat` et `.sh` fournis)

## Configuration
- Fichiers de configuration : `application.yaml`, `application-dev.yaml`, `application-prod.yaml` dans `gestion-stock-application/src/main/resources` (ou dans `target/classes` après build)
- Variables d'environnement et paramètres Docker : voir `docker-compose.yaml`

## Bonnes pratiques
- Respecter les conventions de commit et de branche (voir `CONTRIBUTING.md`)
- Exécuter les tests locaux avant PR
- Générer la Javadoc pour modifications d'API publiques
- Utiliser des profils Spring pour gérer les configurations spécifiques aux environnements
- Documenter les nouvelles fonctionnalités et les changements majeurs dans le README ou la documentation associée
- Utiliser des outils d'analyse statique de code pour maintenir la qualité du code
- Mettre à jour les dépendances régulièrement pour bénéficier des dernières corrections de bugs et améliorations
