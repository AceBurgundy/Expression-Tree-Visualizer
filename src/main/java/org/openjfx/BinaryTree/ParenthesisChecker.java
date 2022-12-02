package org.openjfx.BinaryTree;

import java.util.Stack;

public class ParenthesisChecker {

    final String expression[];

    public ParenthesisChecker(String input) {
        expression = input.split("");
    }

    public boolean hasBalancedParenthesis() {

        Stack<String> stack = new Stack<>();

        for (String element : expression) {
            switch (element) {
                case "(":
                case "{":
                case "[":
                    stack.push(element);
                    break;
                case ")":
                    if (stack.isEmpty() || !stack.pop().equals("(")) {
                        return false;
                    }
                    break;
                case "}":
                    if (stack.isEmpty() || !stack.pop().equals("{")) {
                        return false;
                    }
                    break;
                case "]":
                    if (stack.isEmpty() || !stack.pop().equals("[")) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }

        return stack.isEmpty();

    }
}
