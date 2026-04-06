package ast;

import token.Token;

class AstVariable extends AstExpression {

    private final Token token;

    public AstVariable(Token token) {
        this.token = token;
    }

}
