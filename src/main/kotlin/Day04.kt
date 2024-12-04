import kotlin.io.path.Path
import kotlin.io.path.readLines

private val XMAS = "XMAS"
private val MAS = "MAS"
private val input = readInput()
private val sizeOfRows = input.first().size
private val sizeOfColumns = input.size

private val directionsFromRightToLeftAndFromBottomToUp = listOf(
    // right
    Pair(0, 1),
    // up
    Pair(1, 0),
    //diagonal
    Pair(1, 1),
    Pair(1, -1),
)

fun main() {
    println(partOne())
    println(partTwo())
}

private fun partOne(): Int {
    var result = 0
    for (y in input.indices) {
        for (x in input[y].indices) {
            for (direction in directionsFromRightToLeftAndFromBottomToUp) {
                if (checkForXMASInDirection(x, y, direction)) {
                    result++
                }
            }
        }
    }
    return result
}

private fun partTwo(): Int {
    var result = 0
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == 'A' && checkForMASInShapeOfX(x, y)) {
                result++
            }
        }
    }
    return result
}

private fun checkForXMASInDirection(startX: Int, startY: Int, direction: Pair<Int, Int>): Boolean {
    val charsVisited = buildString {
        for (step in 0 until 4) {
            val currentX = startX + direction.second * step
            val currentY = startY + direction.first * step
            if (isInsideOfArray(currentX, currentY)) {
                append(input[currentY][currentX])
            } else break
        }
    }
    return charsVisited in listOf(XMAS, XMAS.reversed())
}

private fun checkForMASInShapeOfX(startX: Int, startY: Int): Boolean {
    return listOf(
        collectDiagonalChars(startX, startY, 1, 1),
        collectDiagonalChars(startX, startY, -1, 1)
    ).all { it in listOf(MAS, MAS.reversed()) }
}

private fun collectDiagonalChars(x: Int, y: Int, directionX: Int, directionY: Int): String {
    return (-1..1).mapNotNull { step ->
        val currentX = x + directionX * step
        val currentY = y + directionY * step
        if (isInsideOfArray(currentX, currentY)) input[currentY][currentX] else null
    }.joinToString("")
}

private fun isInsideOfArray(x: Int, y: Int): Boolean = x in 0 until sizeOfRows && y in 0 until sizeOfColumns

private fun readInput() =
    Path("src/main/resources/Day04")
        .readLines()
        .map { line -> line.toCharArray().toTypedArray() }
        .toTypedArray()