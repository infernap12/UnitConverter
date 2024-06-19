package converter

enum class Units(
    val factor: Double,
    val symbol: String,
    val plural: String,
    val unitType: UnitType,
    val offset: Double = 0.0
) {
    // Time
    SECOND(1.0, "s", "seconds", UnitType.TIME),
    MINUTE(60.0, "min", "minutes", UnitType.TIME),
    HOUR(3600.0, "hr", "hours", UnitType.TIME),
    DAY(86400.0, "d", "days", UnitType.TIME),

    // Length
    METER(1.0, "m", "meters", UnitType.LENGTH),
    SMOOT(1.702, "smoot", "smoots", UnitType.LENGTH),

    THOU(0.0000254, "thou", "thou", UnitType.LENGTH),
    INCH(0.0254, "in", "inches", UnitType.LENGTH),
    FOOT(0.3048, "ft", "feet", UnitType.LENGTH),
    YARD(0.9144, "yd", "yards", UnitType.LENGTH),
    MILE(1609.35, "mi", "miles", UnitType.LENGTH),
    LEAGUE(4828.032, "le", "leagues", UnitType.LENGTH),

    // Mass
    GRAM(0.001, "g", "grams", UnitType.MASS),
    TONNE(1000.0, "t", "tonnes", UnitType.MASS),
    GRAIN(0.0000648, "gr", "grains", UnitType.MASS),
    OUNCE(0.0283495, "oz", "ounces", UnitType.MASS),
    POUND(0.453592, "lb", "pounds", UnitType.MASS),
    TON(907.18, "tn", "tons", UnitType.MASS),

    // Electric Current
    AMP(1.0, "a", "amps", UnitType.CURRENT),

    // Thermodynamic Temperature
    KELVIN(1.0, "k", "kelvins", UnitType.TEMPERATURE),
    CELSIUS(1.0, "c", "celsius", UnitType.TEMPERATURE, 273.15),
    FAHRENHEIT(5.0 / 9, "f", "Fahrenheit", UnitType.TEMPERATURE, 459.67),

    // Amount of Substance
    MOL(1.0, "n", "mols", UnitType.SUBSTANCE),

    // Luminous Intensity
    CANDELA(1.0, "cd", "candela", UnitType.LUMINOSITY),
    CANDLEPOWER(0.981, "cp", "candlepower", UnitType.LUMINOSITY),
    HEFNERKERZE(0.920, "hk", "HEFNERKERZE", UnitType.LUMINOSITY);


    fun getPluralisedName(i: Double): String = if (i == 1.0) single else plural

    val single: String
        get() = name.lowercase()


    companion object {
        fun getBy(unitString: String, selector: (Units) -> String): Units? {
            return Units.entries.firstOrNull { selector(it) == unitString }
        }

        fun get(string: String): Units? {
            return when (string) {
                "km", "cm", "mm" -> METER
                "kg", "mg" -> GRAM
                "df" -> FAHRENHEIT
                "dc" -> CELSIUS
                else -> when {
                    string.length <= 2 -> getBy(string) { it.symbol }
                    else -> getBy(string) { it.single } ?: getBy(string) { it.plural }
                }
            }
        }
    }
}