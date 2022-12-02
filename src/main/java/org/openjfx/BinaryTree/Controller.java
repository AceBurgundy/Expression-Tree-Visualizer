package org.openjfx.BinaryTree;

import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.fxml.FXML;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.Cursor;
import javafx.scene.transform.Rotate;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    public Pane screen;

    @FXML
    public TextField arrayInput;

    @FXML
    public Button createButton;

    public Label traversalResult;

    final Cursor hand = Cursor.cursor("HAND");

    final Color buttonBoxShadowColor = Color.rgb(176, 153, 153);

    ArrayList<Node> NodeList = new ArrayList<>();

    ArrayList<Button> buttonList = new ArrayList<>();

    ArrayList<Rectangle> arrowList = new ArrayList<>();

    ArrayList<Circle> circleList = new ArrayList<>();

    ArrayList<Text> textList = new ArrayList<>();

    StringBuilder traversalResultString = new StringBuilder();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    private Node root;

    private enum Direction {
        LEFT,
        RIGHT,
        NONE
    }

    public class Node {

        final String value;

        Node left, right;
        private Circle NodeCircle;

        Node(String value) {
            this.value = value;
            left = right = null;
            NodeCircle = null;
        }

        public void setNodeCircle(Circle NodeCircle) {
            this.NodeCircle = NodeCircle;
        }
    }

    private void createCircle(double x, double y, Node node, Direction direction, String valueOfParent) {

        Circle circle = new Circle();
        circle.setRadius(20.f);
        circle.setCenterX(x);
        circle.setCenterY(y);

        circle.setFill(Color.rgb(254, 242, 191));

        Rectangle arrow = new Rectangle();
        arrow.setWidth(5);
        arrow.setHeight(51);
        arrow.setY(y - 61.5);

        if (direction == Direction.RIGHT) {

            if (valueOfParent.equals(root.value)) {
                x -= 40;
                arrow.setHeight(83);
                circle.setCenterX(x);
                arrow.setY(y - 75);
                arrow.setX(arrow.getX() + x + 50.7);
                arrow.setFill(Color.AQUA);
                arrow.getTransforms().add(new Rotate(53.5, arrow.getX() + 4, arrow.getY() - 1 + 37));
            } else {
                arrow.setX(arrow.getX() + x + 20);
                arrow.setFill(Color.AQUA);
                arrow.getTransforms().add(new Rotate(43, arrow.getX() + 4, arrow.getY() + 35));
            }
        }

        if (direction == Direction.LEFT) {
            if (valueOfParent.equals(root.value)) {
                x += 40;
                arrow.setHeight(82.3);
                circle.setCenterX(x);
                arrow.setY(y - 75);
                arrow.setX(arrow.getX() + x - 57.8);
                arrow.setFill(Color.PINK);
                arrow.getTransforms().add(new Rotate(-54.8, arrow.getX() + 4, arrow.getY() + 37));
            } else {
                arrow.setX(arrow.getX() + x - 30);
                arrow.setFill(Color.PINK);
                arrow.getTransforms().add(new Rotate(-43, arrow.getX() + 4, arrow.getY() + 32));
            }
        }

        if (direction == Direction.NONE) {
            arrow.setOpacity(0);
        }

        Text text = new Text(node.value);

        text.getStyleClass().add("expression-tree-node-text");

        text.setX(x);
        text.setY(y);
        text.setX(text.getX() - text.getLayoutBounds().getWidth() / 1.1);
        text.setY(text.getY() + text.getLayoutBounds().getHeight() / 2.5);
        text.setFill(Color.BLACK);
        circleList.add(circle);
        arrowList.add(arrow);
        textList.add(text);

        node.setNodeCircle(circle);

        if (node.left != null) {
            createCircle(x - 60, y + 70, node.right, Direction.RIGHT, node.value);
        }

        if (node.right != null) {
            createCircle(x + 60, y + 70, node.left, Direction.LEFT, node.value);
        }

    }

    public void printBinaryTree() {
        new Thread(() -> {
            Collections.reverse(NodeList);
            for (int index = 0; index < arrowList.size(); index++) {

                final int runner = index;

                Platform.runLater(() -> screen.getChildren().addAll(arrowList.get(runner), circleList.get(runner),
                        textList.get(runner)));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        }).start();
    }

    public void loopThroughNodes() {
        new Thread(() -> {
            Collections.reverse(NodeList);
            for (Node Node : NodeList) {

                if (Node == NodeList.get(NodeList.size() - 1)) {
                    traversalResultString.append(String.valueOf(Node.value));
                } else {
                    traversalResultString.append(String.valueOf(Node.value + ", "));
                }

                Platform.runLater(() -> Node.NodeCircle.setFill(Color.ORANGE));
                Platform.runLater(() -> traversalResult.setText(String.valueOf(traversalResultString)));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        }).start();
    }

    public void NodeListReset() {
        for (Node Node : NodeList) {
            Node.NodeCircle.setFill(Color.rgb(254, 242, 191));
        }
        traversalResultString.setLength(0);
        NodeList.clear();
    }

    public void showButtonsForTraversal() {

        traversalResult = new Label();
        traversalResult.setTranslateX(40);
        traversalResult.setTranslateY(577);
        traversalResult.getStyleClass().add("traversal-result");

        Rectangle preorderBoxShadow = new Rectangle(20, 90, 185, 35);
        preorderBoxShadow.setFill(buttonBoxShadowColor);
        preorderBoxShadow.setArcHeight(5);
        preorderBoxShadow.setArcWidth(5);

        Button preorder = new Button("PREORDER");
        preorder.getStyleClass().add("button");
        preorder.setTranslateX(10);
        preorder.setTranslateY(80);
        preorder.getWidth();
        preorder.setCursor(hand);
        preorder.setOnAction(ActionEvent -> {
            NodeListReset();
            traversePreorder(root);
            loopThroughNodes();
        });
        buttonList.add(preorder);

        Rectangle inorderBoxShadow = new Rectangle(20, 160, 165, 35);
        inorderBoxShadow.setFill(buttonBoxShadowColor);
        inorderBoxShadow.setArcHeight(5);
        inorderBoxShadow.setArcWidth(5);

        Button inorder = new Button("INORDER");
        inorder.getStyleClass().add("button");
        inorder.setTranslateX(10);
        inorder.setTranslateY(150);
        inorder.setCursor(hand);
        inorder.setOnAction(ActionEvent -> {
            NodeListReset();
            traverseInorder(root);
            loopThroughNodes();
        });
        buttonList.add(inorder);

        Rectangle postorderBoxShadow = new Rectangle(20, 230, 205, 35);
        postorderBoxShadow.setFill(buttonBoxShadowColor);
        postorderBoxShadow.setArcHeight(5);
        postorderBoxShadow.setArcWidth(5);

        Button postorder = new Button("POSTORDER");
        postorder.getStyleClass().add("button");
        postorder.setTranslateX(10);
        postorder.setTranslateY(220);
        postorder.setCursor(hand);
        postorder.setOnAction(ActionEvent -> {
            NodeListReset();
            traversePostorder(root);
            loopThroughNodes();
        });
        buttonList.add(postorder);

        screen.getChildren().addAll(preorderBoxShadow, preorder, inorderBoxShadow, inorder, postorderBoxShadow,
                postorder, traversalResult);
    }

    public void traverseInorder(Node Node) {
        if (Node == null) {
            return;
        }
        traverseInorder(Node.left);
        NodeList.add(Node);
        traverseInorder(Node.right);
    }

    public void traversePreorder(Node Node) {
        if (Node == null) {
            return;
        }
        NodeList.add(Node);
        traversePreorder(Node.left);
        traversePreorder(Node.right);
    }

    public void traversePostorder(Node Node) {
        if (Node == null) {
            return;
        }
        traversePostorder(Node.left);
        traversePostorder(Node.right);
        NodeList.add(Node);
    }

    public void showResetTreeButton() {
        Rectangle resetBoxShadow = new Rectangle(20, 20, 125, 35);
        resetBoxShadow.setFill(buttonBoxShadowColor);
        resetBoxShadow.setArcHeight(5);
        resetBoxShadow.setArcWidth(5);

        Button reset = new Button("RESET");
        reset.getStyleClass().add("button");
        reset.setTranslateX(10);
        reset.setTranslateY(10);
        reset.setCursor(hand);
        buttonList.add(reset);

        screen.getChildren().addAll(resetBoxShadow, reset);

        reset.setOnAction(ActionEvent -> {
            screen.getChildren().clear();
            NodeListReset();

            arrayInput.setText("");
            root = null;
        });
    }

    public void runAlertBasicSettings() {
        alert.setContentText("Only accepts expressions as input\n\nEx: (X+5)-((A+B)/C)");
        alert.showAndWait();
        screen.getChildren().clear();
        arrayInput.setText("");
    }

    public boolean isOperator(String character) {
        return (character.equals("+") || character.equals("-") || character.equals("*") || character.equals("/")
                || character.equals("$"));
    }

    public void createTree(ActionEvent event) {

        showResetTreeButton();
        showButtonsForTraversal();

        buttonList.forEach(button -> {

            button.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    e -> {
                        button.setStyle("-fx-text-fill: orange;");
                    });

            button.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    e -> {
                        button.setStyle("-fx-text-fill: white;");
                    });
        });

        Convert machine = new Convert();

        Stack<Node> stack = new Stack<>();
        Node node;

        // stops the method if there's nothing to convert
        if (arrayInput.getText().trim().equals("")) {
            alert.setHeaderText("Expression is empty");
            runAlertBasicSettings();
            return;
        }

        // checks if threre are other symbols that are placed which are not recognized
        // as operands or operators
        if (arrayInput.getText().matches("[^a-zA-Z0-9+/*$()]")) {
            alert.setHeaderText(
                    "Only accepts numbers and letters for operands and +,-,/,$ for operators\n\nOperands must also be followed by Operator\n\n3C is not accepted\n\n3+ is accepted");
            runAlertBasicSettings();
            return;
        }

        ParenthesisChecker expression = new ParenthesisChecker(arrayInput.getText());

        // checks for unbalanced parentheses
        if (!expression.hasBalancedParenthesis()) {
            alert.setHeaderText("Expression has unbalanced parentheses\n\nCheck for extra {, }, (, ), [, ]");
            runAlertBasicSettings();
            return;
        }

        String input[] = arrayInput.getText().split("");

        // takes input, splits it, and uses advance for loop to iterate and to check if
        // values that are not
        // 0-9 or ',' or ' ' or any value that may trigger NumberFormatException is
        // found else, adds that number to list arrayList.
        for (int index = 0; index < arrayInput.getText().split("").length; index++) {

            try {

                if (input[index].matches("a-zA-Z0-9") && input[index + 1].matches("a-zA-Z0-9")) {
                    alert.setHeaderText(
                            "Consecutive operands " + String.join("", input[index], input[index]) + "found");
                    runAlertBasicSettings();
                    return;
                }

            } catch (NumberFormatException e) {
                alert.setHeaderText("Triggered Number Format Exception");
                runAlertBasicSettings();
                return;
            }
        }

        String postfixVersion = machine.convert(arrayInput.getText());

        for (int i = 0; i < postfixVersion.length(); i++) {
            if (!isOperator(String.valueOf(postfixVersion.charAt(i)))) {
                node = new Node(String.valueOf(postfixVersion.charAt(i)));
                stack.push(node);
            } else {
                node = new Node(String.valueOf(postfixVersion.charAt(i)));

                node.left = stack.pop();
                node.right = stack.pop();

                stack.push(node);
            }

        }

        root = stack.pop();

        createCircle(screen.getLayoutBounds().getWidth() / 2, 40.0, root, Direction.NONE, root.value);

        printBinaryTree();
    }
}
