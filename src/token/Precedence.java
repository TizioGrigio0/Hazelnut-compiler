package token;

public enum Precedence {

    // This precedence only dictates how the token behaves when it appears in the middle or at the end of an expression
    // So dual operators like "++" or '-' will still be processed correctly in both prefix and postfix cases.

    // Low precedence = Last thing processed
    LOWEST,
    ASSIGNMENT,
    LOGICAL_OR,
    LOGICAL_XOR,
    LOGICAL_AND,
    BITWISE_OR,
    BITWISE_XOR,
    BITWISE_AND,
    EQUALITY,
    COMPARISON,
    BITWISE_SHIFT,
    SUM,
    PRODUCT,
    EXPONENT,
    PREFIX,
    CALL_INDEX,      // () []
    POSTFIX,        // ++ --
    HIGHEST,
    // High precedence = First thing processed

}
