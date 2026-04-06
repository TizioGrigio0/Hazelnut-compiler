package errorreporter;

import java.util.ArrayList;
import java.util.List;

public class ErrorReporter {

    private final List<CompilerError> errors = new ArrayList<>();

    public ErrorReporter() {    }

    // Adds an error to the list
    public void pushError(CompilerError error) {
        this.errors.add(error);
    }

    // Prints all the warning and errors in order, if the compilation needs to stop, returns true
    public boolean checkFailureAndPrintErrors() {

        boolean fatal = false;

        for (CompilerError error : this.errors) {
            if (error.getErrorType() == CompilerError.ErrorType.ERROR) fatal = true;
            System.out.println(error.formatError());
        }

        return fatal;
    }
}
