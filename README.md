# Android To-Do List Application

## Description
Cette application Android permet de gérer une liste de tâches avec des fonctionnalités avancées telles que la suppression et la modification par balayage, la recherche dynamique et la mise à jour en temps réel du compteur de tâches. Elle utilise une architecture MVC pour une organisation claire du code et intègre des tests unitaires pour garantir la fiabilité des fonctionnalités principales.

## Fonctionnalités
- **Liste des tâches** : Affiche toutes les tâches ajoutées par l'utilisateur.
- **Ajout d'une tâche** : Permet d'ajouter une nouvelle tâche avec des informations telles que le titre, la description, et la date d'échéance.
- **Suppression/Modification par balayage** : Les tâches peuvent être supprimées ou modifiées en effectuant un balayage (swipe) sur l'élément de la liste.
- **Recherche dynamique** : Permet de rechercher une tâche par titre ou description via une barre de recherche.
- **Marquage comme complétée** : Cochez une case pour marquer une tâche comme complétée ou non.
- **Mise à jour du compteur de tâches** : Affiche le nombre total de tâches et se met à jour automatiquement après chaque modification.
- **Tests unitaires** : Utilisation de JUnit et Mockito pour tester les fonctionnalités principales de l'application, notamment la gestion des tâches dans le `TaskService`.
## Fichiers de Mise en Page XML

L'application utilise plusieurs fichiers XML pour définir l'interface utilisateur de chaque fonctionnalité :

- **activity_splash.xml** : Mise en page de l'écran d'accueil, affiché lors du lancement de l'application pour présenter une introduction visuelle pendant le chargement initial.

- **activity_add_task.xml** : Mise en page pour l'ajout d'une nouvelle tâche, incluant des champs pour le titre, la description, et la date limite.

- **activity_list_task.xml** : Mise en page affichant la liste de toutes les tâches, avec des options pour interagir avec chaque tâche.

- **activity_task_detail.xml** : Mise en page pour l'affichage des détails complets d'une tâche sélectionnée.

- **task_edit_dialog.xml** : Mise en page pour la boîte de dialogue utilisée lors de l'édition des détails d'une tâche.

- **task_item.xml** : Mise en page pour chaque élément de tâche dans la liste des tâches, affichant des informations de base comme le titre et l'état.

Ces fichiers XML définissent l'apparence et la disposition des éléments de l'interface utilisateur dans les différentes sections de l'application.


## Prérequis

- **Android Studio** : Assurez-vous d'avoir Android Studio installé.
- **SDK Android 21 ou supérieur** : Le projet nécessite un SDK minimum de niveau 21.

## Dépendances Utilisées

L'application utilise les dépendances suivantes :

- **Material Components** : Utilisé pour des composants d'interface utilisateur comme les `Snackbar`.
  ```gradle
  implementation 'com.google.android.material:material:1.9.0'
  ## Dépendances Utilisées

- **JUnit** et **Mockito** : Utilisés pour les tests unitaires.
  ```gradle
  testImplementation 'junit:junit:4.13.2'
  testImplementation 'org.mockito:mockito-core:3.11.2'
## Fonctionnalités Avancées

### Suppression et Modification par Balayage

* **Suppression** : Effectuez un balayage vers la droite sur une tâche pour afficher une option de suppression avec confirmation.
* **Modification** : Effectuez un balayage vers la gauche pour ouvrir un dialogue permettant de modifier les informations de la tâche.

### Recherche Dynamique

* Une barre de recherche située en haut de la liste des tâches permet de filtrer les résultats en fonction du titre ou de la description des tâches en temps réel.

### Mise à Jour Automatique du Compteur de Tâches

* Le nombre total de tâches est affiché et se met à jour automatiquement chaque fois qu'une tâche est ajoutée, modifiée ou supprimée.
* ## Activités

L'application comprend plusieurs activités pour gérer les différentes fonctionnalités de gestion des tâches :

- **SplashActivity** : Sert d'écran d'accueil, affiché lors du lancement de l'application pour présenter une introduction visuelle pendant le chargement initial.

- **AddTaskActivity** : Permet aux utilisateurs d'ajouter une nouvelle tâche avec des détails spécifiques (titre, description, date limite, etc.).

- **ListTaskActivity** : Affiche la liste de toutes les tâches dans une interface conviviale, avec des options pour les afficher, les modifier ou les supprimer.

- **TaskDetailActivity** : Montre les détails complets d'une tâche sélectionnée. Les utilisateurs peuvent voir toutes les informations et effectuer des actions comme marquer une tâche comme terminée.

- **SwipeToDeleteCallback** : Gère l'interaction de glissement pour supprimer une tâche de la liste de tâches.

Chaque activité est conçue pour offrir une expérience utilisateur fluide et intuitive dans la gestion des tâches.

## Installation

1. **Clonez ce dépôt** :
   ```bash
   git clone https://github.com/zinebtaghti/To_Do_List.git
2. **Ouvrez le projet dans Android Studio**.

3. **Synchronisez le projet avec Gradle** et compilez l'application.

4. **Exécutez l'application** sur un émulateur ou un appareil physique.

## Tests Unitaires

Les tests unitaires sont situés dans le package `test` et incluent :

* **TaskServiceTest.kt** : Teste les fonctionnalités de `TaskService`, en s'assurant que les tâches sont bien ajoutées, mises à jour et supprimées comme prévu.
* **TaskTest.kt** : Vérifie les propriétés et le comportement de base de la classe `Task`.
Pour exécuter les tests :

1. Cliquez sur le test que vous souhaitez exécuter dans Android Studio.
2. Sélectionnez **Run** pour lancer le test et vérifier son bon fonctionnement.
## Vidéo demonstratif:

https://github.com/user-attachments/assets/28882aea-c0f9-4d02-8c87-bfd96b2b9fb1





