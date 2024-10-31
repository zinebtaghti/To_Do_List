package ma.ensa.projet.beans

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

// Classe de test pour les objets Task
class TaskTest {

    // Test pour vérifier l'initialisation des propriétés d'une tâche
    @Test
    fun `test task initialization`() {
        // Création d'une nouvelle tâche avec des propriétés spécifiques
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "This is a test task",
            dueDate = "Today"
        )

        // Vérification des propriétés initiales de la tâche
        assertEquals(1, task.id)
        assertEquals("Test Task", task.title)
        assertEquals("This is a test task", task.description)
        assertEquals("Today", task.dueDate)
        // La tâche ne doit pas être marquée comme terminée par défaut
        assertFalse(task.isCompleted)
    }

    // Test pour vérifier le changement de statut d'une tâche
    @Test
    fun `test task completion status`() {
        // Création d'une nouvelle tâche avec le statut non complété
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "This is a test task",
            dueDate = "Tomorrow",
            isCompleted = false
        )

        // Vérification que la tâche n'est pas terminée initialement
        assertFalse(task.isCompleted)

        // Marquer la tâche comme terminée
        task.isCompleted = true
        // Vérification que la tâche est maintenant terminée
        assertTrue(task.isCompleted)
    }

    // Test pour vérifier la mise à jour des propriétés d'une tâche
    @Test
    fun `test task update properties`() {
        // Création d'une nouvelle tâche avec des propriétés initiales
        val task = Task(
            id = 1,
            title = "Initial Task",
            description = "Initial Description",
            dueDate = "Today"
        )

        // Mise à jour des propriétés de la tâche
        task.title = "Updated Task"
        task.description = "Updated Description"
        task.dueDate = "Tomorrow"
        task.isCompleted = true

        // Vérification que les propriétés de la tâche ont été mises à jour correctement
        assertEquals("Updated Task", task.title)
        assertEquals("Updated Description", task.description)
        assertEquals("Tomorrow", task.dueDate)
        assertTrue(task.isCompleted)
    }

    // Test pour vérifier l'égalité de deux tâches ayant les mêmes propriétés
    @Test
    fun `test task equality`() {
        // Création de deux tâches avec des propriétés identiques
        val task1 = Task(
            id = 1,
            title = "Task 1",
            description = "Description 1",
            dueDate = "Today"
        )

        val task2 = Task(
            id = 1,
            title = "Task 1",
            description = "Description 1",
            dueDate = "Today"
        )

        // Vérification que les deux tâches sont égales
        assertEquals(task1, task2)
    }
}
