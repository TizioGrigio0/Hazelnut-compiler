import lexer.Lexer;
import token.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main{

    public static void main(String[] args) throws IOException {

        boolean[] tests = new boolean[7];
        //tests[0] = true;
        tests[1] = true;

        // Complex tests
        if (tests[0]) {
            TestTokenize("int _mask=0b1100_1010;\n" +
                    "if(_mask!=0x_FF_&&_mask>=0o77){\n" +
                    "    _mask^=0b1;\n" +
                    "    return(_mask<<=2)==0;\n" +
                    "}");
            TestTokenize("string s=\"## # literal\";\n" +
                    "char c='\\n'; ### multi\n" +
                    "line block ### b=c=='\\n';\n" +
                    "while(b||false){\n" +
                    "    s+=c; ##### comment ##### }");
            TestTokenize("float f=100_._00; #### multi #### f/=2.5;\n" +
                    "bool _check=(f<=.99)&&(0x_A_B!=0b_1_0_);\n" +
                    "return _check ? 0o_7_6_ : 0; # single to EOF");
        }

        if (tests[1])
            TestTokenize( Files.readString(Path.of("examples/variables.hzl")) );
    }

    private static void TestTokenize(String s) {
        Lexer lexer = new Lexer(s);
        System.out.print("\"");
        System.out.print("\u001B[34m"); // ANSI blue
        System.out.print(s);
        System.out.print("\u001B[0m");
        System.out.println("\"");
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            if (token.isInvalid()) System.out.print("\u001B[31m"); // ANSI red
            System.out.println(token);
            if (token.isInvalid()) System.out.print("\u001B[0m"); // ANSI reset
        }
        System.out.println();
    }
}