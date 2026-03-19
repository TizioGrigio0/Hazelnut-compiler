import Lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TestTokenize("f = 1517_1_24");
        TestTokenize("_f_a_1=123_123_123_");
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