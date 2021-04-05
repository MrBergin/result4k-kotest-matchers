package mr.bergin.kotest.result4k

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
            "$value should be $expectedSuccess",
            "$value should not be $expectedSuccess",
        )
    }
}

/**
 * @return a matcher that tests a value is a [Success]
 */
fun beSuccess(): Matcher<Result<*, *>> = object : Matcher<Result<*, *>> {
    override fun test(value: Result<*, *>) = MatcherResult(
        value is Success<*>,
        "$value should be Success",
        "$value should not be Success",
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

infix fun <R> Result<R, *>.shouldBeSuccess(block: (Success<R>) -> Unit) {
    this.shouldBeSuccess()
    block(this as Success<R>)
}

infix fun <T> Result<T, *>.shouldBeSuccess(value: T) = this should beSuccess(value)

fun <F> beFailure(r: F): Matcher<Result<*, F>> = object : Matcher<Result<*, F>> {
    override fun test(value: Result<*, F>) = MatcherResult(
        value == Failure(r),
        "$value should be Failure($r)",
        "$value should not be Failure($r)",
    )
}

fun beFailure(): Matcher<Result<*, *>> = object : Matcher<Result<*, *>> {
    override fun test(value: Result<*, *>) = MatcherResult(
        value is Failure<*>,
        "$value should be Failure",
        "$value should not be Failure",
    )
}

@OptIn(ExperimentalContracts::class)
fun <E> Result<*, E>.shouldBeFailure() {
    contract {
        returns() implies (this@shouldBeFailure is Failure<*>)
    }
    this should beFailure()
}

infix fun <E> Result<*, E>.shouldBeFailure(block: (Failure<E>) -> Unit) {
    this.shouldBeFailure()
    block(this as Failure<E>)
}

infix fun <E> Result<*, E>.shouldBeFailure(value: E) = this should beFailure(value)
