package errorreporter;

import util.AnsiTextHandler;

public class CompilerError {

    // Enums
    public enum CompilerPhase {
        LEXER,
        PARSER,
    }

    public enum ErrorType {
        WARNING,
        ERROR,
    }

    // Attributes
    private final String message;
    private final int line;
    private final int column;
    private final CompilerPhase phase;
    private final ErrorType errorType;
    private final String source;

    // Constructor
    public CompilerError(String message, int line, int column, CompilerPhase phase, ErrorType errorType, String source) {
        this.message = message;
        this.line = line;
        this.column = column;
        this.phase = phase;
        this.errorType = errorType;
        this.source = source;
    }

    // Getters
    public ErrorType getErrorType() {
        return errorType;
    }

    // Methods
    public String formatError() {
        StringBuilder sb = new StringBuilder();

        // Set the color of the output
        switch (this.errorType) {
                case WARNING -> {
                    sb.append(AnsiTextHandler.getYellow());
                    sb.append("WARNING: ");
                }
                case ERROR -> {
                    sb.append(AnsiTextHandler.getRed());
                    sb.append("ERROR: ");
                }
        }

        sb.append(AnsiTextHandler.getItalic());
        sb.append(message);
        sb.append(AnsiTextHandler.getNoItalic());
        sb.append("; at line: ");
        sb.append(line);
        sb.append(", column: ");
        sb.append(column);
        sb.append(", during ");
        sb.append(phase);
        sb.append(" phase:\n >>> ");
        sb.append(AnsiTextHandler.getItalic());
        sb.append(source);
        sb.append(AnsiTextHandler.getNoItalic());
        sb.append(" <<< \n");

        for(int i=-5; i<column; i++) { sb.append(" "); }
        sb.append("^");

        return sb.toString();
    } // formatError() closure

}
