package Lexer;

import java.util.ArrayList;
import java.util.List;


public class Token {
    private final TokenType type;     // The identifier of the type of the token
    private final String value;       // Value inside the token, might get converted later
    private final int position;       // Token position (line)

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
        this.position = -1;
    }

    public Token(TokenType type, String value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();

        int tokenStart = 0; // First index of the token in the string

        TokenType type = TokenType.UNSET;
        TokenType lastType = TokenType.UNSET;

        int length = input.length();

        boolean isCharacterWhitespace = false;
        boolean endTokenOnWhitespace = false;

        for(int i=0; i < length; i++) {

            char c = input.charAt(i);
            isCharacterWhitespace = Character.isWhitespace(c);

            // Check if we reached the end of the token
            if ((type != lastType && lastType != TokenType.UNSET) || (isCharacterWhitespace && endTokenOnWhitespace)) {
                // Create the token
                tokens.add(
                        new Token(
                                type,
                                input.substring(tokenStart, i).trim(),
                                tokenStart
                        ) // new Token()
                ); // tokens.add()

                tokenStart = i; // Set up the start of the next token
                type = TokenType.UNSET; // Unset the token
                endTokenOnWhitespace = false;
            } // if closure

            lastType = type; // Remember the previous type

            // If it's a whitespace (not already managed by the end token part of the code), ignore and go to the next character
            if (isCharacterWhitespace) { continue; }

            // Check for known symbols
            if (c == '=') { type = TokenType.EQUALS; }
            else if (isValidArithmeticSymbol(c)) { type = TokenType.ARITHMETIC_SYMBOL; } // TODO make it able to get more than 1 char (<<)

            // Check for numbers
            else if (Character.isDigit(c)) {
                // Set it as number if it's not an identifier
                if (lastType != TokenType.IDENTIFIER) { type = TokenType.NUMBER; endTokenOnWhitespace = true; }
                // if it's already an IDENTIFIER, there is no need to set it again (numbers in the name of the variable)
            }

            // Check for identifiers
            else if (Character.isAlphabetic(c) || isValidIdentifierChar(c) ) {

                // If it's the start of the token, set it as identifier
                if (lastType == TokenType.UNSET) {
                    type = TokenType.IDENTIFIER;
                    endTokenOnWhitespace = true;
                }
                if (lastType == TokenType.NUMBER) { type = TokenType.INVALID; }
            }

            // If none of the previous checks were right
            else { type = TokenType.INVALID; }
            if (type == TokenType.INVALID) lastType = TokenType.INVALID;

        } // for closure

        tokens.add(new Token(lastType, input.substring(tokenStart).trim(), tokenStart));

        return tokens;
    }

    @Override
    public String toString() {
        return "Token{" +
                "TYPE=" + type +
                ", VALUE='" + value + '\'' +
                ", POSITION=" + position +
                '}';
    }

    static private boolean isValidIdentifierChar(char c) {
        String allowedChars = "_";
        int length = allowedChars.length();
        for (int i=0; i<length; i++) {
            if (c == allowedChars.charAt(i)) return true;
        }
        return false;
    }

    static private boolean isValidArithmeticSymbol(char c) {
        String allowedChars = "+-*/%";
        int length = allowedChars.length();
        for (int i=0; i<length; i++) {
            if (c == allowedChars.charAt(i)) return true;
        }
        return false;
    }
}
