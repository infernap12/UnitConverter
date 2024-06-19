package converter

import kotlin.math.sign

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val userInput = readln()
        if (userInput == "exit") {
            return
        }
        println()
        val conversion: Conversion
        try {
            conversion = parseInput(userInput)
        } catch (e: Exception) {
            println(e.message)
            continue
        }
        println(conversion.getFormattedString())
        println()
    }

}


fun parseInput(userInput: String): Conversion {
    val cleanInput = cleanInput(userInput)
    val (iString, fromString, toString) = cleanInput
    val i: Double
    try {
        i = iString.toDouble()
    } catch (_: NumberFormatException) {
        throw NumberFormatException("Parse error")
    }
    val (fromPrefix, fromUnit) = parsePrefix(fromString) // can be null
    val (toPrefix, toUnit) = parsePrefix(toString)

    val leftString = fromUnit?.let { getUnitName(2.0, fromPrefix, it) } ?: "???"
    val rightString = toUnit?.let { getUnitName(2.0, toPrefix, it) } ?: "???"

    if (fromUnit == null || toUnit == null || (fromUnit.unitType != toUnit.unitType)) throw IllegalArgumentException(
        "Conversion from $leftString to $rightString is impossible"
    )
    if (i.sign == -1.0) when (fromUnit.unitType) {
        UnitType.LENGTH -> throw IllegalArgumentException("Length shouldn't be negative")
        UnitType.MASS -> throw IllegalArgumentException("Weight shouldn't be negative")
        else -> ""
    }
    return Conversion(i, fromUnit, toUnit, fromPrefix, toPrefix)
}

fun parsePrefix(unitString: String): Pair<Prefix, Units?> {
    val prefix: Prefix = Prefix.get(unitString)
    val unit = prefix.prefix.let { unitString.replace(it, "", true) }.let { Units.get(it) }
    return Pair(prefix, unit)
}

fun cleanInput(userInput: String): List<String> {
    return userInput.lowercase()
        .split(" ")
        .filterNot { Regex("degrees?", RegexOption.IGNORE_CASE).matches(it) }
        .filterIndexed { i, _ -> i != 2 }.let { if (it.size == 3) it else throw Exception("Parse error") }
}

fun getUnitName(i: Double = 2.0, prefix: Prefix, unit: Units): String {
    return when (unit) {
        Units.CELSIUS, Units.FAHRENHEIT -> (if (i == 1.0) "degree" else "degrees") + " "
        else -> ""
    } + prefix.prefix + unit.getPluralisedName(i)

}


data class Conversion(
    val i: Double,
    val fromUnit: Units,
    val toUnit: Units,
    val fromPrefix: Prefix = Prefix.NULL,
    val toPrefix: Prefix = Prefix.NULL
) {
    val fromName: String
        get() = getUnitName(i, fromPrefix, fromUnit)

    val toName: String
        get() = getUnitName(result, toPrefix, toUnit)

    val result: Double
        get() {
            val baseUnitAmount = (i + fromUnit.offset) * (fromUnit.factor * fromPrefix.factor)
            return (baseUnitAmount / (toUnit.factor * toPrefix.factor)) - toUnit.offset
        }

    fun getFormattedString(): String {
        return "$i $fromName is $result $toName"
    }


}

enum class UnitType {
    TIME, LENGTH, MASS, CURRENT, TEMPERATURE, SUBSTANCE, LUMINOSITY
}