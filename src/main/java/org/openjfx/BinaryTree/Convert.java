package org.openjfx.BinaryTree;

import java.util.*;

public class Convert {

    private final Map<String, Integer> operators = new HashMap<>();

    Convert() {
        operators.put("+", 1);
        operators.put("-", 1);
        operators.put("/", 2);
        operators.put("*", 2);
        operators.put("$", 3);
    }

    public String convert(String initial) {

        Stack<String> stack = new Stack<>();

        StringBuilder newExpression = new StringBuilder();

        for (int index = 0; index < initial.length(); index++) {

            String currentValue = String.valueOf(initial.charAt(index));

            // if operand
            if (currentValue.matches("[a-zA-Z0-9]")) {
                newExpression.append(currentValue);
            }

            // if left parentheses
            if (currentValue.equals("(")) {
                stack.push(currentValue);
            }

            // if right parentheses
            if (currentValue.equals(")")) {

                while (stack.peek().equals("(") == false) {
                    String popped = stack.pop();
                    newExpression.append(popped);
                }

                stack.pop();
            }

            if (operators.containsKey(currentValue)) {

                if (stack.empty() || stack.peek().equals("(")) {
                    stack.push(currentValue);
                } else {

                    if (operators.get(stack.peek()) >= operators.get(currentValue)) {

                        try {
                            while (operators.get(stack.peek()) >= operators.get(currentValue)
                                    && operators.containsKey(stack.peek())) {

                                String popped = stack.pop();
                                newExpression.append(popped);

                                if (stack.empty()) {
                                    break;
                                }
                            }
                        } catch (NullPointerException e) {

                        }
                        stack.push(currentValue);
                    } else {
                        stack.push(currentValue);
                    }
                }
            }
        }

        while (true) {

            if (stack.empty()) {
                break;
            }
            String popped = stack.pop();
            newExpression.append(popped);

        }

        return String.valueOf(newExpression);

    }
}
