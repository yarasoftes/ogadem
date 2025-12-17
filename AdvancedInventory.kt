package igra

class AdvancedInventory {
    // Создаем слоты для разных предметов
    private val weapons = InventorySlot<Item>(2)    // максимум 2 оружия
    private val armors = InventorySlot<Item>(2)     // максимум 2 брони
    private val potions = InventorySlot<Item>(10)   // максимум 10 зелий

    var currentWeapon: Item? = null  // оружие которое сейчас надето
    var currentArmor: Item? = null   // броня которая сейчас надета

    // Добавляем предмет в рюкзак
    fun pickUpItem(item: Item) {
        when (item.type) {
            ItemType.WEAPON -> {
                if (weapons.add(item)) {
                    println("Нашли оружие: ${item.name}")
                    wearBestWeapon()  // одеваем лучшее оружие
                } else {
                    println("Нельзя взять оружие, места нет!")
                }
            }
            ItemType.ARMOR -> {
                if (armors.add(item)) {
                    println("Нашли броню: ${item.name}")
                    wearBestArmor()  // одеваем лучшую броню
                } else {
                    println("Нельзя взять броню, места нет!")
                }
            }
            ItemType.POTION -> {
                if (potions.add(item)) {
                    println("Нашли зелье: ${item.name}")
                } else {
                    println("Нельзя взять зелье, места нет!")
                }
            }
        }
    }

    // Одеваем лучшее оружие
    private fun wearBestWeapon() {
        var bestWeapon: Item? = null
        var maxDamage = 0

        // Ищем самое сильное оружие
        for (i in 0 until weapons.count()) {
            val weapon = weapons.get(i)
            if (weapon != null && weapon.value > maxDamage) {
                maxDamage = weapon.value
                bestWeapon = weapon
            }
        }

        currentWeapon = bestWeapon
        if (bestWeapon != null) {
            println("Одели оружие: ${bestWeapon.name}")
        }
    }

    // Одеваем лучшую броню
    private fun wearBestArmor() {
        var bestArmor: Item? = null
        var maxProtection = 0

        // Ищем самую крепкую броню
        for (i in 0 until armors.count()) {
            val armor = armors.get(i)
            if (armor != null && armor.value > maxProtection) {
                maxProtection = armor.value
                bestArmor = armor
            }
        }

        currentArmor = bestArmor
        if (bestArmor != null) {
            println("Одели броню: ${bestArmor.name}")
        }
    }

    // Используем зелье
    fun usePotion(): Int? {
        if (!potions.isEmpty()) {
            val potion = potions.remove(0)  // берем первое зелье
            if (potion != null) {
                println("Выпили зелье: ${potion.name}")
                return potion.value  // сколько здоровья восстанавливает
            }
        }
        println("Зелья кончились!")
        return null
    }

    // Сколько дает оружие урона
    fun weaponBonus(): Int {
        return currentWeapon?.value ?: 0  // если оружия нет, то 0
    }

    // Сколько дает броня защиты
    fun armorBonus(): Int {
        return currentArmor?.value ?: 0  // если брони нет, то 0
    }

    // Есть ли зелья
    fun hasPotions(): Boolean = !potions.isEmpty()

    // Сколько зелий
    fun potionCount(): Int = potions.count()

    // Показываем что в рюкзаке
    fun showInventory() {
        println("=== РЮКЗАК ===")
        println("Оружие (${weapons.count()}/2):")
        for (i in 0 until weapons.count()) {
            val weapon = weapons.get(i)
            if (weapon != null) {
                val mark = if (weapon == currentWeapon) "[НАДЕТО]" else ""
                println("  - ${weapon.name} $mark")
            }
        }

        println("Броня (${armors.count()}/2):")
        for (i in 0 until armors.count()) {
            val armor = armors.get(i)
            if (armor != null) {
                val mark = if (armor == currentArmor) "[НАДЕТО]" else ""
                println("  - ${armor.name} $mark")
            }
        }

        println("Зелья (${potions.count()}/10):")
        for (i in 0 until potions.count()) {
            val potion = potions.get(i)
            if (potion != null) {
                println("  - ${potion.name}")
            }
        }
    }
}