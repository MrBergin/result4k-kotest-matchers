![TeamCity Full Build Status](https://img.shields.io/teamcity/build/s/Result4kKotestMatchers_Build?server=https%3A%2F%2Fmrbergin.beta.teamcity.com&style=for-the-badge)
![TeamCity Coverage](https://img.shields.io/teamcity/coverage/Result4kKotestMatchers_Build?server=https%3A%2F%2Fmrbergin.beta.teamcity.com&style=for-the-badge)

# result4k-kotest-matchers
Kotest matchers for the Result4k library

## Example Gradle usage:
```kotlin
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("dev.mrbergin:result4k-kotest-matchers:0.0.2")    
}
```

## Some example usage:
```kotlin

fun exampleDivideByZero() {
    val result = divide(5, 0)

    //all of these would pass
    result.shouldBeFailure()
    result shouldBeFailure DivideByZeroFailure
    result shouldBeFailure { reason ->
        reason shouldBe DivideByZeroFailure
    }

    //but this will fail with the appropriate message
    result shouldBeSuccess 5 //Throws AssertionError like: Failure(DivideByZero) should be Success(5)
}

object DivideByZeroFailure

fun divide(dividend: Int, divisor: Int): Result<Int, DivideByZeroFailure> {
    return if (divisor == 0) {
        Failure(DivideByZeroFailure)
    } else {
        Success(dividend / divisor)
    }
}
```