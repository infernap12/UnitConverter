package converter

fun main() {
    print("Enter a number and a measure: ")
    val userInput = readln()
    println()
    val parsedInput: Pair<Double, Units>? = parseInput(userInput)
    if (parsedInput == null) {
        println("Wrong input")
        return
    }
    val amount = parsedInput.first
    val fromUnitName = parsedInput.second.name.lowercase().pluralise(amount)
    val result: Double = convert(parsedInput)
    val toUnitName = "meter".pluralise(result)
    println("${amount.dropTrail()} $fromUnitName is ${result.dropTrail()} $toUnitName")
}

private fun Double.dropTrail(): String {
    return (if (this % 1 == 0.0) this.toInt() else this).toString()
}

private fun String.pluralise(d: Double): String {
    return if (d == 1.0) {
        this
    } else this + "s"
}

fun convert(pair: Pair<Double, Units>): Double {
    return pair.first * pair.second.SIFactor
}

fun parseInput(userInput: String): Pair<Double, Units>? {
    val (i, unit) = userInput.uppercase().split(" ")
    try {
        i.toInt()
    } catch (_: NumberFormatException) {
        return null
    }
    return when {
        unit == "KM" -> Pair(i.toDouble(), Units.KILOMETER)
        i.toInt() > 1 && unit == "KILOMETERS" -> Pair(i.toDouble(), Units.KILOMETER)
        i.toInt() == 1 && unit == "KILOMETER" -> Pair(i.toDouble(), Units.KILOMETER)
        else -> null
    }

}




enum class Units(val SIFactor: Double) {
    KILOMETER(1000.0),
    M(1.0),
    CM(0.01);
}

