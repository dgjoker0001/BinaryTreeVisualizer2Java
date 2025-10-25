package org.joker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("1. Enter the nodes separated by space");
        System.out.println("2. Nodes should be entered from top to bottom, from left to right");
        System.out.println("3. Press enter when done");
        System.out.print("4. Start Input: ");
        String tree = sc.nextLine(); // get tree input
        System.out.println(" ");
        if (tree == null || tree.isEmpty()) {
            System.out.println("No data entered"); // empty input check
        } else {
            tree = tree.replaceAll("\\s{2,}", " "); // remove all extra space
            List<String> treeNodes = new ArrayList<>(List.of(tree.split(" "))); // split input into list

            if (treeNodes.isEmpty()) {
                System.out.println("No data entered"); // empty input check
            } else {
                Node head = buildTree(treeNodes); // build tree
                System.out.println("  ");
                System.out.println("Tree is built"); // tree built confirmation
                System.out.println(" ");
                assert head != null; // head null check
                printTree(head); // print tree
                System.out.println(" ");
                System.out.println("Tree is printed"); // tree printed confirmation
            }
        }
    }

    //    Build tree from list of nodes
    public static Node buildTree(List<String> treeNodes) {
        if (treeNodes.get(0).isEmpty()) { // check if head is null
            return null;
        } else {
            Node head = new Node(treeNodes.get(0));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head); // add head to queue
            int i = 1;
            while (!queue.isEmpty() && i < treeNodes.size()) { // build tree using level order insertion
                Node node = queue.poll();
                if (i < treeNodes.size() && !treeNodes.get(i).isEmpty()) { // left child
                    node.setLeft(new Node(treeNodes.get(i)));
                    queue.add(node.getLeft());
                }
                i++;
                if (i < treeNodes.size() && !treeNodes.get(i).isEmpty()) { // right child
                    node.setRight(new Node(treeNodes.get(i)));
                    queue.add(node.getRight());
                }
                i++;
            }
            return head;
        }
    }

    //    Print tree in a structured format
    public static void printTree(Node node) {
        List<List<String>> bfs = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) { // level order traversal to get nodes at each level
            int size = queue.size();
            List<String> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node current = queue.poll();
                assert current != null;
                level.add(current.getData());
                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
            bfs.add(level);
        }
        int heightOfTree = bfs.size();

        List<String> headNode = bfs.get(0);

        int headSpaceBetweenDouble = (int) Math.pow(2, heightOfTree + 2);
        String headSpace = " ".repeat(Math.max(0, headSpaceBetweenDouble));
        String headSpaceBy2 = " ".repeat(Math.max(0, headSpaceBetweenDouble / 2));
        System.out.println(headSpaceBy2 + headNode.get(0)); // print head node

        int reduceBy = 0;
        int topValueForLevelWiseAdjustment = 1; // to adjust spaces level wise
        int nextIncrement = 2; // to adjust spaces level wise

        for (int i = 1; i < bfs.size(); i++) {
            List<String> level = bfs.get(i);
            double spaceBetweenDouble = Math.pow(2, heightOfTree + 2) / ((int) Math.pow(2, i));
            BigDecimal bigdecimal = BigDecimal.valueOf(spaceBetweenDouble).setScale(0, RoundingMode.HALF_UP); // calculate spaces between nodes
            int spacesBetweenNode = bigdecimal.intValue();
            StringBuilder sb = new StringBuilder();

            if (i > topValueForLevelWiseAdjustment) {
                topValueForLevelWiseAdjustment = topValueForLevelWiseAdjustment + nextIncrement;
                reduceBy = reduceBy + 1;
                nextIncrement = nextIncrement + 1;
            }

            String space = " ".repeat(Math.max(0, spacesBetweenNode - reduceBy));

            String spaceBy2 = " ".repeat(Math.max(0, spacesBetweenNode / 2));
            sb.append(spaceBy2);

            for (int k = 0; k < level.size(); k++) {
                if (k % 2 == 0) {
                    sb.append("/");
                } else {
                    sb.append("\\");
                }
                sb.append(space);
            }

            System.out.println(sb);
            sb = new StringBuilder();
            sb.append(spaceBy2);
            for (int k = 0; k < level.size(); k++) {
                String val = level.get(k);
                sb.append(val);
                sb.append(space);
                if (k % 2 != 0) {
                    sb.append(" ".repeat(Math.max(0, reduceBy)));
                }
            }

            System.out.println(sb);
        }
    }
}