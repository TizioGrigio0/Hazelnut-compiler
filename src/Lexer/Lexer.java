package Lexer;

import Enums.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    // ----- Attributes -----

    String input = "";
    private int tokenStart = 0; // First index of the token in the string
    private int currentIndex = 0;

    private int currentPositionInLine = -1;
    private int currentLine = 0;

    // ----- Constructors -----
    public Lexer() {}

    // ----- Methods -----

    /// Given an input, returns a list of all the tokens in it
    /// Automatically places EOF at the end of the list
    public List<Token> tokenize(String input) {

        this.input = input.trim();
        int length = this.input.length();
        this.tokenStart = 0;     // Reset tokenStart

        this.currentPositionInLine = 0;
        this.currentLine = 0;

        List<Token> tokens = new ArrayList<>();

        for (this.currentIndex = 0; this.currentIndex < length; this.currentIndex++) {
            this.currentPositionInLine++; // Advance the position in line

            this.tokenStart = currentIndex; // Set the start of the new token at the current index
            char c = this.input.charAt(this.currentIndex);
            // Ignore whitespaces
            if (Character.isWhitespace(c)) {
                checkNewline(c);
                continue;
            }
            // Get the token and add it to the list of tokens
            tokens.add( scanToken() ); // scanToken() sets tokenStart and currentIndex
        } // for closure
        tokens.add(new Token(TokenType.EOF, "", this.currentIndex, this.currentLine, this.currentPositionInLine)); // EOF at the end of the input
        return tokens;
    } // tokenize closure

    // Dispatcher pattern
    private Token scanToken() {
        final char firstChar = this.input.charAt(this.tokenStart);
        // Search for number
        if ( Character.isDigit(firstChar) ) { return scanNumber(); }
        // Otherwise search for identifiers
        else if ( isIdentifiersChar(firstChar) ) { return scanIdentifier(); }
        // Otherwise search for symbols (default case)
        else { return scanSymbol(); }
    }

    private Token scanIdentifier() {
        int firstIndex = this.currentIndex;

        for (int i=this.tokenStart; i < this.input.length(); i++) {
            char currentChar = this.input.charAt(i);
            // If the character is alphanumeric, then it's a valid identifier
            if (isIdentifiersChar(currentChar)) {
                currentIndex = i;
            } else {
                break;
            } // else closure
        } // for closure

        String detected_string = input.substring(tokenStart, currentIndex+1);
        TokenType detected_type = TokenType.decode(detected_string);
        if (detected_type == null) detected_type = TokenType.IDENTIFIER;

        return new Token(
                detected_type,
                detected_string,
                firstIndex,
                this.currentLine,
                this.currentPositionInLine
        );
    } // scanIdentifier() closure

    private Token scanNumber() {
        int firstIndex = this.currentIndex;

        int i=this.tokenStart;
        TokenType detected_type = TokenType.NUMBER;
        boolean decimalPoint = false; // Did we find a decimal dot up until now?

        char base = 'd';

        { // Numerical base scope
            char first_char = this.input.charAt(i);

            // Check the numerical base
            if (first_char == '0' && i+1 < this.input.length()) { // If the first character is 0 and the length of the input is at least 2
                char base_identifier = this.input.charAt(i+1);
                if (isValidNumericalBaseCharacter(base_identifier)) {
                    base = base_identifier;
                    i += 2; // Jump the prefix
                    this.currentIndex = i-1; // Keep the currentIndex in sync
                    // If there is no other input after '0x' it's invalid
                    if (i >= this.input.length() || Character.isWhitespace(this.input.charAt(i))) {
                        detected_type = TokenType.INVALID;
                    }
                }
            }

            base = Character.toLowerCase(base);

        } // Numerical base scope closure

        for (; i < this.input.length(); i++) {

            char currentChar = this.input.charAt(i);

            // If it's a valid character for our number
            if (isValidDigitForBase(currentChar, base) || currentChar == '.') {
                // Check for double decimal point (and set as invalid accordingly)
                if (currentChar == '.') {
                    if (base != 'd') detected_type = TokenType.INVALID; // Decimal point is only allowed in decimal base
                    if (decimalPoint) detected_type = TokenType.INVALID;
                    decimalPoint = true;
                }
                currentIndex = i;
            } else if (isIdentifiersChar(currentChar)) {
                currentIndex = i;
                detected_type = TokenType.INVALID;
            } else {
                break;
            } // else closure
        } // for closure

        char lastChar = input.charAt(currentIndex);
        if (lastChar == '.' || lastChar == '_') { // If the last character is a . or a _, then mark it as invalid
            // We are marking _ last character as invalid because it probably means that the user missed it, it could be a typo
            detected_type = TokenType.INVALID;
        }

        return new Token(
                detected_type,
                input.substring(tokenStart, currentIndex+1),
                firstIndex,
                this.currentLine,
                this.currentPositionInLine
        );
    } // scanNumber closure()

    private Token scanSymbol() {
        int firstIndex = this.currentIndex;

        for (int i=this.tokenStart; i < this.input.length(); i++) {
            char currentChar = this.input.charAt(i);
            // If it's a digit, or it's another valid character for numbers, then it's a valid number
            if (isIdentifiersChar(currentChar) || Character.isWhitespace(currentChar)) {
                break;
            } else {
                currentIndex = i;
            } // else closure
        } // for closure

        String detected_string = input.substring(tokenStart, currentIndex+1);
        TokenType detected_type = TokenType.decode(detected_string);
        while (detected_type == null && !detected_string.isEmpty()) {
            detected_string = detected_string.substring(0, detected_string.length()-1);
            currentIndex--;
            detected_type = TokenType.decode(detected_string);
        }

        // If it wasn't detected in the list of known symbols, return INVALID
        if (detected_type == null) {
            return new Token(
                    TokenType.INVALID,
                    detected_string,
                    firstIndex,
                    this.currentLine,
                    this.currentPositionInLine
            );
        }

        // if it was detected, return that
        return new Token(
                detected_type,
                detected_string,
                firstIndex,
                this.currentLine,
                this.currentPositionInLine
        );
    } // scanSymbol() closure

    // ------ Helper functions ------
    ///  Returns true if the passed character is a valid Identifier first character
    private boolean isIdentifiersChar(char c) {
        if (Character.isAlphabetic(c)) return true;
        if (Character.isDigit(c)) return true;
        return c == '_';
    }

    ///  Returns true if the passed character is a valid Number character
    private boolean isValidNumberCharacter(char c) {
        String valid_characters = "_.";
        return valid_characters.contains(""+c);
    }

    ///  Returns true if the passed character is a valid numerical base character
    private boolean isValidNumericalBaseCharacter(char c) {
        String valid_character = "xXbBoOdD";
        return valid_character.contains(""+c);
    }

    private boolean isValidDigitForBase(char digit, char base) {
        if (digit == '_') return true;
        digit = Character.toLowerCase(digit);
        return switch (base) {
            case 'o' -> (digit >= '0' && digit <= '7'); // Octal base
            case 'b' -> (digit == '0' || digit == '1'); // Binary base
            case 'x' -> (Character.isDigit(digit) || (digit >= 'a' && digit <= 'f')); // Hexadecimal
            default -> Character.isDigit(digit);
        };
    } // isValidDigitForBase closure()

    ///  Checks if the passed character is a newLine and if the next character in the input composes \r\n,
    ///  eventually skipping a char to not count it twice
    private void checkNewline(char c) {
        if (c == '\n') {
            this.currentLine++;
            this.currentPositionInLine = -1;
        }
        else if (c == '\r') {
            this.currentLine++;
            this.currentPositionInLine = -1;
            // Manage \r\n
            if (this.currentIndex+1 < this.input.length() && this.input.charAt(this.currentIndex+1) == '\n') {
                this.currentIndex++; // Skip \n
            }
        }
        /*else { return false; }
        return true;*/
    }
}
