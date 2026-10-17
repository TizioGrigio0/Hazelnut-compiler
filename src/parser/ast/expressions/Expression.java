package parser.ast.expressions;

import parser.ast.ASTNode;

public interface Expression extends ASTNode {

    // Logic for anything that returns a value
    // 5 + 2
    // x + 1
    // foo()

    default boolean isError() {
        return false;
    }

}
