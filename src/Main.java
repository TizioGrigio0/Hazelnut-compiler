import Lexer.Token;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        TestTokenize("x = 5 + 3");
        TestTokenize("x=5+3");
        TestTokenize("x1 = 532 + 425");
        TestTokenize("1x = 525x - 513x");
        TestTokenize("x1=532+425");

    }

    static void TestTokenize(String s) {
        System.out.println("\""+s+"\"");
        List<Token> tokens = Token.tokenize(s);
        int tokens_length = tokens.toArray().length;
        for (int i=0; i < tokens_length; i++) {
            System.out.println(tokens.get(i));
        }
        System.out.println();
    }
}