package token.tokenscanner;

import errorreporter.CompilerError;
import lexer.Lexer;
import lexer.LexerErrorMessage;
import lexer.SourceCursor;
import token.*;

public class StringScanner extends TokenScanner{

    public StringScanner(Lexer lexer) {
        super(lexer);
    }

    public boolean canScan(char c) {
        return c == '"';
    }

    public Token scan(SourceCursor source) {

        TokenType detected_type = TokenType.STRING_LITERAL;
        StringBuilder sb = new StringBuilder();

        source.advance(); // Skip the first "
        source.setTokenStart(); // We don't need the first "

        while (true) {
            char currentChar = source.peek();

            // If we reached the end of the input without finishing the string
            if (currentChar == '\0') {
                detected_type = TokenType.INVALID;
                lexer.generateError(CompilerError.ErrorType.ERROR, LexerErrorMessage.UNTERMINATED_LITERAL, "string");
                break;
            }

            // Manage \
            if (currentChar == '\\') {
                source.advance(); // Consume '\'
                char hold = source.peek(); // Get the character after \
                switch (hold) {
                    case '\\' -> sb.append('\\');
                    case '"' -> sb.append('"');
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    default -> {
                        detected_type = TokenType.INVALID;
                        sb.append("\\"); sb.append(hold);
                        lexer.generateError(CompilerError.ErrorType.ERROR, LexerErrorMessage.INVALID_ESCAPE, hold);
                    }
                } // switch closure
            } // '\' management closure

            // We reach the end of the string as soon as we find another "
            else if (currentChar == '"') {
                source.advance(); // Skip the last " to not have it in the next token
                break;
            }
            // If we didn't get any special characters
            else {
                // Append it
                sb.append(currentChar);
                // If we get a non-ascii character, it's not a valid string
                if (currentChar >= 256) {
                    detected_type = TokenType.INVALID;
                    lexer.generateError(CompilerError.ErrorType.ERROR, LexerErrorMessage.INVALID_CHARACTER, currentChar);
                }
            }

            source.advance();
        }

        Token token = source.generateTokenHere(detected_type);
        token.setValue(sb.toString());
        return token;
    }
}
