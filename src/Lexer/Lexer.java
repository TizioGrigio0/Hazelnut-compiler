package Lexer;

import java.util.ArrayList;
import java.util.List;

// Singleton pattern
public class Lexer {

    // ----- Attributes -----
    private static final Lexer INSTANCE = new Lexer();

    String input = "";
    private int tokenStart = 0; // First index of the token in the string
    private int currentIndex = 0;

    private int currentPositionInLine = -1;
    private int currentLine = 0;

    // ----- Constructors -----
    private Lexer() {}

    // ----- Methods -----
    public static Lexer getInstance() { return INSTANCE; }

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

        for (int i=this.tokenStart; i < this.input.length(); i++) {
            char currentChar = this.input.charAt(i);
            // If it's a digit, or it's another valid character for numbers, then it's a valid number
            if (Character.isDigit(currentChar) || isValidNumberCharacter(currentChar)) { // TODO fix valid number character 1x
                currentIndex = i;
            } else {
                break;
            } // else closure
        } // for closure

        return new Token(
                TokenType.NUMBER,
                input.substring(tokenStart, currentIndex+1),
                firstIndex,
                this.currentLine,
                this.currentPositionInLine
        );
    } // scanNumber closure()

    private Token scanSymbol() {    // TODO Fix ){
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
        String valid_characters = "_.eExb";
        return valid_characters.contains(""+c);
    }

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
