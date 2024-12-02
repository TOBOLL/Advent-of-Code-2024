import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main() {
    val input = readInput()
    println(partOne(input))
    println(partTwo(input))
}

enum class TENDENCY { INC, DEC }

private fun partOne(input: List<List<Int>>): Int {
    return input.count { it.isSafe() }
}

private fun partTwo(input: List<List<Int>>): Int {
    return input.count { row ->
        row.isSafe() || row.isAnySafeAfterRemovingOneNumber()
    }
}

private fun List<Int>.isAnySafeAfterRemovingOneNumber() = this.indices.any { index ->
    this.toMutableList().also { it.removeAt(index) }.isSafe()
}

private fun List<Int>.isSafe() =
    defineTendency(this) != null && this.zipWithNext { current, next -> abs(current - next) }.all { it in 1..3 }

private fun defineTendency(numbers: List<Int>): TENDENCY? = when {
    numbers.zipWithNext().all { (a, b) -> a < b } -> TENDENCY.INC
    numbers.zipWithNext().all { (a, b) -> a > b } -> TENDENCY.DEC
    else -> null
}

private fun readInput() =
    Path("src/main/resources/Day02").readLines()
        .map {
            it.split(" ").map { string -> string.toInt() }
        }