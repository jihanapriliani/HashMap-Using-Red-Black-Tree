package com.company;

import java.io.*;
import java.io.PrintStream;

public class TreePrinter {
    public static String ANSI_RESET = "\u001B[0m";
    public static String ANSI_BLACK = "\u001B[30m";
    public static String ANSI_RED = "\u001B[31m";

    private Node tree;

    TreePrinter(Node tree) {
        this.tree = tree;
    }

    String traversePreOrder(Node root) {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();

        if (root.isColor() == true) {
            sb.append(ANSI_BLACK + root.getKey() + " : " + root.getValue() + ANSI_RESET);
        } else {
            sb.append(ANSI_RED + root.getKey() + " : " + root.getValue() + ANSI_RESET);
        }

        String pointerRight = "└── r ── ";
        String pointerLeft = (root.getRight() != null) ? "├── l ── " : "└── l ── ";

        traverseNodes(sb, "", pointerLeft, root.getLeft(), root.getRight() != null);
        traverseNodes(sb, "", pointerRight, root.getRight(), false);

        return sb.toString();
    }

    void traverseNodes(StringBuilder sb, String padding, String pointer, Node node, boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);

            if (node.isColor() == true) {
                sb.append(ANSI_BLACK + node.getKey() + " : " + node.getValue() + ANSI_RESET);
            } else {
                sb.append(ANSI_RED + node.getKey() + " : " + node.getValue() + ANSI_RESET);
            }

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└── r ── ";
            String pointerLeft = (node.getRight() != null) ? "├── l ── " : "└── l ── ";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }


    void print(PrintStream os) {
        os.print(traversePreOrder(this.tree));
    }

}
