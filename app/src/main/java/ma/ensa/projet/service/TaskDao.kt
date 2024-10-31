package ma.ensa.projet.service

import android.os.Build
import androidx.annotation.RequiresApi
import ma.ensa.projet.beans.Task
import ma.ensa.projet.dao.IDao

// Classe TaskDao qui implémente l'interface IDao pour gérer les opérations CRUD sur les objets Task
class TaskDao : IDao<Task> {

    // Liste mutable de tâches pour stocker les objets Task
    private val tasks = mutableListOf<Task>()

    // Variable pour générer des identifiants uniques pour chaque tâche
    private var currentId = 1

    // Méthode pour créer une nouvelle tâche et l'ajouter à la liste
    override fun create(item: Task) {
        // Attribuer un identifiant unique à la nouvelle tâche
        item.id = currentId++
        // Ajouter la tâche à la liste
        tasks.add(item)
    }

    // Méthode pour récupérer une tâche par son identifiant
    override fun getById(id: Int): Task? {
        // Rechercher et retourner la tâche correspondant à l'identifiant donné, ou null si non trouvée
        return tasks.find { it.id == id }
    }

    // Méthode pour obtenir la liste de toutes les tâches
    override fun getAll(): List<Task> {
        // Retourner la liste complète des tâches
        return tasks
    }

    // Méthode pour mettre à jour une tâche existante
    override fun update(item: Task) {
        // Trouver l'index de la tâche à mettre à jour en fonction de l'identifiant
        val index = tasks.indexOfFirst { it.id == item.id }
        // Si l'index est valide (différent de -1), mettre à jour la tâche
        if (index != -1) {
            tasks[index] = item
        }
    }

    // Méthode pour supprimer une tâche par son identifiant (disponible pour les versions de SDK Nougat et plus récentes)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun delete(id: Int) {
        // Supprimer toutes les tâches qui ont l'identifiant donné
        tasks.removeIf { it.id == id }
    }
}
