package parser.ast.visitors;

public class ConstantFolder {

    /*
        Fuses constant calculations to reduce runtime instructions
            "a + 2 + 3" is effectively the same as "a + 5"
        Also should remove constant operations like the following:
            - "x + 0"
            - "x - 0"
            - "x * 1"
            - "x / 1"
            - "x ^ 1"
            - "1 ^ x"
    */

}
