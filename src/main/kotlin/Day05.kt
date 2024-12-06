import kotlin.io.path.Path
import kotlin.io.path.readLines

private val pageOrderingRules = readPageOrderingRules()
private val listOfPages = readPages()
private val SMALLER = -1
private val GREATER = 1
private val EQUAL = 0

fun main() {
    println(partOne())
    println(partTwo())
}

private fun partOne(): Int {
    return filterPagesNotInOrderingRule().sumOf { it[it.size / 2] }
}

private fun partTwo(): Int {
    return filterPagesInOrderingRule().map {
        it.sortedWith { page, nextPage ->
            when {
                pageOrderingRules[page]?.contains(nextPage) == true -> SMALLER
                pageOrderingRules[page]?.contains(nextPage) == false -> GREATER
                else -> EQUAL
            }
        }
    }.sumOf { it[it.size / 2] }
}

private fun filterPagesNotInOrderingRule() = listOfPages.mapNotNull {
    it.takeIf {
        it.mapIndexed { index, value ->
            val otherPages = it.slice(0 until index)
            pageOrderingRules[value]?.any { incorrectPage -> otherPages.contains(incorrectPage) } ?: false
        }.all { incorrectOrder ->  !incorrectOrder }
    }
}

private fun filterPagesInOrderingRule() = listOfPages.mapNotNull {
    it.takeIf {
        it.mapIndexed { index, value ->
            val otherPages = it.slice(0 until index)
            pageOrderingRules[value]?.any { incorrectPage -> otherPages.contains(incorrectPage) } ?: false
        }.any { incorrectOrder ->  incorrectOrder }
    }
}

private fun readPageOrderingRules(): Map<Int, List<Int>> {
    return Path("src/main/resources/Day05")
        .readLines()
        .takeWhile { it.isNotBlank() }
        .map { line ->
            val (key, value) = line.split("|").map { it.toInt() }
            key to value
        }
        .groupBy({ it.first }, { it.second })
}

private fun readPages() = Path("src/main/resources/Day05")
    .readLines()
    .dropWhile { it.isNotBlank() }
    .drop(1)
    .map { string ->
        string.split(",")
            .map { it.toInt() }
    }