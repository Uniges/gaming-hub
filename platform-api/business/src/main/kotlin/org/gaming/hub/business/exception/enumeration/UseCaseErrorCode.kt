package org.gaming.hub.business.exception.enumeration

internal enum class UseCaseErrorCode(
    val number: Int,
    private val errorMessage: String,
    private val isMessageWithPattern: Boolean = false
) {
    REQUEST_DATA_USER_NOT_FOUND(1, ErrorMessagePattern.USER_NOT_FOUND, true),
    REQUEST_DATA_CURRENCY_NOT_FOUND(2, ErrorMessagePattern.CURRENCY_NOT_FOUND, true),
    REQUEST_DATA_USER_BALANCE_NOT_FOUND(3, ErrorMessagePattern.USER_BALANCE_NOT_FOUND, true),
    REQUEST_DATA_IDEMPOTENT_TOKEN_BLANK(4, ErrorMessage.IDEMPOTENT_TOKEN_BLANK),
    REQUEST_DATA_IDEMPOTENT_TOKEN_NOT_FOUND(
        5,
        ErrorMessagePattern.IDEMPOTENT_TOKEN_NOT_FOUND,
        true
    ),
    REQUEST_DATA_OPERATION_AMOUNT_LESS_THEN_OR_ZERO(
        6,
        ErrorMessagePattern.OPERATION_AMOUNT_LESS_THAN_ZERO,
        true
    ),
    USER_DOES_NOT_HAVE_ENOUGH_MONEY(
        7,
        ErrorMessagePattern.USER_DOES_NOT_HAVE_ENOUGH_MONEY,
        true
    ),
    IDEMPOTENT_TOKEN_DOES_NOT_RELATE_WITH_USER(
        8,
        ErrorMessagePattern.IDEMPOTENT_TOKEN_DOES_NOT_RELATE_WITH_USER,
        true
    ),
    REQUEST_DATA_DUPLICATE_TRANSACTION(
        9,
        ErrorMessage.REQUEST_DATA_DUPLICATE_TRANSACTION
    );

    fun getExceptionMessage(vararg messageParams: Any?): String =
        if (isMessageWithPattern) errorMessage.format(*messageParams) else errorMessage
}

private object ErrorMessage {
    const val IDEMPOTENT_TOKEN_BLANK = "Idempotent token is blank"
    const val REQUEST_DATA_DUPLICATE_TRANSACTION = "Duplicate transaction, idempotent token has already been used"
}

private object ErrorMessagePattern {
    const val USER_NOT_FOUND = "User (id:%s and login:%s) not found"
    const val CURRENCY_NOT_FOUND = "Currency (name: %s) not found"
    const val USER_BALANCE_NOT_FOUND =
        "User balance with params (userId: %s, gameSessionId: %s, currencyType: %s) not found"
    const val OPERATION_AMOUNT_LESS_THAN_ZERO = "Amount (%s) of operation should be more than zero"
    const val USER_DOES_NOT_HAVE_ENOUGH_MONEY = "User (id: %s) doesn't have enough money (balance = %s) for credit (%s)"
    const val IDEMPOTENT_TOKEN_NOT_FOUND = "Idempotent token %s not found"
    const val IDEMPOTENT_TOKEN_DOES_NOT_RELATE_WITH_USER = "Idempotent token (%s) does not belong to the user (%s)"
}