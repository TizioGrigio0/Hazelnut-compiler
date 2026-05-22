package parser;

public enum ParserErrorMessage {

    UNEXPECTED_TOKEN("Unexpected token: '%s'"),
    INVALID_ASSIGNMENT_TARGET("Invalid assignment target. Cannot assign a value to '%s'"),
    EXPECTED_TOKEN("Expected '%s', but found '%s'"),
    EXPECTED_EXPRESSION("Expected an expression, but found '%s'"),
    EXPECTED_IDENTIFIER("Expected an identifier, but found '%s'"),
    EXPECTED_STATEMENT("Expected a statement, but found '%s'");

    private final String errorMessage;

    ParserErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // Formats the message based on the arguments
    public String format(Object... args) {
        return String.format(errorMessage, args);
    }

}