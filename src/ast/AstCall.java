package ast;

import java.util.ArrayList;
import java.util.Arrays;

class AstCall extends AstExpression {

    private final AstExpression callee;
    private final ArrayList<AstExpression> arguments;

    public AstCall(AstExpression callee, AstExpression... arguments) {
        this.callee = callee;
        this.arguments = new ArrayList<>();
        this.arguments.addAll(Arrays.asList(arguments));
    }

}
