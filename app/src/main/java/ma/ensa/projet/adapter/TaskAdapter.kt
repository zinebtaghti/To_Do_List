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

class TaskAdapter(val context: Context, var tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var onItemClickListener: ((Task) -> Unit)? = null
    // Passer une instance de TaskDao à TaskService
    var taskService = TaskService(TaskDao())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Utilisation de LayoutInflater pour gonfler le layout XML de chaque élément
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Assigner les données de la tâche
        holder.titleText?.text = task.title
        holder.dateText?.text = task.dueDate
        holder.completedCheckbox?.isChecked = task.isCompleted

        // Écouteur pour mettre à jour l'état de la tâche
        holder.completedCheckbox?.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            taskService.update(task) // Mettre à jour la tâche dans le service

            // Afficher une notification Snackbar
            val message = if (isChecked) {
                "Tâche marquée comme complétée"
            } else {
                "Tâche marquée comme non complétée"
            }
            Snackbar.make(holder.itemView, message, Snackbar.LENGTH_LONG).show()
        }

        // Définir un écouteur de clic sur chaque élément de la liste
        holder.itemView.setOnClickListener {
            val intent = Intent(context, TaskDetailActivity::class.java)
            intent.putExtra("TASK_TITLE", task.title)
            intent.putExtra("TASK_DESCRIPTION", task.description)
            intent.putExtra("TASK_DUE_DATE", task.dueDate)
            intent.putExtra("TASK_IS_COMPLETED", task.isCompleted)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = tasks.size

    // ViewHolder qui contient les références aux vues d'un élément de la liste
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView? = itemView.findViewById(R.id.taskTitle)
        val dateText: TextView? = itemView.findViewById(R.id.taskDate) // Référence au TextView pour la date
        val completedCheckbox: CheckBox? = itemView.findViewById(R.id.taskCompleted)
    }

    // Méthode pour mettre à jour les tâches dans l'adaptateur
    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    // Fonction pour afficher un dialogue de modification de tâche
    fun showEditTaskDialog(task: Task, position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.task_edit_dialog, null)
        val titleInput = dialogView.findViewById<TextView>(R.id.editTaskTitle)
        val descriptionInput = dialogView.findViewById<TextView>(R.id.editTaskDescription)
        val dueDateInput = dialogView.findViewById<TextView>(R.id.editTaskDueDate)

        // Initialiser les champs avec les données actuelles
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

    // Méthode pour définir un écouteur de clic pour les éléments de la liste
    fun setOnItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }
}
