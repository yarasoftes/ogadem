package igra

fun main() {
    println("ПРИВЕТ! ЭТО МОЯ ИГРА")
    println("=".repeat(40))
    println("Твоя задача - дойти до 110 позиции")
    println("По пути будут враги и предметы")
    println()
    println("Управление:")
    println("- Просто нажимай Enter чтобы идти")
    println("- В бою выбирай цифры 1 или 2")
    println("=".repeat(40))
    println()

    // Тут я сначала хотел сделать меню выбора, но времени нет
    // поэтому сразу начинаем игру

    val game = AdvancedGame()
    game.startGame()
}