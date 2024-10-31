package ma.ensa.projet.service

import ma.ensa.projet.beans.Task
import ma.ensa.projet.dao.IDao

// Classe TaskService qui implémente l'interface IDao et utilise un IDao générique pour déléguer les opérations CRUD
class TaskService(private val taskDao: IDao<Task>) : IDao<Task> {

    // Méthode pour créer une nouvelle tâche en utilisant l'objet DAO fourni
    override fun create(item: Task) {
        taskDao.create(item)
    }

    // Méthode pour récupérer une tâche par son identifiant en utilisant l'objet DAO fourni
    override fun getById(id: Int): Task? {
        return taskDao.getById(id)
    }

    // Méthode pour obtenir la liste de toutes les tâches en utilisant l'objet DAO fourni
    override fun getAll(): List<Task> {
        return taskDao.getAll()
    }

    // Méthode pour mettre à jour une tâche existante en utilisant l'objet DAO fourni
    override fun update(item: Task) {
        taskDao.update(item)
    }

    // Méthode pour supprimer une tâche par son identifiant en utilisant l'objet DAO fourni
    override fun delete(id: Int) {
        taskDao.delete(id)
    }
}
