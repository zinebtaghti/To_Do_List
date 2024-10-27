package ma.ensa.projet.service

import ma.ensa.projet.beans.Task
import ma.ensa.projet.dao.IDao

class TaskService(private val taskDao: IDao<Task>) : IDao<Task> {

    override fun create(item: Task) {
        taskDao.create(item)
    }

    override fun getById(id: Int): Task? {
        return taskDao.getById(id)
    }

    override fun getAll(): List<Task> {
        return taskDao.getAll()
    }

    override fun update(item: Task) {
        taskDao.update(item)
    }

    override fun delete(id: Int) {
        taskDao.delete(id)
    }
}
