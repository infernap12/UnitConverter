package converter

import kotlin.math.pow

enum class Prefix(val base10: Int) {
    QUETTA(30),
    RONNA(27),
    YOTTA(24),
    ZETTA(21),
    EXA(18),
    PETA(15),
    TERA(12),
    GIGA(9),
    MEGA(6),
    KILO(3),
    HECTO(2),
    DECA(1),
    DECI(-1),
    CENTI(-2),
    MILLI(-3),
    MICRO(-6),
    NANO(-9),
    PICO(-12),
    FEMTO(-15),
    ATTO(-18),
    ZEPTO(-21),
    YOCTO(-24),
    RONTO(-27),
    QUECTO(-30),
    NULL(0);

    val factor: Double
        get() = 10.0.pow(base10)

    val prefix: String
        get() = if (this == NULL) "" else name.lowercase()

    companion object {
        fun get(string: String): Prefix {
            return when (string) {
                "km", "kg" -> KILO
                "mm", "mg" -> MILLI
                "cm" -> CENTI
                else -> entries.dropLast(1).singleOrNull { string.contains(it.prefix) } ?: NULL
            }
        }
    }
}