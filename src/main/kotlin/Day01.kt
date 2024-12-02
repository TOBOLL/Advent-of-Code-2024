import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main() {
    val input = readInput()
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: Pair<List<Int>, List<Int>>) =
    input.first.sorted().zip(input.second.sorted()).sumOf { (elementFormFirst, elementFromSecond) ->
        abs(elementFormFirst - elementFromSecond)
    }

private fun partTwo(input: Pair<List<Int>, List<Int>>) =
    input.first.sumOf { elementFormFirst -> elementFormFirst * input.second.count { elementFormFirst == it } }


private fun readInput() =
    Path("src/main/resources/Day01").readLines()
        .map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()