# README

## 1. Prérequis

- **Docker** installé et opérationnel (Docker Desktop ou Docker Engine).
- **Java** (JDK 17 ou +) installé.
- **Gradle ou Maven** (selon la configuration de votre projet Spring Boot), ou bien **IntelliJ IDEA/Eclipse/VSCode** pour lancer l’application.

---

## 2. Mise en place de la base MySQL avec Docker

### 2.1. Récupérer l’image MySQL

```bash
docker pull mysql:8.0
```

Si vous ne l’avez jamais fait, Docker téléchargera l’image mysql:8.0 depuis Docker Hub.

### 2.2. Créer et démarrer un conteneur MySQL

```bash
docker run --name mysql-container \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=db_test \
-p 3306:3306 \
-d mysql:8.0
```

Détails des options :

- --name mysql-container : nom du conteneur.
- -e MYSQL_ROOT_PASSWORD=root : mot de passe root (ne jamais utiliser en prod).
- -e MYSQL_DATABASE=db_test : crée automatiquement la base db_test.
- -p 3306:3306 : expose le port 3306 de la machine hôte vers le conteneur.
- -d : démarre en mode détaché (en arrière-plan).

Pour vérifier le conteneur en cours d’exécution :
```bash
docker exec -it mysql-container bash
mysql -u root -p
# Mot de passe : root
```

Vous pouvez ensuite créer une table, afficher la liste des bases, etc.
Par exemple :
```bash
USE db_test;
SHOW TABLES;
```
## 3. Configuration de l’application Spring Boot

### 3.1. Fichier application.properties (ou application.yml)

Assurez-vous d’avoir les propriétés suivantes (et la dépendance mysql-connector-java dans votre projet) :

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/db_test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root

# Dialect Hibernate pour MySQL
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Créer/Mettre à jour automatiquement le schéma (pour dev)
spring.jpa.hibernate.ddl-auto=update

# Afficher les requêtes SQL dans la console
spring.jpa.show-sql=true
```

Note : En production, il est préférable de désactiver allowPublicKeyRetrieval=true et de ne pas utiliser ddl-auto=update.

### 3.2. Structure du projet

- models (entités JPA)
- repository (interfaces JPA)
- services (logique métier)
- controllers (contrôleurs Spring MVC)
- templates (HTML Thymeleaf, dans src/main/resources/templates/)

Par exemple, un controller ItemController utilisera un service ItemService qui lui-même fera appel au repository ItemRepository.


## 4. Lancement de l’application
1. S’assurer que le conteneur MySQL tourne :

```bash
docker ps
# Doit afficher mysql-container
```

2. Compiler et lancer le projet Spring Boot :

- Via IntelliJ : Exécutez la classe principale (ex. DockerMysqlSpringbootApplication).
- Via Gradle : ./gradlew bootRun (ou gradlew.bat bootRun sous Windows).
- Via Maven : mvn spring-boot:run.

3. Accéder à l’application 
- Ouvrez un navigateur : http://localhost:8080/items
- Vous devriez voir la liste des items.
- Cliquez sur “Créer un nouvel Item” pour ajouter un item (Thymeleaf form).

## 5. Tester les endpoints
- GET http://localhost:8080/items : Affiche la liste des items (page HTML).
- GET http://localhost:8080/items/new : Montre le formulaire d’ajout.
- POST http://localhost:8080/items : Traite la création (en provenance du formulaire).
- GET http://localhost:8080/items/edit/{id} : Montre le formulaire pour éditer un item existant.
- GET http://localhost:8080/items/delete/{id} : Supprime l’item et redirige vers la liste.

## 6. Persistance des données dans Docker (optionnel)
Par défaut, si vous supprimez le conteneur Docker, vous perdez les données. Pour conserver (persister) la base sur votre disque, vous pouvez monter un volume :

```bash
docker run --name mysql-container \
-e MYSQL_ROOT_PASSWORD=root \
-e MYSQL_DATABASE=db_test \
-p 3306:3306 \
-v ~/docker/mysql/data:/var/lib/mysql \
-d mysql:8.0
```

Ainsi, même si vous arrêtez/supprimez le conteneur, les données restent dans ~/docker/mysql/data.

## 7. Conclusion
Vous avez à présent :
- Un conteneur Docker MySQL accessible sur localhost:3306, base db_test.
- Une application Spring Boot (Thymeleaf) connectée à cette base.
- Un CRUD simple sur l’entité Item (create, read, update, delete).

Pour toute modification ou redéploiement :
- Vérifiez toujours que le conteneur MySQL est démarré.
- Lancez ensuite votre application Spring Boot.
- Testez les endpoints ou accédez directement aux pages Thymeleaf.

Enjoy!