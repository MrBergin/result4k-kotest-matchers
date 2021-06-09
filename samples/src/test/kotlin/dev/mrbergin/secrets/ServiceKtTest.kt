package dev.mrbergin.secrets

import dev.mrbergin.kotest.result4k.beFailure
import dev.mrbergin.kotest.result4k.beSuccess
import dev.mrbergin.kotest.result4k.shouldBeFailure
import dev.mrbergin.kotest.result4k.shouldBeSuccess
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ServiceKtTest : StringSpec({

    "Author should know his own secret, but not KFC" {
        val positiveResult = retrieveSuperSecret(
            SecretRequest("The Answer to the Ultimate Question of Life, the Universe, and Everything", "Douglas Adams")
        )

        val expectedAnswer = "42"

        positiveResult should beSuccess()
        positiveResult should beSuccess(expectedAnswer)
        positiveResult.shouldBeSuccess()
        positiveResult shouldBeSuccess expectedAnswer
        positiveResult shouldBeSuccess {
            it.shouldBeInstanceOf<String>()
            it shouldBe expectedAnswer
        }
    }

    "Author should not know about KFC" {
        val expectedAuthorizationError = NotAuthorizedForSecretError("You do not own The KFC Recipe")

        val notAllowedResult = retrieveSuperSecret(
            SecretRequest("The KFC Recipe", "Douglas Adams")
        )

        notAllowedResult should beFailure()
        notAllowedResult should beFailure(expectedAuthorizationError)
        notAllowedResult.shouldBeFailure()
        notAllowedResult shouldBeFailure expectedAuthorizationError
        notAllowedResult shouldBeFailure {
            it.shouldBeInstanceOf<NotAuthorizedForSecretError>()
            it shouldBe expectedAuthorizationError
        }
    }

    "This secret doesn't exists in our system" {
        val notExistsResult = retrieveSuperSecret(
            SecretRequest("iddqd", "John Romero")
        )

        notExistsResult should beFailure()
        notExistsResult should beFailure(NoSecretExistsError)
        notExistsResult.shouldBeFailure()
        notExistsResult shouldBeFailure NoSecretExistsError
        notExistsResult shouldBeFailure {
            it.shouldBeInstanceOf<NoSecretExistsError>()
            it shouldBe NoSecretExistsError
        }
    }

})
