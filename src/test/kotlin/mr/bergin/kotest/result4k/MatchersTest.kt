package mr.bergin.kotest.result4k

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class MatchersTest {
    @Test
    fun `when Failure is passed to shouldBeSuccess, then throw an AssertionError with correct message`() {
        val expectedValue = "I have failed..."
        val subject = Failure(expectedValue)

        assertAll(
            { assertThrows<AssertionError> { subject.shouldBeSuccess() } },
            { assertThrows<AssertionError> { subject.shouldBeSuccess { } } },
            {
                val error = assertThrows<AssertionError> { subject.shouldBeSuccess(expectedValue) }
                assertThat(error.message ?: "", containsSubstring(expectedValue))
            },
        )
    }

    @Test
    fun `when Success is passed to shouldBeFailure, then throw an AssertionError with correct message`() {
        val expectedValue = "Great success!"
        val subject = Success(expectedValue)

        assertAll(
            { assertThrows<AssertionError> { subject.shouldBeFailure() } },
            { assertThrows<AssertionError> { subject.shouldBeFailure { } } },
            {
                val error = assertThrows<AssertionError> { subject.shouldBeFailure(expectedValue) }
                assertThat(error.message ?: "", containsSubstring(expectedValue))
            },
        )
    }

    @Test
    fun `when Success is passed to shouldBeSuccess, then don't throw an AssertionError`() {
        val expectedValue = 1
        val subject = Success(expectedValue)

        assertAll(
            { assertDoesNotThrow { subject.shouldBeSuccess() } },
            { assertDoesNotThrow { subject.shouldBeSuccess { } } },
            { assertDoesNotThrow { subject.shouldBeSuccess(expectedValue) } },
        )
    }

    @Test
    fun `when Failure is passed to shouldBeFailure, then don't throw an AssertionError`() {
        val expectedValue = 1
        val subject = Failure(expectedValue)

        assertAll(
            { assertDoesNotThrow { subject.shouldBeFailure() } },
            { assertDoesNotThrow { subject.shouldBeFailure { } } },
            { assertDoesNotThrow { subject.shouldBeFailure(expectedValue) } },
        )
    }

    @Test
    fun `when Failure is passed to shouldBeFailure, but values don't match then throw an AssertionError`() {
        val expectedValue = "Some value here"
        val unexpectedValue = "Another value there"
        val subject = Failure(expectedValue)

        val error = assertThrows<AssertionError> { subject.shouldBeFailure(unexpectedValue) }
        val message = error.message ?: ""
        assertAll(
            { assertThat(message, containsSubstring(unexpectedValue)) },
            { assertThat(message, containsSubstring(expectedValue)) }
        )
    }

    @Test
    fun `when Success is passed to shouldBeSuccess, but values don't match then throw an AssertionError`() {
        val expectedValue = "Yet another value"
        val unexpectedValue = "But wait, there's, more!"
        val subject = Failure(expectedValue)

        val error = assertThrows<AssertionError> { subject.shouldBeFailure(unexpectedValue) }
        val message = error.message ?: ""
        assertAll(
            { assertThat(message, containsSubstring(unexpectedValue)) },
            { assertThat(message, containsSubstring(expectedValue)) }
        )
    }
}
