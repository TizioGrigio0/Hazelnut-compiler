package token.tokenscanner;

import errorreporter.CompilerError;
import lexer.Lexer;
import lexer.LexerErrorMessage;
import lexer.SourceCursor;
import token.Token;
import token.TokenType;

import static token.tokenscanner.ScannerUtils.isIdentifiersChar;

public class NumberScanner extends TokenScanner {

    public NumberScanner(Lexer lexer) {
        super(lexer);
    }

    public boolean canScan(char c) {
        return (c >= '0' && c <= '9');
    }

    public Token scan(SourceCursor source) {

        TokenType detected_type = TokenType.NUMBER;
        boolean decimalPoint = false; // Did we find a decimal dot up until now?

        char base = 'd';

        { // Numerical base scope
            char first_char = source.peek();

            // Check the numerical base
            if (first_char == '0' && source.getRemainingLength() >= 2) { // If the first character is 0 and the length of the input is at least 3
                source.advance();
                char base_identifier = source.peek(); // Peek at i+1
                if (isValidNumericalBaseCharacter(base_identifier)) {
                    base = base_identifier;
                    source.advance(); // Jump over the prefix i+=1 => i+2;
                    // If there is no other input after '0x' it's invalid
                    if (source.getRemainingLength() <= 1 || Character.isWhitespace(source.peek())) {
                        detected_type = TokenType.INVALID;
                    }
                }
            }

            base = Character.toLowerCase(base);

        } // Numerical base scope closure

        char currentChar = '\0';
        boolean wasLastDecimalPoint = false;

        while ( true ) {

            currentChar = source.peek();

            // If it's a valid character for our number
            if (isValidDigitForBase(currentChar, base) || currentChar == '.') {
                // Check for double decimal point (and set as invalid accordingly)
                if (currentChar == '.') {
                    if (base != 'd') detected_type = TokenType.INVALID; // Decimal point is only allowed in decimal base
                    if (decimalPoint) detected_type = TokenType.INVALID;
                    decimalPoint = true;
                    wasLastDecimalPoint = true;
                } else wasLastDecimalPoint = false;
            } else if (isIdentifiersChar(currentChar)) {
                detected_type = TokenType.INVALID;
            } else {
                break;
            } // else closure

            source.advance();
        } // while true closure

        if (wasLastDecimalPoint || currentChar == '_') { // If the last character is a . or a _, then mark it as invalid
            // We are marking _ last character as invalid because it probably means that the user missed it, it could be a typo
            detected_type = TokenType.INVALID;
        }

        if (detected_type == TokenType.INVALID) {
            lexer.generateError(CompilerError.ErrorType.ERROR, LexerErrorMessage.MALFORMED_NUMBER, source.extractTokenString());
        }

        return source.generateTokenHere(detected_type);
    } // scan() closure

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

}
