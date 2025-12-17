package igra

import kotlin.random.Random

class AdvancedGame {
    private val gamer = Player()  // создаем игрока
    private val badGuys = mutableListOf<Enemy>()  // список врагов
    private val itemsOnMap = mutableListOf<Pair<Double, Item>>()  // предметы на карте
    private val finish = 110.0  // финиш игры
    private var gameEnded = false  // игра окончена?
    private val maxItemsOnMap = 4  // макс предметов на карте

    // Начинаем игру
    fun startGame() {
        println("====== НАЧАЛО ИГРЫ ======")

        // Создаем врагов
        val enemyCount = Random.nextInt(2, 5)  // от 2 до 4 врагов
        for (i in 1..enemyCount) {
            val pos = Random.nextDouble(25.0, 100.0)
            badGuys.add(Enemy(pos))
        }

        // Сортируем врагов по позиции (чтоб по порядку встречались)
        badGuys.sortBy { it.position }

        println("В игре $enemyCount врагов")
        println("Нужно дойти до $finish")
        println()

        // Главный цикл игры
        gameLoop()
    }

    // Главный цикл
    private fun gameLoop() {
        while (!gameEnded && badGuys.isNotEmpty() && gamer.x < finish) {
            println("\n" + "~".repeat(50))

            // Двигаемся
            gamer.moveForward()
            println("Текущая позиция: ${"%.1f".format(gamer.x)}")

            // Проверяем предметы
            checkItems()

            // Может появится предмет
            maybeSpawnItem()

            // Проверяем врагов
            val enemy = findEnemy()
            if (enemy != null) {
                // Начался бой!
                val win = battle(enemy)
                if (!win) {
                    println("\nТЫ ПРОИГРАЛ!!!")
                    gameEnded = true
                    break
                }
            }

            // Показываем статистику
            println("\n--- СТАТУС ---")
            println("До финиша: ${"%.1f".format(finish - gamer.x)}")
            println("Врагов осталось: ${badGuys.size}")
            println("Предметов на карте: ${itemsOnMap.size}/$maxItemsOnMap")

            // Пауза перед следующим ходом
            if (!gameEnded && gamer.x < finish) {
                println("\nНажми Enter чтобы идти дальше...")
                try {
                    readLine()
                } catch (e: Exception) {
                    // ну если что-то не так, просто продолжаем
                }
            }
        }

        // Завершаем игру
        endGame()
    }

    // Проверяем предметы на пути
    private fun checkItems() {
        val toRemove = mutableListOf<Pair<Double, Item>>()

        for ((itemPos, item) in itemsOnMap) {
            // Если мы на позиции предмета
            if (gamer.x >= itemPos && gamer.x < itemPos + gamer.speed) {
                gamer.backpack.pickUpItem(item)
                toRemove.add(Pair(itemPos, item))
            }
        }

        // Удаляем подобранные предметы
        for (item in toRemove) {
            itemsOnMap.remove(item)
        }
    }

    // Случайно появляется предмет
    private fun maybeSpawnItem() {
        if (itemsOnMap.size < maxItemsOnMap) {
            val chance = Random.nextDouble()  // от 0.0 до 1.0
            if (chance < 0.3) {  // 30% шанс
                val newItem = Item.createRandomItem()
                itemsOnMap.add(Pair(gamer.x, newItem))
                println("Появился предмет: ${newItem.name}")
            }
        }
    }

    // Ищем врага на пути
    private fun findEnemy(): Enemy? {
        for (enemy in badGuys) {
            if (gamer.x >= enemy.position && gamer.x < enemy.position + gamer.speed) {
                return enemy
            }
        }
        return null
    }

    // Бой с врагом
    private fun battle(enemy: Enemy): Boolean {
        println("\n" + "=".repeat(40))
        println("ВСТРЕТИЛСЯ ВРАГ!")
        println(enemy.info())
        gamer.showStats()

        var round = 1

        while (gamer.isAlive() && enemy.isAlive()) {
            println("\n--- Раунд $round ---")

            // Игрок атакует
            println("Твоя атака:")
            val myDamage = gamer.attack()
            println("Ты нанес $myDamage урона")
            enemy.takeDamage(myDamage)
            println("У врага осталось ${enemy.currentHP} HP")

            // Проверяем убит ли враг
            if (!enemy.isAlive()) {
                println("\nВРАГ УБИТ!")
                badGuys.remove(enemy)
                return true
            }

            // Враг атакует
            println("\nАтака врага:")
            val enemyDamage = enemy.attack()
            println("Враг нанес $enemyDamage урона")
            gamer.takeDamage(enemyDamage)
            println("У тебя осталось ${gamer.currentHP} HP")

            // Предлагаем выпить зелье
            if (gamer.currentHP < 50 && gamer.backpack.hasPotions() && gamer.isAlive()) {
                println("\nУ тебя мало здоровья! Выпить зелье?")
                println("1. Да, выпить")
                println("2. Нет, продолжить")
                print("Твой выбор (1 или 2): ")

                val choice = readLine()
                if (choice == "1") {
                    gamer.heal()
                }
            }

            round++
        }

        return gamer.isAlive()
    }

    // Завершение игры
    private fun endGame() {
        println("\n" + "=".repeat(50))

        if (!gamer.isAlive()) {
            println("ИГРА ОКОНЧЕНА")
            println("Ты умер на позиции ${"%.1f".format(gamer.x)}")
            println("Врагов осталось: ${badGuys.size}")
        } else if (gamer.x >= finish) {
            println("ПОБЕДА!!!")
            println("Ты дошел до финиша!")
            println("Врагов побеждено: ${badGuys.size}")
            println("\nТвои результаты:")
            gamer.showStats()
        } else {
            println("Игра прервана")
        }

        println("\nСпасибо за игру!")
    }
}