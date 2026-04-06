package ast;

class AstGrouping extends AstExpression {

    private final AstExpression expression;

    public AstGrouping(AstExpression expression) {
        this.expression = expression;
    }

}
