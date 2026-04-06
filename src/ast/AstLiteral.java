package ast;

class AstLiteral extends AstExpression {

    private final Object value;

    public AstLiteral(Object value) {
        this.value = value;
    }

}
