package ma.ensa.projet.dao

interface IDao<T> {
    fun create(item: T)
    fun getById(id: Int): T?
    fun getAll(): List<T>
    fun update(item: T)
    fun delete(id: Int)
}