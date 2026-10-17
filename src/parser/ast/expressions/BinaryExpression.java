package parser.ast.expressions;

import token.Token;

public record BinaryExpression(Expression left, Token op, Expression right) implements Expression {

    @Override
    public boolean isError() {
        return left.isError() || right.isError();
    }

}
