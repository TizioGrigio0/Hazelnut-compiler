import errorreporter.ErrorReporter;
import lexer.Lexer;
import parser.Parser;
import parser.ast.expressions.Expression;
import parser.ast.statements.Statement;
import token.Precedence;
import token.Token;
import util.AnsiTextHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main{

    public static void main(String[] args) {

        String inputFile = "examples/4_parser_errors.hzl";
        String inputCode;
        try {
            inputCode = Files.readString(Path.of(inputFile));
        } catch(IOException e) {
            AnsiTextHandler.setRed();
            System.out.println("Something went wrong while trying to read the input file \""+inputFile+"\"");
            AnsiTextHandler.resetEverything();
            return;
        }

        ErrorReporter reporter = new ErrorReporter();

        // Generate the tokens from an input code
        Lexer lexer = new Lexer(inputCode, reporter);
        List<Token> tokens = lexer.tokenize();

        // Check if the lexer gave any problems, and eventually return
        if (reporter.checkFailureAndPrintErrors()) return;

        // Parse the tokens and create the AST
        Parser parser = new Parser(tokens, reporter);
        List<Statement> ast = parser.parse();

        System.out.println(ast);

        if (reporter.checkFailureAndPrintErrors()) return;
    }

}