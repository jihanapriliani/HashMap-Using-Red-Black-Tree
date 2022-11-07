package com.company;

public class RedBlackTree {
    private Node root;

    static private boolean RED = false;
    static private boolean BLACK = true;

    public Node getRoot() {
        return root;
    }

//    used to rotate to the right
    public void rotateRight(Node node) {
        Node parent = node.getParent();
        Node leftChild = node.getLeft();

        node.setLeft(leftChild.getRight());
        if (leftChild.getRight() != null) {
            leftChild.getRight().setParent(node);
        }

        leftChild.setRight(node);
        node.setParent(leftChild);

        replaceParentsChild(parent, node, leftChild);
    }

//    used to rotate to the left
    public void rotateLeft(Node node) {
        Node parent = node.getParent();
        Node rightChild = node.getRight();

        node.setRight(rightChild.getLeft());
        if (rightChild.getLeft() != null) {
            rightChild.getLeft().setParent(node);
        }

        rightChild.setLeft(node);
        node.setParent(rightChild);

        replaceParentsChild(parent, node, rightChild);
    }

//    used to search spesific node based on the data or key
    public Node searchNode(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.getData()) {
                return node;
            } else if (key < node.getData()) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return null;
    }

//    used to insert node
    public void insertNode(int key) {
        Node node = root;
        Node parent = null;

        while (node != null) {
            parent = node;
            if (key < node.getData()) {
                node = node.getLeft();
            } else if (key > node.getData()) {
                node = node.getRight();
            } else {
                throw new IllegalArgumentException("Tree already contains a node with that value!!");
            }
        }

        Node newNode = new Node(key);
        newNode.setColor(RED);
        if (parent == null) {
            root = newNode;
        } else if (key < parent.getData()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }

        newNode.setParent(parent);

        fixRedBlackPropertiesAfterInsert(newNode);
    }

//    after inserting node used to recolor the violated nodes
    private void fixRedBlackPropertiesAfterInsert(Node node) {
        Node parent = node.getParent();

        if (parent == null) {
            node.setColor(BLACK);
            return;
        }

        if (parent.isColor() == BLACK) {
            return;
        }

        Node grandparent = parent.getParent();
        if (grandparent == null) {
            parent.setColor(BLACK);
            return;
        }


        Node uncle = getUncle(parent);
        if (uncle != null && uncle.isColor() == RED) {
            parent.setColor(BLACK);
            grandparent.setColor(RED);
            uncle.setColor(BLACK);

            fixRedBlackPropertiesAfterInsert(grandparent);
        } else if (parent == grandparent.getLeft()) {
            if (node == parent.getRight()) {
                rotateRight(parent);
                parent = node;
            }

            rotateRight(grandparent);

            parent.setColor(BLACK);
            grandparent.setColor(RED);
        } else {
            if (node == parent.getLeft()) {
                rotateRight(parent);
                parent = node;
            }

            rotateLeft(grandparent);

            parent.setColor(BLACK);
            grandparent.setColor(RED);
        }
    }

//    used to delete node
    public void deleteNode(int key) {
        Node node = root;

        while (node != null && node.getData() != key) {
            if (key < node.getData()) {
                node =  node.getLeft();
            } else {
                node = node.getRight();
            }
        }

        if (node == null) {
            return;
        }

        Node movedUpNode;
        boolean deleteNodeColor;
        if (node.getLeft() == null || node.getRight() == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deleteNodeColor = node.isColor();
        } else {
            Node inOrderSuccessor = findMinimum(node.getRight());
            node.setData(inOrderSuccessor.getData());

            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deleteNodeColor = inOrderSuccessor.isColor();
        }

        if (deleteNodeColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);

            if (movedUpNode.getClass() ==  NilNode.class) {
                replaceParentsChild(movedUpNode.getParent(), movedUpNode, null);
            }
        }
    }

//   after deleting node used to recolor the violated nodes
    private void fixRedBlackPropertiesAfterDelete(Node node) {
        if (node == root) {
            node.setColor(BLACK);
            return;
        }

        Node sibling = getSibling(node);

        if (sibling.isColor() == RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        if (isBlack(sibling.getLeft()) && isBlack(sibling.getRight())) {
            sibling.setColor(RED);

            if (node.getParent().isColor() == RED) {
                node.getParent().setColor(BLACK);
            } else {
                fixRedBlackPropertiesAfterDelete(node.getParent());
            }
        } else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

//    used to get sibling of node
    private Node getSibling(Node node) {
        Node parent = node.getParent();
        if (node == parent.getLeft()) {
            return parent.getRight();
        } else if (node == parent.getRight()) {
            return parent.getLeft();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent!!");
        }
    }

//    return whether the node color is black or not
    private boolean isBlack(Node node) {
        return node == null || node.isColor() == BLACK;
    }

//    used to handle red sibling
    private void handleRedSibling(Node node, Node sibling) {
        sibling.setColor(BLACK);
        node.getParent().setColor(RED);

        if (node == node.getParent().getLeft()) {
            rotateLeft(node.getParent());
        } else {
            rotateRight(node.getParent());
        }
    }


//    used to handle black sibling that has min one red child
    private void handleBlackSiblingWithAtLeastOneRedChild(Node node, Node sibling) {
        boolean nodeIsLeftChild = node == node.getParent().getLeft();

        if (nodeIsLeftChild && isBlack(sibling.getRight())) {
            sibling.getLeft().setColor(BLACK);
            sibling.setColor(RED);
            rotateRight(sibling);
            sibling = node.getParent().getRight();
        } else if (!nodeIsLeftChild && isBlack(sibling.getLeft())) {
            sibling.getRight().setColor(BLACK);
            sibling.setColor(RED);
            rotateLeft(sibling);
            sibling = node.getParent().getLeft();
        }

        sibling.setColor(node.getParent().isColor());
        node.getParent().setColor(BLACK);
        if (nodeIsLeftChild) {
            sibling.getRight().setColor(BLACK);
            rotateLeft(node.getParent());
        } else {
            sibling.getLeft().setColor(BLACK);
            rotateRight(node.getParent());
        }
    }

//    find minimum node
    private Node findMinimum(Node node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

//    used to deleted node that has one or no child
    private Node deleteNodeWithZeroOrOneChild(Node node) {
        if (node.getLeft() != null) {
            replaceParentsChild(node.getParent(), node, node.getLeft());
            return node.getLeft();
        } else if (node.getRight() != null) {
            replaceParentsChild(node.getParent(), node, node.getRight());
            return node.getRight();
        } else {
            Node newChild = node.isColor() == BLACK ? new NilNode() : null;
            replaceParentsChild(node.getParent(), node, newChild);
            return newChild;
        }
    }

//    get the uncle node
    private Node getUncle(Node parent) {
        Node grandparent = parent.getParent();
        if (grandparent.getLeft() == parent) {
            return grandparent.getRight();
        } else if (grandparent.getRight() == parent) {
            return grandparent.getLeft();
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

//    used at the end of rotation to replace or re-arrange the parent, old child and new child or transplant method
    private void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeft() == oldChild) {
            parent.setLeft(newChild);
        } else if (parent.getRight() == oldChild) {
            parent.setRight(newChild);
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.setParent(parent);
        }
    }


    private static class NilNode extends Node {
        private NilNode() {
            super(0);
            this.setColor(BLACK);
        }
    }

}


