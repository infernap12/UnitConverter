package converter


fun test() {
    val test1 = testFormatString()
    val test2 = testCleanUserInput()
    val test3 = testParseUserInput()
    val test4 = testPrefixWithLength()
    val test5 = testTemp1() && testTemp2()
    val result = test1 && test2 && test3 && test4 && test5
    println("\nAll tests successful:\n$result")
}

fun testFormatString(): Boolean {
    println("\nTesting:\n'Conversion(1.0, Units.CELSIUS, Units.KELVIN)getFormattedString()'")
    val expected = "1.0 degree celsius is 274.15 kelvins"
    val actual = Conversion(1.0, Units.CELSIUS, Units.KELVIN).getFormattedString()
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

fun testCleanUserInput(): Boolean {
    println("\nTesting clean:\n-272.15 dc to degrees fahrenheit\n")
    val expected = listOf("-272.15", "dc", "fahrenheit")
    val actual = cleanInput("-272.15 dc to degrees fahrenheit")
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

fun testParseUserInput(): Boolean {
    println("\nTesting:\n1.0 Dc bob K\n")
    val expected = Conversion(1.0, Units.CELSIUS, Units.KELVIN)
    val actual = parseInput("1.0 Dc bob K")
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

fun testPrefixWithLength(): Boolean {
    println("\nTesting:\n1.0 kilometer bringus millimeter\n")
    val expected = "1.0 kilometer is 1000000.0 millimeters"
    val actual = parseInput("1.0 kilometer bringus millimeter").getFormattedString()
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

fun testTemp1(): Boolean {
    println("\nTesting:\n1 F in C\n")
    val expected = "1.0 degree Fahrenheit is -17.22222222222222 degrees Celsius"
    val actual = parseInput("1 F in C").getFormattedString()
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

private fun testTemp2(): Boolean {
    println("\nTesting:\n1 C in F\n")
    val expected = "1.0 degree Celsius is 33.8 degrees Fahrenheit"
    val actual = parseInput("1 C in F").getFormattedString()
    println(expected)
    println(actual)
    val result = expected == actual
    println("\nResult:\n${result}")
    return result
}

