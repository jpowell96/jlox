package com.interpreter;

import java.util.List;

public abstract class Expr {
 interface Visitor<R> { 
 R visitBinaryExpr(Binary expr);
 R visitGroupingExpr(Grouping expr);
 R visitLiteralExpr(Literal expr);
 R visitUnaryExpr(Unary expr);
 R visitBranchExpr(Branch expr);
 }
 public static class Binary extends Expr {
 Binary(Expr left, Token operator, Expr right) {
 this.left = left;
 this.operator = operator;
 this.right = right;
}

 @Override
 <R> R accept(Visitor<R> visitor) {
 return visitor.visitBinaryExpr(this);
 }

 final Expr left;
 final Token operator;
 final Expr right;
 }
 public static class Grouping extends Expr {
 Grouping(Expr expression) {
 this.expression = expression;
}

 @Override
 <R> R accept(Visitor<R> visitor) {
 return visitor.visitGroupingExpr(this);
 }

 final Expr expression;
 }
 public static class Literal extends Expr {
 Literal(Object value) {
 this.value = value;
}

 @Override
 <R> R accept(Visitor<R> visitor) {
 return visitor.visitLiteralExpr(this);
 }

 final Object value;
 }
 public static class Unary extends Expr {
 Unary(Token operator, Expr right) {
 this.operator = operator;
 this.right = right;
}

 @Override
 <R> R accept(Visitor<R> visitor) {
 return visitor.visitUnaryExpr(this);
 }

 final Token operator;
 final Expr right;
 }
 public static class Branch extends Expr {
 Branch(Expr conditional, Expr true_branch, Expr false_branch) {
 this.conditional = conditional;
 this.true_branch = true_branch;
 this.false_branch = false_branch;
}

 @Override
 <R> R accept(Visitor<R> visitor) {
 return visitor.visitBranchExpr(this);
 }

 final Expr conditional;
 final Expr true_branch;
 final Expr false_branch;
 }

 abstract <R> R accept(Visitor<R> visitor);
}
