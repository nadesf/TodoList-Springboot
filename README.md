# TodoList Spring Boot

Application de gestion de tâches (TodoList) développée avec Spring Boot et Bootstrap 5.

## Prérequis

- Java 17 ou supérieur
- Maven 3.6+
- Docker et Docker Compose (pour le déploiement containerisé)

## Lancement de l'application

### Avec Docker Compose

```bash
docker compose up --build
```

L'application sera accessible à l'adresse : http://localhost:8080

### Avec Maven (développement local)

```bash
mvn spring-boot:run
```

## Fonctionnalités

### Interface Web
- ✅ Ajouter une tâche
- ✅ Marquer une tâche comme terminée/non terminée
- 🗑️ Supprimer une tâche
- 📱 Design responsive avec Bootstrap 5
- 🎨 Thème blanc avec boutons noirs

### API REST

#### Gestion des tâches

- `GET /api/todos` - Récupérer toutes les tâches
- `GET /api/todos?completed=true` - Filtrer par statut
- `GET /api/todos/{id}` - Récupérer une tâche par ID
- `POST /api/todos` - Créer une nouvelle tâche
- `PUT /api/todos/{id}` - Mettre à jour une tâche
- `PATCH /api/todos/{id}/toggle` - Basculer le statut completed
- `DELETE /api/todos/{id}` - Supprimer une tâche

#### Endpoints de test (pour Sentry/monitoring)

- `GET /api/test/health` - Vérifier l'état du service
- `GET /api/test/external-service` - Tester un appel API externe (JSONPlaceholder)
- `GET /api/test/error1` - Provoquer une ArithmeticException (division par zéro)
- `GET /api/test/error2` - Exception gérée avec réponse d'erreur
- `GET /api/test/slow-response` - Réponse lente (3 secondes)
- `GET /api/test/log-error?message=...` - Logger une erreur dans la console
- `GET /api/test/null-pointer` - Provoquer une NullPointerException
- `GET /api/test/custom-error?errorMessage=...` - Exception personnalisée

### Exemples d'utilisation

```bash
# Créer une tâche
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Ma première tâche"}'

# Récupérer toutes les tâches
curl http://localhost:8080/api/todos

# Tester les endpoints de monitoring
curl http://localhost:8080/api/test/health
curl http://localhost:8080/api/test/external-service
curl http://localhost:8080/api/test/error1
curl http://localhost:8080/api/test/log-error?message=Test+erreur
```

## Console H2

~~La console H2 n'est plus disponible car nous utilisons maintenant SQLite.~~

## Base de données SQLite

L'application utilise SQLite pour stocker les tâches de manière persistante.

- Fichier de base de données: `todolist.db`
- Les données persistent entre les redémarrages de l'application
- Aucune configuration supplémentaire nécessaire

## Structure du projet

```
todolist-springboot/
├── src/
│   ├── main/
│   │   ├── java/com/todolist/
│   │   │   ├── TodolistApplication.java
│   │   │   ├── config/
│   │   │   │   └── AppConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── TodoRestController.java
│   │   │   │   ├── TodoWebController.java
│   │   │   │   └── TestController.java
│   │   │   ├── dto/
│   │   │   │   └── TodoRequest.java
│   │   │   ├── model/
│   │   │   │   └── Todo.java
│   │   │   ├── repository/
│   │   │   │   └── TodoRepository.java
│   │   │   └── service/
│   │   │       └── TodoService.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/
│   │       │   └── index.html
│   │       └── static/
│   │           └── css/
│   └── test/
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Technologies utilisées

- **Spring Boot 3.2.1** - Framework Java
- **Spring Data JPA** - Persistance des données
- **SQLite** - Base de données embarquée
- **Sentry** - Monitoring et gestion des erreurs
- **OpenTelemetry** - Observabilité et tracing
- **Thymeleaf** - Moteur de templates
- **Bootstrap 5** - Framework CSS
- **Lombok** - Réduction du code boilerplate
- **Maven** - Gestion des dépendances
- **Docker** - Containerisation
