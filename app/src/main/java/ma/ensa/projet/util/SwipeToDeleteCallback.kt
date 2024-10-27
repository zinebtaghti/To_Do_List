package ma.ensa.projet.util

import android.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ma.ensa.projet.adapter.TaskAdapter
import ma.ensa.projet.service.TaskService

open class SwipeToDeleteCallback(private val adapter: TaskAdapter, private val taskService: TaskService) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val task = adapter.tasks[position]

        if (direction == ItemTouchHelper.LEFT) {
            // Afficher le dialogue d'édition
            adapter.showEditTaskDialog(task, position)
            // Restaurer l'élément en cas d'annulation
            adapter.notifyItemChanged(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            // Confirmer la suppression de la tâche
            val dialog = AlertDialog.Builder(adapter.context)
                .setTitle("Supprimer la tâche")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette tâche ?")
                .setPositiveButton("Oui") { _, _ ->
                    taskService.delete(task.id)
                    adapter.notifyItemRemoved(position)
                }
                .setNegativeButton("Non") { _, _ ->
                    // Restaurer l'élément s'il clique sur "Non"
                    adapter.notifyItemChanged(position)
                }
                .create()

            dialog.show()
        }
    }
}
