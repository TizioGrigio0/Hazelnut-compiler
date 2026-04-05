import ast.AstNode;
import errorreporter.ErrorReporter;
import lexer.Lexer;
import parser.Parser;
import token.Token;
import util.AnsiTextHandler;

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
            AnsiTextHandler.setRed();
            System.out.println("Something went wrong while trying to read the input file");
            AnsiTextHandler.resetEverything();
            return;
        }

        ErrorReporter reporter = new ErrorReporter();

        // Generate the tokens from an input code
        Lexer lexer = new Lexer(inputCode, reporter);
        List<Token> tokens = lexer.tokenize();

        // Check if the lexer gave any problems, and eventually return
        if (reporter.checkFailureAndPrintErrors()) {
            return;
        }

        // Parse the tokens and create the AST
        Parser parser = new Parser(tokens, reporter);
        AstNode ast = parser.parse();
    }

}