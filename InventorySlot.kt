package igra



class InventorySlot<T : Item>(val maxSize: Int = 10) {
    private val items = mutableListOf<T>()  // список предметов

    // Добавляем предмет
    fun add(item: T): Boolean {
        if (items.size < maxSize) {
            items.add(item)
            return true
        }
        return false  // если нет места
    }

    // Берем предмет
    fun get(index: Int): T? {
        if (index in 0 until items.size) {
            return items[index]
        }
        return null
    }

    // Удаляем предмет
    fun remove(index: Int): T? {
        if (index in 0 until items.size) {
            return items.removeAt(index)
        }
        return null
    }

    // Сколько предметов
    fun count(): Int = items.size

    // Есть ли предметы
    fun isEmpty(): Boolean = items.isEmpty()

    // Все предметы
    fun getAll(): List<T> = items.toList()
}



