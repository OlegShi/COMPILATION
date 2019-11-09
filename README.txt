# COMPILATION
Java implementation of a compiler to an invented object oriented language called Poseidon.
implemented:
- lexical scanner based on the open source toolJFlex.
- CUP (LALR parser generator) based parser on top of the JFlex scanner
- semantic analyzer
- code generation phase for Poseidon programs. the chosen destination language is MIPS assembly.
