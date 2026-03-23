## Numbers
### Numerical bases
The following numerical bases are allowed when using the corresponding prefix (casing doesn't matter):
- Hexadecimal: `0x`
- Octal: `0o` 
- Binary: `0b`
- Decimal: by default or with `0d` (decimal numbers `eg. 1.1` are only allowed here)

### Allow typing at the end of the number TODO
It's possible to define the type of the number (int, float, double) by simply adding a letter at the end of the number matching the type.

`<number>i` integer, `<number>f` float, `<number>d` double, `<number>s` short.

### "_" is an ignored character for numbers
The character _"\_"_ is an ignored character in numbers, for example, `1_000_000` = `1000000`
This doesn't apply for identifiers given throughout the code, for example, `x` != `x_` != `_x` != `_x_`