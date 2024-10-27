package ma.ensa.projet.beans

import java.io.Serializable

data class Task(
    var id: Int = 0,
    var title: String,
    var description: String,
    var dueDate: String,
    var isCompleted: Boolean = false
): Serializable