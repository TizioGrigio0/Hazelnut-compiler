package ast;

import token.Token;

class AstBinary extends AstExpression {

    private final AstExpression left;
    private final Token operator;
    private final AstExpression right;

    public AstBinary(AstExpression left, Token operator, AstExpression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

}
