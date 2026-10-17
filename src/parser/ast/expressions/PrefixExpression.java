package parser.ast.expressions;

import token.Token;

public record PrefixExpression(Token op, Expression subject) implements Expression {

    @Override
    public boolean isError() {
        return subject.isError();
    }
}
