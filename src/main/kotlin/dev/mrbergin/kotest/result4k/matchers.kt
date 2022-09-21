package dev.mrbergin.kotest.result4k

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @return a matcher that tests a value is a [Success] wrapping the [expectedValue]
 */
fun <T> beSuccess(expectedValue: T): Matcher<Result<T, *>> = object : Matcher<Result<T, *>> {
    override fun test(value: Result<T, *>): MatcherResult {
        val expectedSuccess = Success(expectedValue)
        return MatcherResult(
            value == expectedSuccess,
            { "$value should be $expectedSuccess" },
            { "$value should not be $expectedSuccess" },
        )
    }
}

/**
 * @return a matcher that tests a value is a [Success]
 */
fun beSuccess(): Matcher<Result<*, *>> = object : Matcher<Result<*, *>> {
    override fun test(value: Result<*, *>) = MatcherResult(
        value is Success<*>,
        { "$value should be Success" },
        { "$value should not be Success" },
    )
}

/**
 * Tests [this] is of type [Success]
 */
@OptIn(ExperimentalContracts::class)
fun <T> Result<T, *>.shouldBeSuccess() {
    contract {
        returns() implies (this@shouldBeSuccess is Success<*>)
    }
    this should beSuccess()
}

/**
 * Tests [this] is of type [Success]
 *
 * @param [block] - lambda which passes in [this] cast to [Success]
 */
infix fun <R> Result<R, *>.shouldBeSuccess(block: (R) -> Unit) {
    this.shouldBeSuccess()
    block((this as Success<R>).value)
}

/**
 * Tests [this] is of type [Success] wrapping [value]
 *
 * @param value - the expected value wrapped in a [Success]
 */
infix fun <T> Result<T, *>.shouldBeSuccess(value: T) = this should beSuccess(value)

/**
 * @return a matcher that tests a value is a [Failure] wrapping the [expectedValue]
 */
fun <F> beFailure(expectedValue: F): Matcher<Result<*, F>> = object : Matcher<Result<*, F>> {
    override fun test(value: Result<*, F>) = MatcherResult(
        value == Failure(expectedValue),
        { "$value should be Failure($expectedValue)" },
        { "$value should not be Failure($expectedValue)" },
    )
}

/**
 * @return a matcher that tests a value is a [Failure]
 */
fun beFailure(): Matcher<Result<*, *>> = object : Matcher<Result<*, *>> {
    override fun test(value: Result<*, *>) = MatcherResult(
        value is Failure<*>,
        { "$value should be Failure" },
        { "$value should not be Failure" },
    )
}

/**
 * Tests [this] is of type [Failure]
 */
@OptIn(ExperimentalContracts::class)
fun <E> Result<*, E>.shouldBeFailure() {
    contract {
        returns() implies (this@shouldBeFailure is Failure<*>)
    }
    this should beFailure()
}

/**
 * Tests [this] is of type [Failure]
 *
 * @param [block] - lambda which passes in [this] cast to [Failure]
 */
infix fun <E> Result<*, E>.shouldBeFailure(block: (E) -> Unit) {
    this.shouldBeFailure()
    block((this as Failure<E>).reason)
}

/**
 * Tests [this] is of type [Failure] wrapping [value]
 *
 * @param value - the expected value wrapped in a [Failure]
 */
infix fun <E> Result<*, E>.shouldBeFailure(value: E) = this should beFailure(value)
