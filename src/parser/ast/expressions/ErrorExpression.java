package parser.ast.expressions;

import token.Token;

public record ErrorExpression(Token content) implements Expression {

    @Override
    public boolean isError() {
        return true;
    }
}
