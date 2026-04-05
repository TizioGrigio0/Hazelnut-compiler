package lexer;

public enum LexerErrorMessage {
    MALFORMED_CHAR("Malformed char '%s'"),
    INVALID_CHARACTER("Invalid character: '%s'"),
    UNTERMINATED_LITERAL("Unterminated %s"),
    MALFORMED_NUMBER("Malformed number '%s"),
    INVALID_SYMBOL("Invalid symbol: '%s'"),
    INVALID_ESCAPE("Invalid escape sequence: '\\%s");

    private final String errorMessage;

    LexerErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Formats the message based on the arguments
    public String format(Object... args) {
        return String.format(errorMessage, args);
    }
}