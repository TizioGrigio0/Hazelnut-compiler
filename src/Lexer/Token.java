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

        input = input.trim();

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

            lastType = type; // Remember the previous type

            // If it's a whitespace (not already managed by the end token part of the code), ignore and go to the next character
            if (!isCharacterWhitespace) {

                // Check for known symbols
                if (c == '=') {
                    if (type == TokenType.UNSET) type = TokenType.EQUALS;
                    else type = TokenType.INVALID;
                    endTokenOnWhitespace = true;
                }
                else if (isValidArithmeticSymbol(c)) {
                    if (type == TokenType.UNSET) type = TokenType.ARITHMETIC_SYMBOL;
                    else type = TokenType.INVALID;
                    endTokenOnWhitespace = true;
                }
                // TODO make it able to get more than 1 char (<<)
                // TODO make logical operators and find a way to differentiate between arithmetical operators (& &&, < << etc)

                // Check for numbers
                else if (Character.isDigit(c)) {
                    if (lastType == TokenType.UNSET) {
                        type = TokenType.NUMBER;
                        endTokenOnWhitespace = true;
                    } else if (lastType != TokenType.NUMBER && lastType != TokenType.IDENTIFIER) { type = TokenType.INVALID; }
                    // if it's already an IDENTIFIER, there is no need to set it again (numbers in the name of the variable)
                }

                // Check for identifiers
                else if (Character.isAlphabetic(c) || isValidIdentifierChar(c) ) {

                    // If it's the start of the token, set it as identifier
                    if (lastType == TokenType.UNSET) {
                        type = TokenType.IDENTIFIER;
                        endTokenOnWhitespace = true;
                    } else if (lastType != TokenType.IDENTIFIER) {
                        type = TokenType.INVALID;
                        lastType = TokenType.INVALID;
                    } // TODO FIX ME
                }

                // If none of the previous checks were right
                else { type = TokenType.INVALID; }

            } // isCharacterWhitespace() closure

            // Check if we reached the end of the token
            if ((type != lastType && lastType != TokenType.UNSET) || (isCharacterWhitespace && endTokenOnWhitespace)) {
                // Create the token
                tokens.add(
                        new Token(
                                lastType,
                                input.substring(tokenStart, i).trim(),
                                tokenStart
                        ) // new Token()
                ); // tokens.add()

                tokenStart = i; // Set up the start of the next token

                type = TokenType.UNSET; // Unset the token
                endTokenOnWhitespace = false;
                i--; // WORKS BUT IDK WHY, DO NOT CHANGE
            } // if closure

        } // for closure

        tokens.add(new Token(type, input.substring(tokenStart).trim(), tokenStart));

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
