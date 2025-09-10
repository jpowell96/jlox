package tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    /**
     *
     * A script to generate the Expr.java class and subclasses to express Lox as an Abstract Syntax Tree
     *
     * Refer to page 65 - 70 for more information.
     *
     * To run from the current diretory and generate to the main package, run:  java GenerateAst.java "../com/interpreter"
     * TODO: Create some toolchain for generating before compiling
     */

    public static void main (String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];
        defineAST(outputDir, "Expr", Arrays.asList(
                // Each entry in the array defines the class name and it's parameters. We have a class for each part of our
                // Lox Grammar on Page 65
                // <Class Name> : <Param 1 Type> <Param 1 Name>, <Param 2 Type> <Param 2 Name>, ... ,

                "Binary : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal : Object value",
                "Unary : Token operator, Expr right",
                "Branch : Expr conditional, Expr true_branch, Expr false_branch"
        ));
    }

    private static void defineAST(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        // 1. Generate the abstract Expr class that other parts of our syntax tree inherit from
        writer.println("package com.interpreter;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        // 1a. Generate Visitors.Visitor pattern allows us to easily modify behavior in AST, without needed to update
        // each individual class. Refer to pages 69 - 72 for more explanation
        defineVisitor(writer, baseName, types);

        // 2. Generate classes for each of our AST classes
        for (String type : types) {
            // Get the class name from a string of this shape:  <Class Name> : <Param 1 Type> <Param 1 Name>, <Param 2 Type> <Param 2 Name>,
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();

            defineType(writer, baseName, className, fields);
        }

        // 2a. Define the base accept method
        writer.println();
        writer.println(" abstract <R> R accept(Visitor<R> visitor);");
        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println(" public static class " + className + " extends " + baseName + " {");

        // Constructor with the field names
        writer.println(" " + className + "(" + fieldList + ") {");
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println(" this." + name + " = " + name + ";");
        }
        // Close the constructor
        writer.println("}");

        // Visitor Pattern Override
        writer.println();
        writer.println(" @Override");
        writer.println(" <R> R accept(Visitor<R> visitor) {");
        writer.println(" return visitor.visit" + className + baseName + "(this);");
        writer.println(" }");

        // Add the instance variables
        writer.println();
        for (String field : fields) {
            writer.println(" final " + field + ";");
        }

        writer.println(" }");
    }

    /**
    * Q: Why and how do we use the Visitor pattern?
    * A:
     *
     * This method generates the visitor interface that each expression subclass implements
    * */
    private static void defineVisitor(PrintWriter writer, String baseName, List<String> types) {
        writer.println(" interface Visitor<R> { ");
        for (String type: types) {
            String typeName = type.split(":")[0].trim();
            writer.println(" R visit" + typeName + baseName + "(" + typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println(" }");
    }

}
