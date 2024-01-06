# modelsis-backend-Ahamada

## Exécution de l'application Spring Boot et lancement des tests

### Instructions

1. **Ouvrir le Projet dans IntelliJ IDEA :**
  - Importez le projet dans IntelliJ IDEA en sélectionnant "File" > "Open" et en choisissant le dossier du projet.

2. **Build du Projet :**
  - Assurez-vous que le projet est correctement configuré. Vous pouvez le faire en cliquant avec le bouton droit sur le fichier `pom.xml` à la racine du projet et en sélectionnant "Add as Maven Project". Ensuite, cliquez sur l'icône "Reimport" pour mettre à jour les dépendances.

3. **Exécution de l'Application :**
  - Recherchez la classe principale de l'application (probablement annotée avec `@SpringBootApplication`).
  - Cliquez avec le bouton droit sur la classe principale et sélectionnez "Run" pour démarrer l'application Spring Boot.

4. **Lancement des Tests avec IntelliJ IDEA :**
  - Recherchez le répertoire "src/test" dans votre arborescence de projet.
  - Cliquez avec le bouton droit sur le dossier de tests, puis sélectionnez "Run Tests in 'tests'".
  - Alternativement, vous pouvez cliquer avec le bouton droit sur une classe de test spécifique et sélectionner "Run 'NomDeLaClasseTest'" pour exécuter des tests spécifiques.

5. **Vérification des Résultats des Tests :**
  - Après l'exécution des tests, vous pourrez consulter les résultats dans la fenêtre "Run" d'IntelliJ IDEA.

### Base de données H2 en mémoire

L'application utilise une base de données H2 en mémoire pour le stockage des données.

### ENDPOINT PRINCIPAL

Le point de terminaison principal de l'application est [http://localhost:8080/api/v1].

### Contrôleur des Produits (Product Controller)

Le `ProductController` est responsable de la gestion des opérations CRUD (Create, Read, Update, Delete) pour les produits dans l'application. Voici une explication détaillée des différentes parties du contrôleur :

- **Récupération de tous les produits**
    - Endpoint : GET /products
    - Cette méthode permet de récupérer la liste de tous les produits.

- **Création d'un nouveau produit**
    - Endpoint : POST /products

- **Récupération d'un produit par son identifiant**
    - Endpoint : GET /products/{id}

- **Mise à jour d'un produit existant**
    - Endpoint : PUT /products/{id}

- **Suppression d'un produit par son identifiant**
    - Endpoint : DELETE /products/{id}

### Contrôleur des Types de Produits (ProductType Controller)

Le `ProductTypeController` est responsable de la gestion des opérations CRUD (Create, Read, Update, Delete) pour les types de produits dans l'application. Voici une explication détaillée des différentes parties du contrôleur :

- **Récupération de tous les types de produits**
    - Endpoint : GET /productTypes

- **Création d'un nouveau type de produit**
    - Endpoint : POST /productTypes

- **Récupération d'un type de produit par son identifiant**
    - Endpoint : GET /productTypes/{id}

- **Mise à jour d'un type de produit existant**
    - Endpoint : PUT /productTypes/{id}

- **Suppression d'un type de produit par son identifiant**
    - Endpoint : DELETE /productTypes/{id}
