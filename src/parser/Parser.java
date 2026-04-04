package parser;

import token.Token;
import java.util.List;

public class Parser {

    private final TokenCursor source;

    public Parser(List<Token> source) {
        this.source = new TokenCursor(source);
    }

}
