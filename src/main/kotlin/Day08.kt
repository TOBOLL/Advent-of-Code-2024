import kotlin.io.path.Path
import kotlin.io.path.readLines

private var grid = readInput()
private val sizeOfRows = grid.first().size
private val sizeOfColumns = grid.size

fun main() {
    val input = readInput()
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: Array<Array<Char>>): Int {
    val allAntennasPosition = findAllAntennas(input)
    val antiNodesPosition: MutableSet<Pair<Int, Int>> = mutableSetOf()

    allAntennasPosition.forEach { antennas ->
        antennas.value.forEach { currentPosition ->
            antennas.value.forEach { targetPosition ->
                if (targetPosition != currentPosition) {
                    val vectorY = targetPosition.first - currentPosition.first
                    val vectorX = targetPosition.second - currentPosition.second
                    if (isInsideOfArray(
                            targetPosition.first + vectorY,
                            targetPosition.second + vectorX
                        )
                    ) {
                        antiNodesPosition.add(Pair(targetPosition.first + vectorY, targetPosition.second + vectorX))
                    }
                }
            }
        }
    }
    return antiNodesPosition.size
}

private fun partTwo(input: Array<Array<Char>>): Int {
    val allAntennasPosition = findAllAntennas(input)
    val antiNodesPosition: MutableSet<Pair<Int, Int>> = mutableSetOf()

    allAntennasPosition.forEach { antennas ->
        antennas.value.forEach { currentPosition ->
            antennas.value.forEach { targetPosition ->
                if (targetPosition != currentPosition) {
                    var tempPosition = currentPosition
                    val vectorY = targetPosition.first - currentPosition.first
                    val vectorX = targetPosition.second - currentPosition.second
                    while (isInsideOfArray(tempPosition.first + vectorY, tempPosition.second + vectorX)) {
                        antiNodesPosition.add(Pair(tempPosition.first + vectorY, tempPosition.second + vectorX))
                        tempPosition = Pair(tempPosition.first + vectorY, tempPosition.second + vectorX)
                    }
                }
            }
        }
    }
    return antiNodesPosition.size
}

private fun findAllAntennas(input: Array<Array<Char>>): Map<Char, List<Pair<Int, Int>>> {
    return input.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, char -> char to Pair(rowIndex, colIndex) }.filter { it.first != '.' }
    }.groupBy({ it.first }, { it.second })
}

private fun isInsideOfArray(y: Int, x: Int): Boolean =
    x in 0 until sizeOfRows && y in 0 until sizeOfColumns

private fun readInput() =
    Path("src/main/resources/Day08")
        .readLines()
        .map { line -> line.toCharArray().toTypedArray() }
        .toTypedArray()