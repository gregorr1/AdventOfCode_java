package Naloga8;

public class Node {
    private String name;
    private String leftNode;
    private String rightNode;

    public Node(String name, String leftNode, String rightNode) {
        this.name = name;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLeftNode() {
        return leftNode;
    }
    public void setLeftNode(String leftNode) {
        this.leftNode = leftNode;
    }
    public String getRightNode() {
        return rightNode;
    }
    public void setRightNode(String rightNode) {
        this.rightNode = rightNode;
    }

    @Override
    public String toString() {
        return name + " = (" + leftNode + ", " + rightNode + ")";
    }
}
