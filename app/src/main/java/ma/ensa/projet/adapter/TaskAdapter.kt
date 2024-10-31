package ma.ensa.projet.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ma.ensa.projet.TaskDetailActivity
import ma.ensa.projet.beans.Task
import ma.ensa.projet.service.TaskDao
import ma.ensa.projet.service.TaskService
import ma.ensa.projetws.to_do_list.R

class TaskAdapter(
    val context: Context,
    var tasks: List<Task>,
    private val rootView: View // Passer la vue racine pour la Snackbar
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var onItemClickListener: ((Task) -> Unit)? = null
    private val taskService = TaskService(TaskDao()) // Initialisation directe

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.titleText.text = task.title
        holder.dateText.text = task.dueDate
        holder.completedCheckbox.isChecked = task.isCompleted

        holder.completedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            taskService.update(task)

            val message = if (isChecked) "Tâche marquée comme complétée" else "Tâche marquée comme non complétée"
            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TaskDetailActivity::class.java).apply {
                putExtra("TASK_TITLE", task.title)
                putExtra("TASK_DESCRIPTION", task.description)
                putExtra("TASK_DUE_DATE", task.dueDate)
                putExtra("TASK_IS_COMPLETED", task.isCompleted)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tasks.size

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.taskTitle)
        val dateText: TextView = itemView.findViewById(R.id.taskDate)
        val completedCheckbox: CheckBox = itemView.findViewById(R.id.taskCompleted)
    }

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    fun showEditTaskDialog(task: Task, position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.task_edit_dialog, null)
        val titleInput = dialogView.findViewById<TextView>(R.id.editTaskTitle)
        val descriptionInput = dialogView.findViewById<TextView>(R.id.editTaskDescription)
        val dueDateInput = dialogView.findViewById<TextView>(R.id.editTaskDueDate)

        titleInput.text = task.title
        descriptionInput.text = task.description
        dueDateInput.text = task.dueDate

        val dialog = AlertDialog.Builder(context)
            .setTitle("Modifier la tâche")
            .setView(dialogView)
            .setPositiveButton("Enregistrer") { _, _ ->
                task.title = titleInput.text.toString()
                task.description = descriptionInput.text.toString()
                task.dueDate = dueDateInput.text.toString()
                taskService.update(task)
                notifyItemChanged(position)
            }
            .setNegativeButton("Annuler", null)
            .create()

        dialog.show()
    }

    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }
}
