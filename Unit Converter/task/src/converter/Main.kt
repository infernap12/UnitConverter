package converter

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        val userInput = readln()
        if (userInput == "exit") {
            return
        }
        println()
        val request: ConversionRequest
        try {
            request = parseInput(userInput)
        } catch (e: Exception) {
            println(e.message)
            continue
        }
        val amount = request.i
        val fromUnitName = request.fromName
        val result: Double = convert(request)
        val toUnitName = request.to.getPluralisedName(result)
        println("$amount $fromUnitName is $result $toUnitName")
        println()
    }

}

fun convert(request: ConversionRequest): Double {
    val baseUnitAmount = request.i * request.from.factor
    return baseUnitAmount / request.to.factor
}

fun parseInput(userInput: String): ConversionRequest {
    val (iString, fromString, toString) = userInput.lowercase().split(" ").filterIndexed { i, v -> i != 2 }
    val i: Double
    try {
        i = iString.toDouble()
    } catch (_: NumberFormatException) {
        throw NumberFormatException("Wrong input.")
    }
    val from = Units.get(fromString) // can be null
    val to = Units.get(toString) // can be null
    if (from == null || to == null || (from.unitType != to.unitType)) throw ConversionException(
        "Conversion from ${from?.plural ?: "???"} to ${to?.plural ?: "???"} is impossible"
    )
    return ConversionRequest(i, from, to)
}




enum class Units(val factor: Double, val symbol: String, val plural: String, val unitType: UnitType) {
    METER(1.0, "m", "meters", UnitType.LENGTH),
    KILOMETER(1000.0, "km", "kilometers", UnitType.LENGTH),
    CENTIMETER(0.01, "cm", "centimeters", UnitType.LENGTH),
    MILLIMETER(0.001, "mm", "millimeters", UnitType.LENGTH),
    MILE(1609.35, "mi", "miles", UnitType.LENGTH),
    YARD(0.9144, "yd", "yards", UnitType.LENGTH),
    FOOT(0.3048, "ft", "feet", UnitType.LENGTH),
    INCH(0.0254, "in", "inches", UnitType.LENGTH),

    GRAM(0.001, "g", "grams", UnitType.WEIGHT),
    KILOGRAM(1.0, "kg", "kilograms", UnitType.WEIGHT),
    MILLIGRAM(0.000001, "mg", "milligrams", UnitType.WEIGHT),
    POUND(0.453592, "lb", "pounds", UnitType.WEIGHT),
    OUNCE(0.0283495, "oz", "ounces", UnitType.WEIGHT);

    fun getPluralisedName(i: Double): String = if (i == 1.0) single else plural

    val single: String
        get() = name.lowercase()


    companion object {
        fun getBy(unitString: String, selector: (Units) -> String): Units? {
            return Units.entries.firstOrNull { selector(it) == unitString }
        }

        fun get(string: String): Units? {
            return when {
                string.length <= 2 -> getBy(string) { it.symbol }
                else -> getBy(string) { it.single } ?: getBy(string) { it.plural }
            }
        }
    }
}

open class ConversionException : IllegalArgumentException {
    constructor(message: String?) : super(message)
}

enum class UnitType {
    WEIGHT, LENGTH;
}

data class ConversionRequest(val i: Double, val from: Units, val to: Units) {
    val fromName: String
        get() = from.getPluralisedName(i)
}
