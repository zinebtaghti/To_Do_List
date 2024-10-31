package ma.ensa.projet

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import ma.ensa.projet.service.TaskService
import ma.ensa.projetws.to_do_list.R

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var taskService: TaskService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        // Configurer le Toolbar comme ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Activer le bouton de retour
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Gérer l'action de clic sur le bouton retour
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Récupérer les données de la tâche via Intent
        val title = intent.getStringExtra("TASK_TITLE")
        val description = intent.getStringExtra("TASK_DESCRIPTION")
        val dueDate = intent.getStringExtra("TASK_DUE_DATE")
        val isCompleted = intent.getBooleanExtra("TASK_IS_COMPLETED", false)

        if (title != null && description != null && dueDate != null) {
            val titleTextView = findViewById<TextView>(R.id.taskTitle)
            val descriptionTextView = findViewById<TextView>(R.id.taskDescription)
            val dueDateTextView = findViewById<TextView>(R.id.taskDueDate)
            val statusTextView = findViewById<TextView>(R.id.taskStatus)

            titleTextView.text = title
            descriptionTextView.text = description
            dueDateTextView.text = dueDate
            statusTextView.text = if (isCompleted) "Tache complétée" else "Tache non complétée"
        }
    }
}
