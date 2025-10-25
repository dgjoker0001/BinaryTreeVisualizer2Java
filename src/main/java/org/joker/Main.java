package org.joker;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println(" ");
        System.out.println("1. Enter the nodes separated by space");
        System.out.println("2. Nodes should be entered from top to bottom, from left to right");
        System.out.println("3. If a branch is ending, enter null");
        System.out.println("4. Press enter when done");
        System.out.print("5. Start Input: ");
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
                printTree(head, ""); // print tree
                System.out.println(" ");
                System.out.println("Tree is printed"); // tree printed confirmation
            }
        }
    }

    //    Build tree from list of nodes
    public static Node buildTree(List<String> treeNodes) {
        if ("NULL".equalsIgnoreCase(treeNodes.get(0))) { // check if head is null
            return null;
        } else {
            Node head = new Node(treeNodes.get(0));
            Queue<Node> queue = new LinkedList<>();
            queue.add(head); // add head to queue
            int i = 1;
            while (!queue.isEmpty() && i < treeNodes.size()) { // build tree using level order insertion
                Node node = queue.poll();
                if (i < treeNodes.size() && !"NULL".equalsIgnoreCase(treeNodes.get(i))) { // left child
                    node.setLeft(new Node(treeNodes.get(i)));
                    queue.add(node.getLeft());
                }
                i++;
                if (i < treeNodes.size() && !"NULL".equalsIgnoreCase(treeNodes.get(i))) { // right child
                    node.setRight(new Node(treeNodes.get(i)));
                    queue.add(node.getRight());
                }
                i++;
            }
            return head;
        }
    }

    //    Print tree in a structured format
    public static void printTree(Node node, String space) {

    }
}