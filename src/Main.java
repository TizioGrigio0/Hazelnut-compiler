import lexer.Lexer;
import token.Token;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        boolean[] tests = new boolean[7];
        //tests[0] = true;
        //tests[1] = true;
        //tests[2] = true;
        //tests[3] = true;
        //tests[4] = true;
        //tests[5] = true;
        tests[6] = true;

        // Numbers and identifiers
        if (tests[0]) {
            TestTokenize("fa = 1517_1_24");
            TestTokenize("_f_a_1=123_123_123_");
            TestTokenize("int a = 67;\nfunc a (int a, int b){ return a+b;}");
            TestTokenize("1x = 1x00 1x0x 43x0 x00 x00;");
            TestTokenize("1.0 0.1 .0 111.0111 0. 4214.0.0 14.14.2.4");
            TestTokenize("0x1 0x2 0xx2 0xF 0b2 0b101 0d09 0o70");
            TestTokenize("0xG 1.2.3 0b102 12_ 42. .5 0x12.34");
        }
        // Code
        if (tests[1]) {
            TestTokenize("int main() { return (0x1A_2B ^ 0b1010) + 42.0.1; }");
            TestTokenize("float_val = 15. + .99 - 0o77 * 0d123_456;");
            TestTokenize("if (a == 0xG || b!=0b01) { _sys_call(123_); }");
            TestTokenize("string literal_test = \"should be ignored by numbers 1.2.3\"; x = 0x12.34;");
            TestTokenize("while(1x00<0xFFFF){arr[0b11]=.5; arr[1.]=0.;}");
            TestTokenize("func complex_math(float x, int y) { return x/y + 0x_F - 1..2; }");
            TestTokenize("0b1011_0101_1111_0000 0x__1 123_._456 _123 = .001;");
            TestTokenize("int   spaced_out\n\t= \n 0x10 ; \n \r return\n 0b10;\n");
        }

        // Booleans
        if (tests[2]) {
            TestTokenize("bool check = true && !false;");
            TestTokenize("if(isValid==true){ return false; }");
            TestTokenize("trueValue = 1; false_pos = 0;");
            TestTokenize("bool a=true;bool b=false;bool c=truefalse;");
            TestTokenize("return (true || false) == !true;");
            TestTokenize("identifier_with_true = true;");
            TestTokenize("while(true){ if(!!false) break; }");
            TestTokenize("bool x = true; // true in a string: \"true\"");
        }

        // Strings
        if (tests[3]) {
            TestTokenize("\"Standard string\"");
            TestTokenize("\"String with \\\"quotes\\\" inside\"");
            TestTokenize("\"Backslash path: C:\\\\Windows\\\\System32\"");
            TestTokenize("\"Mixed: \\\"Quotes\\\" and \\\\Backslashes\\\\ \"");
            TestTokenize("\"Unterminated string"); // (should be INVALID)
            TestTokenize("\"Escaped backslash at end \\\\\"");
            TestTokenize("\"Emoji or non-ascii: \u1234\""); // (should be INVALID)
            TestTokenize("\"\""); // (empty string)
            TestTokenize("\"abc\""); // Should be 'abc', might result in 'ac'
            TestTokenize("\"\\\\n\""); // Escaped backslash followed by 'n'
            TestTokenize("\"path\\\\to\\\\file\""); // test 3
            TestTokenize("\"\\\"quote\\\"\""); // test 4
            TestTokenize("\"multi word string test\""); // test 5
            TestTokenize("\"tab\\ttest\""); // test 6
            TestTokenize("\"newline\\ntest\""); // test 7
            TestTokenize("\"invalid\\escape\""); // test 8
            TestTokenize("string path = \"C:\\\\Users\\\\Admin\\\\Desktop\\\\\\\"file\\\".txt\";"); // test 1
            TestTokenize("print(\"Line 1\\nLine 2\\tTabbed\\rReturn\");"); // test 2
            TestTokenize("empty = \"\"; only_escapes = \"\\\\\\\\\\\"\\\\\";"); // test 3
            TestTokenize("func(\"string with a 0x123 hex and true boolean inside\");"); // test 4
            TestTokenize("\"Double backslash at end of string\\\\\""); // test 5
            TestTokenize("bad_string = \"forgot to close;"); // test 6
            TestTokenize("\"Mixed ASCII and invalid: \u00FF \u0101\";"); // test 7
            TestTokenize("\"Value with escaped n: \\n and literal n: n\";"); // test 8
        }

        // Characters
        if (tests[4]) {
            TestTokenize("'a';"); // test 1: Standard char
            TestTokenize("'\\n';"); // test 2: Escaped newline
            TestTokenize("'\\t';"); // test 3: Escaped tab
            TestTokenize("'\\\\';"); // test 4: Escaped backslash
            TestTokenize("'\\'';"); // test 5: Escaped single quote
            TestTokenize("'';"); // test 6: Invalid (empty)
            TestTokenize("'abc';"); // test 7: Invalid (too long)
            TestTokenize("'a;"); // test 8: Invalid (unterminated)
            TestTokenize("'\\n\\t';"); // test 9: Invalid (two escapes in one char)
            TestTokenize("'\u0101';"); // test 10: Invalid (non-ascii)
            TestTokenize("'\"';"); // test 11: Single quote containing double quote
            TestTokenize("' ';"); // test 12: Space character
            TestTokenize("'0';"); // test 13: Numeric digit as char
            TestTokenize("'\\r';"); // test 14: Carriage return
        }

        // Comments
        if (tests[5]) {
            TestTokenize("int x = 10; # This is a single line comment"); // test 1: Basic single line
            TestTokenize("int y = 20; ## This is a \n multi-line \n comment ##"); // test 2: Basic multi-line
            TestTokenize("val = # comment \n 50;"); // test 3: Comment splitting a statement
            TestTokenize("val = ## multi \n line ## 100;"); // test 4: Multi-line splitting a statement
            TestTokenize("string s = \"# this is not a comment ## neither is this\";"); // test 5: Symbols inside strings (The Boss Fight)
            TestTokenize("char c = '#'; char d = '##';"); // test 6: Symbols inside chars (The '##' should make the char invalid/long)
            TestTokenize("## # ## int a = 5;"); // test 7: Single-line start symbol nested inside a multi-line
            TestTokenize("if(true)##comment##{x=1;}#final"); // test 8: Extremely tight packing with no whitespace
            TestTokenize("result = 100 ##unterminated multi-line comment"); // test 9: Missing the closing ## (Should handle EOF gracefully)
            TestTokenize("#### # double multi-line start with a single line trailing?"); // test 10: The "Forest of Hashtags"
            TestTokenize("x = 5; ####### many hashes"); // test 11: Repeated start symbols
            TestTokenize("bool b = true; ## comment with \"quotes\" inside ##"); // test 12: Quotes inside comments (should NOT start a string)
            TestTokenize("## comment ## # single \n ## another ##"); // test 13: Rapid switching between comment types
            TestTokenize("a = ######### bla bla # comment ### 5;");
        }

        // Complex tests
        if (tests[6]) {
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