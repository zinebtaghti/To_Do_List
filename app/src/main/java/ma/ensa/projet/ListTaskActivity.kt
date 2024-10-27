package ma.ensa.projetws.to_do_list

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ma.ensa.projet.AddTaskActivity
import ma.ensa.projet.TaskDetailActivity
import ma.ensa.projet.adapter.TaskAdapter
import ma.ensa.projet.beans.Task
import ma.ensa.projet.service.TaskDao
import ma.ensa.projet.service.TaskService
import ma.ensa.projet.util.SwipeToDeleteCallback

class ListTaskActivity : AppCompatActivity() {

    private lateinit var taskService: TaskService
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskCountTextView: TextView
    private lateinit var searchTasksEditText: EditText // Champ de recherche
    private var allTasks: List<Task> = listOf() // Liste complète des tâches
    private val REQUEST_CODE_ADD_TASK = 1 // Code de requête pour ajouter une tâche

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)

        // Initialiser TaskService avec une instance de TaskDao
        taskService = TaskService(TaskDao())

        val taskRecyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        taskCountTextView = findViewById(R.id.taskCount)
        searchTasksEditText = findViewById(R.id.searchTasks) // Initialiser le champ de recherche

        // Ajouter des tâches initiales
        val initialTasks = listOf(
            Task(1, "Faire les courses", "Acheter du lait, des œufs, du pain", "Aujourd'hui", false),
            Task(2, "Finir le projet Android", "Travailler sur l'application To-Do List", "Cette semaine", false),
            Task(3, "Appeler le docteur", "Prendre un rendez-vous pour le contrôle annuel", "Demain", false)
        )
        initialTasks.forEach { taskService.create(it) }

        // Récupérer et afficher toutes les tâches
        allTasks = taskService.getAll()
        taskAdapter = TaskAdapter(this, allTasks)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.adapter = taskAdapter

        // Mettre à jour le compteur de tâches
        updateTaskCount(allTasks.size)

        // Filtrer les tâches lorsque l'utilisateur tape dans le champ de recherche
        searchTasksEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterTasks(s.toString()) // Appeler la fonction de filtre
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Configuration du balayage pour supprimer/modifier des tâches
        val itemTouchHelperCallback = object : SwipeToDeleteCallback(taskAdapter, taskService) {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.tasks[position]

                if (direction == ItemTouchHelper.LEFT) {
                    // Afficher le dialogue d'édition
                    taskAdapter.showEditTaskDialog(task, position)
                    taskAdapter.notifyItemChanged(position) // Restaurer l'élément en cas d'annulation
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Afficher un message de confirmation avant de supprimer la tâche
                    AlertDialog.Builder(this@ListTaskActivity)
                        .setTitle("Supprimer la tâche")
                        .setMessage("Voulez-vous vraiment supprimer cette tâche ?")
                        .setPositiveButton("Oui") { _, _ ->
                            // Supprimer la tâche
                            taskService.delete(task.id)
                            taskAdapter.updateTasks(taskService.getAll()) // Mettre à jour l'adaptateur avec toutes les tâches
                            updateTaskCount(taskService.getAll().size) // Mettre à jour le compteur de tâches
                        }
                        .setNegativeButton("Non") { _, _ ->
                            // Restaurer la tâche en cas d'annulation
                            taskAdapter.notifyItemChanged(position)
                        }
                        .setCancelable(false)
                        .show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)

        // Ajouter un bouton pour ajouter une nouvelle tâche
        findViewById<View>(R.id.addTaskButton).setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK) // Démarrer AddTaskActivity avec un code de requête
        }

        // Ajouter la navigation vers les détails d'une tâche lorsqu'une tâche est cliquée
        taskAdapter.setOnItemClickListener { task ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("TASK_ID", task.id)
            startActivity(intent)
        }
    }

    // Récupérer le résultat de AddTaskActivity et mettre à jour la liste
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            val newTask = data?.getSerializableExtra("NEW_TASK") as? Task
            newTask?.let {
                // Ajouter la nouvelle tâche au service et à la liste
                taskService.create(it)
                taskAdapter.updateTasks(taskService.getAll()) // Mettre à jour l'adaptateur avec toutes les tâches
                updateTaskCount(taskService.getAll().size) // Mettre à jour le compteur de tâches
            }
        }
    }

    // Fonction pour filtrer les tâches
    private fun filterTasks(query: String) {
        val filteredTasks = allTasks.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }

        // Mettre à jour l'adaptateur avec les tâches filtrées
        taskAdapter.updateTasks(filteredTasks)

        // Mettre à jour le compteur de tâches
        updateTaskCount(filteredTasks.size)
    }

    // Fonction pour mettre à jour le compteur de tâches
    private fun updateTaskCount(count: Int) {
        taskCountTextView.text = "$count tâches"
    }
}
