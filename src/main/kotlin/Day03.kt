import kotlin.io.path.Path
import kotlin.io.path.readLines

private val MUL_REGEX = Regex("mul\\(\\d+,\\d+\\)")
private val DO = """do\(\)"""
private val DONT = """don't\(\)"""
private val DISABLES_REGEX = Regex("$DONT.*?$DO")

fun main() {
    val input = readInput()
    println(partOne(input))
    println(partTwo(input))
}

private fun partOne(input: String) = input.findExpression().sumOf { it.calculateExpression() }

private fun partTwo(input: String) = partOne(input.cutDisablePart())

private fun String.cutDisablePart() = this.replace(DISABLES_REGEX, "")

private fun String.findExpression() = MUL_REGEX.findAll(this).map { it.value }.toList()

private fun String.calculateExpression() =
    this.removePrefix("mul(").removeSuffix(")").split(",")
        .let { it[0].toLong() * it[1].toLong() }

private fun readInput() =
    Path("src/main/resources/Day03").readLines().joinToString()