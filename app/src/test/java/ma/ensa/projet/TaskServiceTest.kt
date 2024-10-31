import junit.framework.TestCase.assertEquals
import ma.ensa.projet.beans.Task
import ma.ensa.projet.dao.IDao
import ma.ensa.projet.service.TaskService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TaskServiceTest {

    @Mock
    lateinit var taskDao: IDao<Task> // Crée un mock de IDao<Task>

    private lateinit var taskService: TaskService // Crée l'instance à la main

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this) // Initialise les annotations Mockito
        taskService = TaskService(taskDao) // Injecte explicitement le mock de taskDao dans TaskService
    }

    @Test
    fun `test create task`() {
        val task = Task(id = 1, title = "New Task", description = "Description", dueDate = "Tomorrow")

        // Simule l'absence d'exception à l'appel de create()
        doNothing().`when`(taskDao).create(task)

        // Appeler la méthode de TaskService
        taskService.create(task)

        // Vérifier que la méthode create du taskDao a bien été appelée une fois
        verify(taskDao, times(1)).create(task)
    }

    @Test
    fun `test get task by id`() {
        val taskId = 1
        val expectedTask = Task(id = taskId, title = "Test Task", description = "Description", dueDate = "Tomorrow")

        // Définir ce que renvoie getById lorsqu'il est appelé avec taskId
        `when`(taskDao.getById(taskId)).thenReturn(expectedTask)

        // Appeler la méthode de TaskService
        val result = taskService.getById(taskId)

        // Vérifier que la méthode getById du taskDao a été appelée une fois et vérifier le résultat
        verify(taskDao, times(1)).getById(taskId)
        assertEquals(expectedTask, result)
    }

    @Test
    fun `test update task`() {
        val task = Task(id = 1, title = "Update Task", description = "Old Description", dueDate = "Today")

        // Simule l'absence d'exception à l'appel de update()
        doNothing().`when`(taskDao).update(task)

        // Appeler la méthode de TaskService
        taskService.update(task)

        // Vérifier que la méthode update du taskDao a bien été appelée une fois
        verify(taskDao, times(1)).update(task)
    }

    @Test
    fun `test delete task`() {
        val taskId = 1

        // Simule l'absence d'exception à l'appel de delete()
        doNothing().`when`(taskDao).delete(taskId)

        // Appeler la méthode de TaskService
        taskService.delete(taskId)

        // Vérifier que la méthode delete du taskDao a bien été appelée une fois
        verify(taskDao, times(1)).delete(taskId)
    }
}
