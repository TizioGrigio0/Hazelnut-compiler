import Lexer.Lexer;
import Lexer.Token;

import java.util.List;

public class Main {

    static Lexer lexer = Lexer.getInstance();

    public static void main(String[] args) {

        TestTokenize("f = 1517_1_24");
        TestTokenize("_f_a_1=123_123_123_");
        TestTokenize("int a = 67;\nfunc a (int a, int b){ return a+b;}");
    }

    private static void TestTokenize(String s) {
        System.out.println("\""+s+"\"");
        List<Token> tokens = lexer.tokenize(s);
        int tokens_length = tokens.toArray().length;
        for (int i=0; i < tokens_length; i++) {
            System.out.println(tokens.get(i));
        }
        System.out.println();
    }
}