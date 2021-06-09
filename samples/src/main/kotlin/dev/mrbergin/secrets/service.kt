package dev.mrbergin.secrets

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Result
import dev.forkhandles.result4k.Success

sealed interface SuperSecretError

object NoSecretExistsError : SuperSecretError
data class NotAuthorizedForSecretError(val reason: String) : SuperSecretError

data class SecretRequest(
    val secretKey: String,
    val credentials: String,
)

private data class Secret(
    val owner: String,
    val key: String,
    val data: String,
)

private val secrets = listOf(
    Secret(owner = "The Colonel", key = "The KFC Recipe", data = "Lots of MSG"),
    Secret(owner = "Douglas Adams", key = "The Answer to the Ultimate Question of Life, the Universe, and Everything", data = "42"),
)

fun retrieveSuperSecret(request: SecretRequest): Result<String, SuperSecretError> {
    val (secretKey, credentials) = request
    val secret = secrets.firstOrNull { it.key == secretKey } ?: return Failure(NoSecretExistsError)
    return if (secret.owner == credentials) {
        Success(secret.data)
    } else {
        Failure(NotAuthorizedForSecretError("You do not own ${secret.key}"))
    }
}