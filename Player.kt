package igra

import kotlin.random.Random

class Player {
    var x: Double = 0.0  // позиция игрока
    val speed: Double = 2.0  // скорость движения
    val maxHP: Int = 100  // максимальное здоровье
    var currentHP: Int = 100  // текущее здоровье
    val minDamage: Int = 10
    val maxDamage: Int = 40
    val backpack = AdvancedInventory()  // рюкзак игрока

    // Двигаемся вперед
    fun moveForward(): Double {
        x += speed
        return x
    }

    // Игрок атакует
    fun attack(): Int {
        val baseDamage = Random.nextInt(minDamage, maxDamage + 1)  // от 10 до 40
        val bonus = backpack.weaponBonus()  // бонус от оружия
        return baseDamage + bonus
    }

    // Игрок получает урон
    fun takeDamage(damage: Int) {
        val armor = backpack.armorBonus()  // защита от брони
        val realDamage = if (damage - armor > 0) damage - armor else 1

        currentHP -= realDamage
        if (currentHP < 0) currentHP = 0

        println("Получил $realDamage урона (броня заблокировала $armor)")
    }

    // Игрок лечится
    fun heal() {
        val healAmount = backpack.usePotion()
        if (healAmount != null) {
            currentHP += healAmount
            if (currentHP > maxHP) currentHP = maxHP  // чтоб не больше максимума
            println("Вылечился на $healAmount HP. Теперь HP: $currentHP/$maxHP")
        }
    }

    // Жив ли игрок
    fun isAlive(): Boolean = currentHP > 0

    // Показываем инфу об игроке
    fun showStats() {
        println("=== ИГРОК ===")
        println("Позиция: ${"%.1f".format(x)}")
        println("Здоровье: $currentHP/$maxHP")
        backpack.showInventory()
    }
}