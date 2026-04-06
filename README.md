MIT License © TizioGrigio0

<div style="text-align: center;"><h1> 🌰 Hazelnut compiler 🌰 </h1></div>

Compiler for a custom-made language that I made as a project in the free time.

## Progress status
- Lexer ✔️ (Fully working)
- Parser ⏳ (WIP)

## Error reporting
The compiler will print a detailed list of all the compile-time errors and warnings, eventually stopping the compilation if they are fatal
<div style="display: flex; gap: 16px;">
  <pre style="
    flex: 1;
    overflow: auto;
    white-space: pre;
    border: 1px solid #555;
    padding: 12px;
    max-height: 400px;
  ">
var a = 0b2010;         # Gives the error "Malformed number"
var b = 1.0.2;          # Gives the error "Malformed number"
var c = a$$$b;          # Gives the error "Invalid symbol"
var d = "\s";           # Gives the error "Invalid escape sequence"
var e = 'aaa';          # Gives the error "Malformed char"
var f = a;              ## This comment will give the warning "unterminated block comment"</pre>

  <pre style="
    flex: 1;
    overflow: auto;
    white-space: pre;
    border: 1px solid #555;
    padding: 12px;
    max-height: 400px;
  ">ERROR: Malformed number '0b2010'; at line: 1, column: 8, during LEXER phase:
 >>> var a = 0b2010;         # Gives the error "Malformed number" <<< 
             ^
ERROR: Malformed number '1.0.2'; at line: 2, column: 8, during LEXER phase:
 >>> var b = 1.0.2;          # Gives the error "Malformed number" <<< 
             ^
ERROR: Invalid symbol: '$$$'; at line: 3, column: 9, during LEXER phase:
 >>> var c = a$$$b;          # Gives the error "Invalid symbol" <<< 
              ^
ERROR: Invalid escape sequence: '\s'; at line: 4, column: 9, during LEXER phase:
 >>> var d = "\s";           # Gives the error "Invalid escape sequence" <<< 
              ^
ERROR: Malformed char 'aaa'; at line: 5, column: 9, during LEXER phase:
 >>> var e = 'aaa';          # Gives the error "Malformed char" <<< 
              ^
WARNING: Unterminated block comment; at line: 6, column: 24, during LEXER phase:
 >>> var f = a;              ## This comment will give the warning "unterminated block comment" <<< 
                             ^</pre>

</div>
