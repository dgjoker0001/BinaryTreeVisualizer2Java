package org.joker;

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

        // Adjusted base spacing calculation
        int baseSpace = 4;
        int headSpacing = baseSpace * (int) Math.pow(2, heightOfTree - 1); // initial spacing for head node
        System.out.println(" ".repeat(headSpacing) + bfs.get(0).get(0)); // print head node

        int currentSpacing = headSpacing;
        for (int i = 1; i < bfs.size(); i++) {
            List<String> level = bfs.get(i);
            currentSpacing = currentSpacing / 2; // reduce spacing for each level

            // Print branches
            StringBuilder branches = getBranches(currentSpacing, level);
            System.out.println(branches);

            // Print values
            StringBuilder values = getValues(currentSpacing, level);
            System.out.println(values);
        }
    }

    private static StringBuilder getBranches(int currentSpacing, List<String> level) {
        StringBuilder branches = new StringBuilder();
        branches.append(" ".repeat(currentSpacing)); // initial spacing
        for (int k = 0; k < level.size(); k++) {
            branches.append(k % 2 == 0 ? "/" : "\\");
            // Adjust spacing between branches
            if (k < level.size() - 1) {
                int spaceBetween = Math.max(currentSpacing * 2 - 1, 1); // ensure at least 1 space
                branches.append(" ".repeat(spaceBetween));
            }
        }
        return branches;
    }

    private static StringBuilder getValues(int currentSpacing, List<String> level) {
        StringBuilder values = new StringBuilder();
        values.append(" ".repeat(currentSpacing)); // initial spacing
        for (int k = 0; k < level.size(); k++) {
            values.append(level.get(k)); // append node value
            // Adjust spacing between values
            if (k < level.size() - 1) {
                int spaceBetween = Math.max(currentSpacing * 2 - level.get(k).length(), 1); // ensure at least 1 space
                values.append(" ".repeat(spaceBetween));
            }
        }
        return values;
    }
}