package parser;

import ast.*;
import errorreporter.ErrorReporter;
import token.Token;
import java.util.List;

public class Parser {

    private final TokenCursor source;
    private final ErrorReporter reporter;

    public Parser(List<Token> source, ErrorReporter reporter) {
        this.source = new TokenCursor(source);
        this.reporter = reporter;
    }

    public AstExpression parse() {
        return null;
    }

}
