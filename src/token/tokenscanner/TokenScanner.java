package token.tokenscanner;

import lexer.SourceCursor;
import token.Token;

public interface TokenScanner {

    Token scan(SourceCursor source);

    boolean canScan(char c);

}
