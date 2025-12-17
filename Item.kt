package igra



import kotlin.random.Random

class Item(val type: ItemType, val name: String, val value: Int) {
    // Создает рандомный предмет
    companion object {
        fun createRandomItem(): Item {
            val randNum = Random.nextInt(3)  // от 0 до 2

            return when (randNum) {
                0 -> {
                    val damage = Random.nextInt(5, 16)  // урон от 5 до 15
                    Item(ItemType.WEAPON, "Крутой меч", damage)
                }
                1 -> {
                    val armor = Random.nextInt(5, 16)  // защита от 5 до 15
                    Item(ItemType.ARMOR, "Тяжелая броня", armor)
                }
                else -> {
                    val heal = Random.nextInt(20, 51)  // лечение от 20 до 50
                    Item(ItemType.POTION, "Бутылка зелья", heal)
                }
            }
        }
    }

    // Просто показывает что это за предмет
    fun showInfo(): String {
        return when (type) {
            ItemType.WEAPON -> "$name (+$value урона)"
            ItemType.ARMOR -> "$name (+$value защиты)"
            ItemType.POTION -> "$name (+$value здоровья)"
        }
    }
}