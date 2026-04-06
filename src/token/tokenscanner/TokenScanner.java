package token.tokenscanner;

import lexer.Lexer;
import lexer.SourceCursor;
import token.Token;

public abstract class TokenScanner {

    final Lexer lexer;

    public TokenScanner(Lexer lexer) {
        this.lexer = lexer;
    }

    public abstract Token scan(SourceCursor source);
    public abstract boolean canScan(char c);

}
