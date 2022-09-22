[![Build](https://github.com/MrBergin/result4k-kotest-matchers/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/MrBergin/result4k-kotest-matchers/actions/workflows/build.yaml)

# result4k-kotest-matchers

Kotest matchers for the Result4k library

Version Matrix

| result4k-kotest-matchers | forkhandles | kotlin |
|:------------------------:|-------------|--------|
|          1.0.0           | 1.14.0.1    | 1.6.10 |
|            ?             | 2.2.0.0     | 1.7.10 |

## Example Gradle usage:

```kotlin
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("dev.mrbergin:result4k-kotest-matchers:1.0.0")
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