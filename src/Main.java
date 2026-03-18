import Lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Token> tokens = Token.tokenize("public x7896x 1234 4 class Arma extends Item5 124 351379x");
        int tokens_length = tokens.toArray().length;

        for (int i=0; i < tokens_length; i++) {
            System.out.println(tokens.get(i));
        }


    }
}