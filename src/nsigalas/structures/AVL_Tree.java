package nsigalas.structures;

public class AVL_Tree extends BinarySearchTree
{
    private static final int ALLOWED_IMBALANCE = 1;

    public static class Node extends BinarySearchTree.Node
    {
        int height;

        public Node(int key, BinarySearchTree.Node left, BinarySearchTree.Node right)
        {
            super(key, left, right);
            this.height = 0;
        }
    }

    @Override
    public void insert(int key)
    {
        root = insert(key, (Node) root);
    }

    private Node insert(int key, Node node)
    {
        if (node == null)
            return new Node(key, null, null);

        if (key < node.key)
            node.left = insert(key, (Node) node.left);
        else if (key > node.key)
            node.right = insert(key, (Node) node.right); //else is a duplicate, so do nothing

        return balance(node);
    }

    private int height(Node node)
    {
        return node == null ? -1 : node.height;
    }

    private Node balance(Node node)
    {
        if (node == null)
            return node;

        if(height((Node)node.left)-height((Node)node.right) > ALLOWED_IMBALANCE)  //  left - unknown
        {
            if(height((Node)node.left.left) >= height((Node)node.left.right)) // left - left
                node = rotateWithLeftChild(node);
            else                                            //left - right
                node = doubleWithLeftChild(node);
        }
        else                                      //right - unknown
        {
            if( height( (Node)node.right ) - height((Node) node.left ) > ALLOWED_IMBALANCE ) //right - right
                if( height( (Node)node.right.right ) >= height( (Node)node.right.left ) )
                    node = rotateWithRightChild(node);
                else                                            //right - left
                    node = doubleWithRightChild(node);
        }

        node.height = Math.max( height( (Node)node.left ), height( (Node)node.right ) ) + 1;

        return node;
    }

    private Node doubleWithRightChild(Node k1)
    {
        k1.right = rotateWithLeftChild( (Node) k1.right );
        return rotateWithRightChild( k1 );
    }

    private Node doubleWithLeftChild(Node k3)
    {
        k3.left = rotateWithRightChild( (Node) k3.left );
        return rotateWithLeftChild( k3 );
    }

    private Node rotateWithRightChild(Node k1)
    {
        Node k2 = (Node) k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( (Node) k1.left ), height( (Node) k1.right ) ) + 1;
        k2.height = Math.max( height( (Node) k2.right ), k1.height ) + 1;
        return k2;
    }

    private Node rotateWithLeftChild(Node k2)
    {
        Node k1 = (Node) k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( (Node) k2.left ), height( (Node) k2.right ) ) + 1;
        k1.height = Math.max( height( (Node) k1.left ), k2.height ) + 1;
        return k1;
    }
}
