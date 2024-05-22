package org.gaming.hub.business.exception.enumeration

internal enum class UseCaseErrorCode(
    val number: Int,
    private val errorMessage: String,
    private val isMessageWithPattern: Boolean = false
) {
    REQUEST_DATA_USER_NOT_FOUND(1, ErrorMessagePattern.USER_NOT_FOUND, true),
    REQUEST_DATA_CURRENCY_NOT_FOUND(2, ErrorMessagePattern.CURRENCY_NOT_FOUND, true),
    REQUEST_DATA_USER_BALANCE_NOT_FOUND(3, ErrorMessagePattern.USER_BALANCE_NOT_FOUND, true),
    REQUEST_DATA_OPERATION_AMOUNT_LESS_THEN_OR_ZERO(
        4,
        ErrorMessagePattern.OPERATION_AMOUNT_LESS_THAN_ZERO,
        true
    ),
    USER_DOES_NOT_HAVE_ENOUGH_MONEY(
        5,
        ErrorMessagePattern.USER_DOES_NOT_HAVE_ENOUGH_MONEY,
        true
    );

    fun getExceptionMessage(vararg messageParams: Any?): String =
        if (isMessageWithPattern) errorMessage.format(*messageParams) else errorMessage
}

private object ErrorMessage {
    // ...
}

private object ErrorMessagePattern {
    const val USER_NOT_FOUND = "User (id:%s and login:%s) not found"
    const val CURRENCY_NOT_FOUND = "Currency (name: %s) not found"
    const val USER_BALANCE_NOT_FOUND =
        "User balance with params (userId: %s, gameSessionId: %s, currencyType: %s) not found"
    const val OPERATION_AMOUNT_LESS_THAN_ZERO = "Amount (%s) of operation should be more than zero"
    const val USER_DOES_NOT_HAVE_ENOUGH_MONEY = "User (id: %s) doesn't have enough money (balance = %s) for credit (%s)"
}