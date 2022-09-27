[![Build](https://github.com/MrBergin/result4k-kotest-matchers/actions/workflows/build.yaml/badge.svg?branch=main)](https://github.com/MrBergin/result4k-kotest-matchers/actions/workflows/build.yaml)
[![MavenCentral](https://img.shields.io/maven-metadata/v.svg?label=maven-central&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fdev%2Fmrbergin%2Fresult4k-kotest-matchers%2Fmaven-metadata.xml)](https://search.maven.org/search?q=a:result4k-kotest-matchers)


# result4k-kotest-matchers

Kotest matchers for the [Result4k](https://github.com/fork-handles/forkhandles/tree/trunk/result4k) library

## Version Matrix

| result4k-kotest-matchers | forkhandles | kotlin | java |
|:------------------------:|-------------|--------|------|
|          1.0.0           | 1.14.0.1    | 1.6.10 | 8    |
|        2022-09-22        | 2.2.0.0     | 1.7.10 | 17   |
| 2022.09.24.1..2022.9.26  | 2.2.0.0     | 1.7.10 | 8    |

## Example Gradle usage:

```kotlin
repositories {
    mavenCentral()
}
dependencies {
    testImplementation("dev.mrbergin:result4k-kotest-matchers:2022.9.26")
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