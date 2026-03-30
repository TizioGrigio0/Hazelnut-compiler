package token.tokenscanner;

import lexer.SourceCursor;
import token.*;

public class CharScanner implements TokenScanner{

    public boolean canScan(char c) {
        return c == '\'';
    }

    public Token scan(SourceCursor source) {

        TokenType detected_type = TokenType.CHAR_LITERAL;
        StringBuilder sb = new StringBuilder();

        source.advance(); // Skip the first '
        source.setTokenStart(); // We don't need the first '

        int length = 0;

        while (true) {
            char currentChar = source.peek();

            // If we reached the end of the input without finishing the char
            if (currentChar == '\0') {
                detected_type = TokenType.INVALID;
                break;
            }

            // Manage \
            if (currentChar == '\\') {
                source.advance(); // Consume '\'
                char hold = source.peek(); // Get the character after \
                switch (hold) {
                    case '\\' -> sb.append('\\');
                    case '\'' -> sb.append('\'');
                    case 'n' -> sb.append('\n');
                    case 'r' -> sb.append('\r');
                    case 't' -> sb.append('\t');
                    default -> {
                        detected_type = TokenType.INVALID;
                        sb.append("\\");
                        sb.append(hold);
                        length++;
                    }
                } // switch closure
                length++; // Only add 1 length after consuming '\' and appending the next one
            } // '\' management closure

            // We reach the end of the string as soon as we find another '
            else if (currentChar == '\'') {
                source.advance(); // Skip the last ' to not have it in the next token
                break;
            }
            // If we didn't get any special characters
            else {
                // Append it
                sb.append(currentChar); length++; // Only add more length when appending
                // If we get a non-ascii character, it's not a valid string
                if (currentChar >= 256) { detected_type = TokenType.INVALID; }
            }

            source.advance();
        }

        if (length != 1) detected_type = TokenType.INVALID;
        Token token = source.generateTokenHere(detected_type);
        token.setValue(sb.toString());
        return token;
    }
}
