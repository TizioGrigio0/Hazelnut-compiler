import Lexer.Lexer;
import Lexer.Token;

import java.util.List;

public class Main {

    static Lexer lexer = new Lexer();

    public static void main(String[] args) {

        TestTokenize("f = 1517_1_24");
        TestTokenize("_f_a_1=123_123_123_");
        TestTokenize("int a = 67;\nfunc a (int a, int b){ return a+b;}");
        TestTokenize("1x = 1x00 1x0x 43x0 x00 x00;");
        TestTokenize("1.0 0.1 .0 111.0111 0. 4214.0.0 14.14.2.4");
        TestTokenize("0x1 0x2 0xx2 0xF 0b2 0b101 0d09 0o70");
        TestTokenize("0xG 1.2.3 0b102 12_ 42. .5 0x12.34");
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