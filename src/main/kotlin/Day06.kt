import java.lang.RuntimeException
import kotlin.io.path.Path
import kotlin.io.path.readLines

private var grid = readInput()
private val OBSTRUCTION = '#'
private val VISITED = 'X'
private val GUARD = '^'
private val sizeOfRows = grid.first().size
private val sizeOfColumns = grid.size
private val startingPoint = findStartPoint()

fun main() {
    println(partOne())
    println(partTwo())
}

private fun partOne(): Int {
    var direction = Pair(-1, 0)
    var currentLocation = findStartPoint()
    while (true) {
        val nextStep = currentLocation + direction
        grid[currentLocation.first][currentLocation.second] = VISITED

        if (!isInsideOfArray(nextStep)) break

        when (grid[nextStep.first][nextStep.second]) {
            OBSTRUCTION -> direction = direction.turnGuardToRight()
            else -> {
                currentLocation = nextStep
            }
        }
    }
    return grid.flatten().count { it == VISITED }
}

private fun partTwo(): Int {
    var result = 0

    for (y in 0 until sizeOfColumns) {
        for (x in 0 until sizeOfRows) {

            val temGrid = grid.map { it.copyOf() }.toTypedArray()
            val mapOfVisitedObstructionWithDirection = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>>()
            if (grid[y][x] != VISITED || Pair(y, x) == startingPoint) continue

            temGrid[y][x] = OBSTRUCTION
            var direction = Pair(-1, 0)
            var currentLocation = startingPoint

            while (true) {
                val nextStep = currentLocation + direction
                temGrid[currentLocation.first][currentLocation.second] = VISITED

                if (!isInsideOfArray(nextStep)) break

                when (temGrid[nextStep.first][nextStep.second]) {
                    OBSTRUCTION -> {
                        if (mapOfVisitedObstructionWithDirection[nextStep] == direction) {
                            result++
                            break
                        }
                        mapOfVisitedObstructionWithDirection[nextStep] = direction
                        direction = direction.turnGuardToRight()
                    }

                    else -> {
                        currentLocation = nextStep
                    }
                }
            }
        }
    }
    return result
}

fun findStartPoint(): Pair<Int, Int> =
    grid.flatMapIndexed { i, row -> row.mapIndexed { j, value -> Triple(i, j, value) } }
        .firstOrNull { it.third == GUARD }
        ?.let { Pair(it.first, it.second) }
        ?: throw RuntimeException("Not found guard")

private fun Pair<Int, Int>.turnGuardToRight() =
    when (this) {
        Pair(-1, 0) -> Pair(0, 1)
        Pair(0, 1) -> Pair(1, 0)
        Pair(1, 0) -> Pair(0, -1)
        Pair(0, -1) -> Pair(-1, 0)
        else -> throw RuntimeException("Where are you going guard? Hmm")
    }

private fun isInsideOfArray(step: Pair<Int, Int>): Boolean =
    step.second in 0 until sizeOfRows && step.first in 0 until sizeOfColumns

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
    Pair(this.first + other.first, this.second + other.second)

private fun readInput() =
    Path("src/main/resources/Day06")
        .readLines()
        .map { line -> line.toCharArray().toTypedArray() }
        .toTypedArray()