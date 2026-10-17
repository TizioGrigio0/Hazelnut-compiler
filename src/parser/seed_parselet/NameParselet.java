package parser.seed_parselet;

import parser.Parser;
import parser.ast.expressions.Expression;
import parser.ast.expressions.NameExpression;
import token.Token;

public class NameParselet implements SeedParselet {

    @Override
    public Expression parse(Parser parser, Token token) {
        return new NameExpression(token.getValue());
    }

}
