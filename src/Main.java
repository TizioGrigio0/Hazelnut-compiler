import Lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TestTokenize("Epstein_x = 5y + o");
        TestTokenize("1 -- 2");
        TestTokenize("x01+01+01-x10");
    }

    private static void TestTokenize(String s) {
        System.out.println("\""+s+"\"");
        List<Token> tokens = Token.tokenize(s);
        int tokens_length = tokens.toArray().length;
        for (int i=0; i < tokens_length; i++) {
            System.out.println(tokens.get(i));
        }
        System.out.println();
    }
}