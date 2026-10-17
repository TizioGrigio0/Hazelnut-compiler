package parser.ast.statements;

import parser.ast.expressions.Expression;

public class ExpressionStatement extends Statement {

    private final Expression expression;

    public ExpressionStatement(Expression expression) {
        this.expression = expression;
    }

}
