package ma.ensa.projet.service

import android.os.Build
import androidx.annotation.RequiresApi
import ma.ensa.projet.beans.Task
import ma.ensa.projet.dao.IDao

class TaskDao : IDao<Task> {
    private val tasks = mutableListOf<Task>()
    private var currentId = 1

    override fun create(item: Task) {
        item.id = currentId++
        tasks.add(item)
    }

    override fun getById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    override fun getAll(): List<Task> {
        return tasks
    }

    override fun update(item: Task) {
        val index = tasks.indexOfFirst { it.id == item.id }
        if (index != -1) {
            tasks[index] = item
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun delete(id: Int) {
        tasks.removeIf { it.id == id }
    }
}
