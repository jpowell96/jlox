package com.interpreter;

public class Token {
    final TokenType type;
    // A lexeme is the String representation of the token. ex "var", "int", ";"
    final String lexeme;
    // A literal refers to the interpreted value of the token. What the language will interpret it as when you run your code.
    final Object literal;
    final int line;

    // TODO: Extend to also include column where the literal starts
    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
}
