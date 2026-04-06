package ast;

import token.Token;

class AstUnary extends AstExpression{

    private final Token operator;
    private final AstExpression right;

    public AstUnary(Token operator, AstExpression right) {
        this.operator = operator;
        this.right = right;
    }

}
