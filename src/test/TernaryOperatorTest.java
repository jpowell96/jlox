package test;

import com.interpreter.Expr;
import com.interpreter.Parser;
import com.interpreter.Token;
import com.interpreter.TokenType;

import java.util.List;

public class TernaryOperatorTest {

    public static void main(String[] args) {
            // 1 + 2 == 3 ? 5 : 7
        List<Token> tokens = List.of(
                new Token(TokenType.NUMBER, "7", 7.0, 1),
                new Token(TokenType.PLUS, "+", null, 1),
                new Token(TokenType.NUMBER, "6", 6.0, 1),
                new Token(TokenType.EQUAL_EQUAL, "==", null, 1),
                new Token(TokenType.NUMBER, "13", 13.0, 1),
                new Token(TokenType.TERNARY, "?", null, 1),
                new Token(TokenType.NUMBER, "5", 5.0, 1),
                new Token(TokenType.COLON, ":", null, 1),
                new Token(TokenType.NUMBER, "999", 999.0, 1)
                );


        Parser parser = new Parser(tokens);
        Expr output = parser.parse();

        // (7 + 6 == 13) ? 9 : 10

    }
}
