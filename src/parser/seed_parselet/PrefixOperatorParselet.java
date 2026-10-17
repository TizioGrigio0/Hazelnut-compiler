package parser.seed_parselet;

import parser.Parser;
import parser.ast.expressions.Expression;
import parser.ast.expressions.PrefixExpression;
import token.Precedence;
import token.Token;

public class PrefixOperatorParselet implements SeedParselet {

    @Override
    public Expression parse(Parser parser, Token token) {
        Expression subject = parser.parseExpression(Precedence.PREFIX);
        return new PrefixExpression(token, subject);
    }

}
