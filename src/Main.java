import ast.AstNode;
import errorreporter.ErrorReporter;
import lexer.Lexer;
import parser.Parser;
import token.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main{

    public static void main(String[] args) {

        String inputCode;
        try {
            inputCode = Files.readString(Path.of("examples/variables.hzl"));
        } catch(IOException e) {
            // ADD ANSI ESCAPE COLORS
            System.out.println("Something went wrong while trying to read the input file");
            return;
        }


        ErrorReporter reporter = new ErrorReporter();
            //TestTokenize(input);

        // Generate the tokens from an input code
        Lexer lexer = new Lexer(inputCode, reporter);
        List<Token> tokens = lexer.tokenize();

        // Parse the tokens and create the AST
        Parser parser = new Parser(tokens, reporter);
        AstNode ast = parser.parse();
    }

}