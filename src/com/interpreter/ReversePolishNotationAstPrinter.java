package com.interpreter;

public class ReversePolishNotationAstPrinter implements Expr.Visitor<String> {

    public String print(Expr expression) {
        return expression.accept(this);
    }
    /**
     * Prints out the AST using reverse polish notifcation for math operations
     *
     * (1 + 2) * (4 - 3)
     *
     * As an expression
     *
     * Binary Expr (
     * Grouping ( Binary Expr (Literal 1) (Operator *) (Literal 2))
     *
     * Operator ( * )
     * Grouping ( Binary Expr (Literal 4) (Operator -) (Literal 3))
     *
     * Should print out as:
     * 1 2 + 4 2 - *
     *
     * */
    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        // Print left expr
        // Print right expr
        // Print operator
        return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        // Unwrap the grouping by just calling print on the expression
        return " " + expr.expression.accept(this);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) {
            return "nil";
        } else {
            return expr.value.toString();
        }
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        // Pretend
        return expr.right.accept(this) + " " + expr.operator.lexeme;
    }

    public static void main(String[] args) {
        // (1 + 2)
        Expr onePlusTwo = new Expr.Grouping(new Expr.Binary(new Expr.Literal(1), new Token(TokenType.PLUS, "+", null, 1), new Expr.Literal(2)));

        // (4 - 3)
        Expr fourMinusThree = new Expr.Grouping(new Expr.Binary(new Expr.Literal(4), new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(3)));

        // (1 + 2) * ( 4 - 3)
        Expr multiplyOperation = new Expr.Binary(onePlusTwo, new Token(TokenType.STAR, "*", null, 1), fourMinusThree);

        System.out.println(new ReversePolishNotationAstPrinter().print(multiplyOperation));
    }
}
