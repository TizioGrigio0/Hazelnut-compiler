package parser.parselets;

// PrefixParselet, I decided to call it SeedParselet so that it's easily distinguishable from InfixParselet (Infix looks similar to Prefix and could generate confusion)

import ast.expressions.Expression;

public interface SeedParselet {

    Expression parse(Parser parser, Token token);

}
