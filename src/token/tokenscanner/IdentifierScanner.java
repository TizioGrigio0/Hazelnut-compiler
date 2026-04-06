package token.tokenscanner;

import lexer.Lexer;
import lexer.SourceCursor;
import token.Token;
import token.TokenType;

import static token.tokenscanner.ScannerUtils.isIdentifiersChar;

public class IdentifierScanner extends TokenScanner {

    private static final boolean[] IS_IDENTIFIER_START = new boolean[256];
    static {
        for (int i=0; i<256; i++) {
            IS_IDENTIFIER_START[i] = (i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') || (i == '_');
        }
    }

    public IdentifierScanner(Lexer lexer) {
        super(lexer);
    }

    public boolean canScan(char c) {
        if (c < 256)  return IS_IDENTIFIER_START[c];
        return Character.isLetter(c);
    }

    public Token scan(SourceCursor source) {

        while (true) {
            char currentChar = source.peek();
            // If the character is alphanumeric, then it's a valid identifier
            if (!isIdentifiersChar(currentChar)) { // If it's not a valid identifier character
                break;
            } // if closure
            source.advance();
        } // while true closure

        TokenType detected_type = TokenType.decode(source.extractTokenString());
        if (detected_type == null) {
            detected_type = TokenType.IDENTIFIER;
        }

        return source.generateTokenHere(detected_type);

    }

}
