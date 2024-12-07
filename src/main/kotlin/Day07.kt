import kotlin.io.path.Path
import kotlin.io.path.readLines

private val ADD = "ADD"
private val MULTIPLAY = "MULTIPLAY"
private val MERGE = "MERGE"

fun main() {
    val input = readInput()
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: List<Pair<Long, List<Long>>>): Long {
    return input.mapNotNull { row ->
        row.takeIf {
            it.isOperationsPossibleForOperations(listOf(ADD, MULTIPLAY))
        }
    }.sumOf { it.first }
}

private fun partTwo(input: List<Pair<Long, List<Long>>>): Long {
    return input.mapNotNull { operation ->
        operation.takeIf {
            it.isOperationsPossibleForOperations(listOf(ADD, MULTIPLAY, MERGE))
        }
    }.sumOf { it.first }
}

private fun calculatePossibleOperations(
    expected: Long,
    numbersToCalculate: List<Long>,
    operation: String,
    allowOperations: List<String>
): Boolean {
    if (numbersToCalculate.size == 1) return numbersToCalculate.first() == expected

    val newNumbersToCalculate: List<Long> = when (operation) {
        ADD -> numbersToCalculate.reduceFirstTwo { a, b -> a + b }
        MULTIPLAY -> numbersToCalculate.reduceFirstTwo { a, b -> a * b }
        MERGE -> numbersToCalculate.reduceFirstTwo { a, b -> (a.toString() + b.toString()).toLong() }
        else -> throw IllegalArgumentException("Unknown operation: $operation")
    }

    return newNumbersToCalculate.first() <= expected && allowOperations.any {
        calculatePossibleOperations(expected, newNumbersToCalculate, it, allowOperations)
    }
}
private fun List<Long>.reduceFirstTwo(operation: (Long, Long) -> Long): List<Long> =
    listOf(operation(this[0], this[1])) + this.drop(2)

private fun Pair<Long, List<Long>>.isOperationsPossibleForOperations(operations: List<String>): Boolean {
    return operations.any {
        calculatePossibleOperations(
            this.first,
            this.second,
            it,
            operations
        )
    }
}

private fun readInput() = Path("src/main/resources/Day07").readLines().map { line ->
    val (key, values) = line.split(": ").let { it[0].toLong() to it[1] }
    key to values.split(" ").map(String::toLong)
}