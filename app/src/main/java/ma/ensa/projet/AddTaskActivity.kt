package ma.ensa.projet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ma.ensa.projet.beans.Task
import ma.ensa.projet.service.TaskDao
import ma.ensa.projet.service.TaskService
import ma.ensa.projetws.to_do_list.R

class AddTaskActivity : AppCompatActivity() {

    private lateinit var taskService: TaskService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Passer une instance de TaskDao à TaskService
        taskService = TaskService(TaskDao())

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val dueDateInput = findViewById<EditText>(R.id.dueDateInput)
        val addButton = findViewById<Button>(R.id.addButton)

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val dueDate = dueDateInput.text.toString()

            // Créer la nouvelle tâche
            val newTask = Task(title = title, description = description, dueDate = dueDate)

            // Ajouter la tâche via le service
            taskService.create(newTask)

            // Retourner la nouvelle tâche à ListTaskActivity
            val resultIntent = Intent()
            resultIntent.putExtra("NEW_TASK", newTask)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Ferme AddTaskActivity et retourne à ListTaskActivity
        }
    }
}
