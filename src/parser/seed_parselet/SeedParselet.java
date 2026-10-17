package parser.seed_parselet;

// PrefixParselet, I decided to call it SeedParselet so that it's easily distinguishable from InfixParselet (Infix looks similar to Prefix and was very confusing for me)

import parser.ast.expressions.Expression;
import parser.Parser;
import token.Token;

// Start an expression
public interface SeedParselet {

    Expression parse(Parser parser, Token token);

}
