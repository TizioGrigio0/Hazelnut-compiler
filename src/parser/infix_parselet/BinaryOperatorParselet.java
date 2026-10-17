package parser.infix_parselet;

import parser.Parser;
import parser.ast.expressions.BinaryExpression;
import parser.ast.expressions.Expression;
import token.Precedence;
import token.Token;

public record BinaryOperatorParselet(Precedence precedence) implements InfixParselet {

    @Override
    public Expression parse(Parser parser, Expression left, Token token) {
        Expression right = parser.parseExpression(this.precedence);
        return new BinaryExpression(left, token, right);
    }

}
