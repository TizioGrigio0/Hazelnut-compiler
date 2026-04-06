package token.tokenscanner;

import errorreporter.CompilerError;
import lexer.Lexer;
import lexer.LexerErrorMessage;
import lexer.SourceCursor;
import token.Token;
import token.TokenType;

import static token.tokenscanner.ScannerUtils.isIdentifiersChar;

public class SymbolScanner extends TokenScanner {

    public SymbolScanner(Lexer lexer) {
        super(lexer);
    }

    public boolean canScan(char c) {
        return true;
    }

    public Token scan(SourceCursor source) {

        while (true) {
            char currentChar = source.peek();
            // If it's a digit, or it's another valid character for numbers, then it's a valid number
            if (isIdentifiersChar(currentChar) || Character.isWhitespace(currentChar) || currentChar == '\0') {
                break;
            }
            source.advance();
        } // while true closure

        // Maximal munch for symbols
        String detected_string = source.extractTokenString();
        String max_symbol = detected_string;
        int fallbacks = 0;
        TokenType detected_type = TokenType.decode(detected_string);
        while (detected_type == null && !detected_string.isEmpty()) { // While the string isn't empty and the symbol is still unrecognized
            detected_string = detected_string.substring(0, detected_string.length()-1); // Removes the last character of the string
            source.fallback();
            fallbacks++;
            detected_type = TokenType.decode(detected_string);
        }

        if (detected_type == null) {
            detected_type = TokenType.INVALID; // If no matching symbol was found, then it's invalid
            for(int i=0; i<fallbacks; i++) {
                source.advance(); // skip this symbol
            }
            lexer.generateError(CompilerError.ErrorType.ERROR, LexerErrorMessage.INVALID_SYMBOL, max_symbol);
        }

        return source.generateTokenHere(detected_type);
    }
}
