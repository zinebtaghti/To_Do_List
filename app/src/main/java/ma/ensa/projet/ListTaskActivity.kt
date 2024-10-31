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
    private lateinit var searchTasksEditText: EditText
    private var allTasks: List<Task> = listOf()
    private val REQUEST_CODE_ADD_TASK = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_task)

        taskService = TaskService(TaskDao())
        val taskRecyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        taskCountTextView = findViewById(R.id.taskCount)
        searchTasksEditText = findViewById(R.id.searchTasks)
        val rootView = findViewById<View>(android.R.id.content)

        // Initialiser TaskAdapter avec la vue racine pour afficher Snackbar
        taskAdapter = TaskAdapter(this, allTasks, rootView)

        // Ajouter des tâches initiales
        val initialTasks = listOf(
            Task(1, "Faire les courses", "Acheter du lait, des œufs, du pain", "Aujourd'hui", false),
            Task(2, "Finir le projet Android", "Travailler sur l'application To-Do List", "Cette semaine", false),
            Task(3, "Appeler le docteur", "Prendre un rendez-vous pour le contrôle annuel", "Demain", false),
            Task(4, "Réviser pour l'examen de maths", "Étudier les chapitres 3 à 5 et faire les exercices", "Ce week-end", false),
            Task(5, "Acheter un cadeau d'anniversaire", "Chercher un cadeau pour l'anniversaire de Julie", "La semaine prochaine", false),
            Task(6, "Nettoyer la voiture", "Passer l'aspirateur et nettoyer les vitres", "Aujourd'hui", false)
        )
        initialTasks.forEach { taskService.create(it) }

        allTasks = taskService.getAll()
        taskAdapter.updateTasks(allTasks)

        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.adapter = taskAdapter

        updateTaskCount(allTasks.size)

        // Ajouter un TextWatcher pour filtrer les tâches en fonction de la saisie
        searchTasksEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterTasks(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Configurer le balayage pour supprimer ou modifier des tâches
        val itemTouchHelperCallback = object : SwipeToDeleteCallback(taskAdapter, taskService) {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.tasks[position]

                if (direction == ItemTouchHelper.LEFT) {
                    taskAdapter.showEditTaskDialog(task, position)
                    taskAdapter.notifyItemChanged(position)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    AlertDialog.Builder(this@ListTaskActivity)
                        .setTitle("Supprimer la tâche")
                        .setMessage("Voulez-vous vraiment supprimer cette tâche ?")
                        .setPositiveButton("Oui") { _, _ ->
                            taskService.delete(task.id)
                            updateTasksAndCount()
                        }
                        .setNegativeButton("Non") { _, _ ->
                            taskAdapter.notifyItemChanged(position)
                        }
                        .setCancelable(false)
                        .show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(taskRecyclerView)

        findViewById<View>(R.id.addTaskButton).setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_TASK)
        }

        taskAdapter.setOnItemClickListener { task ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra("TASK_ID", task.id)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            val newTask = data?.getSerializableExtra("NEW_TASK") as? Task
            newTask?.let {
                taskService.create(it)
                updateTasksAndCount()
            }
        }
    }

    // Fonction pour filtrer les tâches
    private fun filterTasks(query: String) {
        val filteredTasks = allTasks.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }
        taskAdapter.updateTasks(filteredTasks)
        updateTaskCount(filteredTasks.size)
    }

    // Mettre à jour la liste et le compteur de tâches
    private fun updateTasksAndCount() {
        val tasks = taskService.getAll()
        taskAdapter.updateTasks(tasks)
        updateTaskCount(tasks.size)
    }

    // Fonction pour mettre à jour le compteur de tâches
    private fun updateTaskCount(count: Int) {
        taskCountTextView.text = "$count tâches"
    }
}
