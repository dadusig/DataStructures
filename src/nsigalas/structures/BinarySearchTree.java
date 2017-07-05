package nsigalas.structures;

public class BinarySearchTree
{
    public static class Node
    {
        int key;
        Node left, right;

        public Node()
        {

        }

        public Node(int key, Node left, Node right)
        {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    Node root;

    public BinarySearchTree()
    {
        root=null;
    }

    public void insert(int key)
    {
        root = insert(root, key);
    }

    private Node insert(Node current, int key)
    {
        if (current == null)
            current = new Node(key, null, null);
        else if (key < current.key)
            current.left = insert(current.left, key);
        else if (key > current.key)
            current.right = insert(current.right, key);

        return current;
    }

    public Node search(int key, boolean isRecursive)
    {
        //choose a search type

        if (isRecursive)
            return recursiveSearch(root, key);
        else
            return iterativeSearch(root, key);
    }

    private Node recursiveSearch(Node current, int key)
    {
        if (current == null)
            return null;

        if (current.key == key)
            return current;
        else if (key < current.key)
            current = recursiveSearch(current.left, key);
        else if (key > current.key)
            current = recursiveSearch(current.right, key);

        return current;
    }

    private Node iterativeSearch(Node current, int key)
    {
        current = root;

        while (current != null)
        {
            if (current.key == key)
                return current;
            else if (key < current.key)
                current = current.left;
            else if (key > current.key)
                current = current.right;
        }

        return null;
    }

}
