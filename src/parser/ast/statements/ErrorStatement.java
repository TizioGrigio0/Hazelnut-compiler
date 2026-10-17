package parser.ast.statements;

import parser.ast.expressions.Expression;

public class ErrorStatement extends Statement {

    private Expression expression;

    public ErrorStatement(Expression expression) {
        this.expression = expression;
    }

}
