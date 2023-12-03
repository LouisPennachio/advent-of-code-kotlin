fun main() {

    fun part1() {
        val input = readInput("Day01_test")

        fun String.calibrate(): Int {
            val first = firstOrNull {
                it.isDigit()
            }?.digitToIntOrNull() ?: 0

            val second = lastOrNull {
                it.isDigit()
            }?.digitToIntOrNull() ?: 0

            return "${first}${second}".toInt()
        }

        fun Iterable<String>.calibrate(): Int = sumOf {
            it.calibrate()
        }

        println(input.calibrate())
    }

    fun part2() {
        val input = readInput("Day01_test")

        val literalToDigitMapping = mapOf(
            "zero" to 0,
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )

        fun String.calibrate(order: Order): Int? {
            var buffer = ""

            val progression = when (order) {
                Order.ASCENDING -> indices
                Order.DESCENDING -> indices.reversed()
            }

            for (i in progression) {
                val char = get(i)

                buffer = when (order) {
                    Order.ASCENDING -> "$buffer$char"
                    Order.DESCENDING -> "$char$buffer"
                }

                val calibration = literalToDigitMapping.firstNotNullOfOrNull { (literal, digit) ->
                    val isLiteralNumber = when (order) {
                        Order.ASCENDING -> buffer.endsWith(literal)
                        Order.DESCENDING -> buffer.startsWith(literal)
                    }

                    if (isLiteralNumber) {
                        digit
                    } else {
                        null
                    }
                } ?: char.digitToIntOrNull()

                if (calibration != null) {
                    return calibration
                }
            }

            return null
        }

        fun String.calibrate(): Int {
            println("Calibrating $this")

            val first = calibrate(Order.ASCENDING)
            println("Ascending=$first")

            val second = calibrate(Order.DESCENDING)
            println("Descending=$second")

            return "${first}${second}".toInt()
        }

        fun Iterable<String>.calibrate(): Int = sumOf {
            it.calibrate()
        }

        println(input.calibrate())
    }

    part1()
    part2()
}

enum class Order {
    ASCENDING,
    DESCENDING
}
