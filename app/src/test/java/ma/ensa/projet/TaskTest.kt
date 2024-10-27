package ma.ensa.projet.beans

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TaskTest {

    @Test
    fun `test task initialization`() {
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "This is a test task",
            dueDate = "Today"
        )
        assertEquals(1, task.id)
        assertEquals("Test Task", task.title)
        assertEquals("This is a test task", task.description)
        assertEquals("Today", task.dueDate)
        assertFalse(task.isCompleted)
    }

    @Test
    fun `test task completion status`() {
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "This is a test task",
            dueDate = "Tomorrow",
            isCompleted = false
        )
        assertFalse(task.isCompleted)

        task.isCompleted = true
        assertTrue(task.isCompleted)
    }

    @Test
    fun `test task update properties`() {
        val task = Task(
            id = 1,
            title = "Initial Task",
            description = "Initial Description",
            dueDate = "Today"
        )

        // Updating task properties
        task.title = "Updated Task"
        task.description = "Updated Description"
        task.dueDate = "Tomorrow"
        task.isCompleted = true

        assertEquals("Updated Task", task.title)
        assertEquals("Updated Description", task.description)
        assertEquals("Tomorrow", task.dueDate)
        assertTrue(task.isCompleted)
    }

    @Test
    fun `test task equality`() {
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

        assertEquals(task1, task2)
    }
}
