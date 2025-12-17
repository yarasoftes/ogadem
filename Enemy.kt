package igra

import kotlin.random.Random

class Enemy(val position: Double) {
    val maxHP: Int
    var currentHP: Int
    val minDamage: Int = 5
    val maxDamage: Int = 20

    init {
        maxHP = Random.nextInt(40, 91)  // от 40 до 90 хп
        currentHP = maxHP
    }

    // Враг атакует
    fun attack(): Int {
        return Random.nextInt(minDamage, maxDamage + 1)  // урон от 5 до 20
    }

    // Враг получает урон
    fun takeDamage(damage: Int) {
        currentHP -= damage
        if (currentHP < 0) currentHP = 0
    }

    // Жив ли враг
    fun isAlive(): Boolean = currentHP > 0

    // Инфа о враге
    fun info(): String {
        return "Враг на ${position.toInt()} позиции (HP: $currentHP/$maxHP)"
    }
}

// =============== ФАЙЛ: Player.kt ===============
// Класс для игрока

