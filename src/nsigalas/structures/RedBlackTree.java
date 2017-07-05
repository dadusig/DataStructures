package nsigalas.structures;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree
{
    public static class Node
    {
        static final int BLACK = 0;
        static final int RED = 1;
        int key;

        Node parent;
        Node left;
        Node right;

        int numLeft = 0; //num of elements to the left
        int numRight = 0; //num of elements to the right
        int color;

        public Node()
        {
            color = BLACK;
            numLeft = 0;
            numRight = 0;
            parent = null;
            left = null;
            right = null;
        }

        //constructor who sets the key
        public Node(int key)
        {
            this(); //calls RedBlackNode() constructor
            this.key = key;
        }
    }

    private Node nil = new Node();
    private Node root = nil;

    public RedBlackTree()
    {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    private void rotateLeft(Node x) //performs a left rotate around x
    {
        rotateLeftFix(x); //updates numLeft and numRight

        Node y;
        y = x.right;
        x.right = y.left;


        if(!isNil(y.left))
            y.left.parent = x;

        y.parent = x.parent;

        if (isNil(x.parent))
            root = y;
        else if (x.parent.left == x)
            x.parent.left = y;
        else
            x.parent.right = y;

        y.left = x;
        x.parent = y;
    }

    private void rotateLeftFix(Node x)
    {
        if (isNil(x.left) && isNil(x.right.left))
        { // Case 1: Only x, x.right and x.right.right always are not nil.
            x.numLeft = 0;
            x.numRight = 0;
            x.right.numLeft = 1;
        }
        else if (isNil(x.left) && !isNil(x.right.left))
        { // Case 2: x.right.left also exists
            x.numLeft = 0;
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 2 + x.right.left.numLeft + x.right.left.numRight;
        }
        else if (!isNil(x.left) && isNil(x.right.left))
        { // Case 3: x.left also exists
            x.numRight = 0;
            x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;
        }
        else
        {
            x.numRight = 1 + x.right.left.numLeft + x.right.left.numRight;
            x.right.numLeft = 3 + x.left.numLeft + x.left.numRight + x.right.left.numLeft + x.right.left.numRight;
        }
    }

    private void rotateRight(Node y)
    {

        rotateRightFix(y);

        Node x = y.left;
        y.left = x.right;

        if (!isNil(x.right))
            x.right.parent = y;
        x.parent = y.parent;

        if (isNil(y.parent))
            root = x;
        else if (y.parent.right == y)
            y.parent.right = x;
        else
            y.parent.left = x;
        x.right = y;

        y.parent = x;
    }


    private void rotateRightFix(Node y)
    {
        if (isNil(y.right) && isNil(y.left.right))
        { // Case 1: Only y, y.left and y.left.left exists.
            y.numRight = 0;
            y.numLeft = 0;
            y.left.numRight = 1;
        }
        else if (isNil(y.right) && !isNil(y.left.right))
        { // Case 2: y.left.right also exists
            y.numRight = 0;
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 2 + y.left.right.numRight + y.left.right.numLeft;
        }
        else if (!isNil(y.right) && isNil(y.left.right))
        {// Case 3: y.right also exists in addition to Case 1
            y.numLeft = 0;
            y.left.numRight = 2 + y.right.numRight +y.right.numLeft;
        }
        else
        { // Case 4: y.right & y.left.right exist
            y.numLeft = 1 + y.left.right.numRight + y.left.right.numLeft;
            y.left.numRight = 3 + y.right.numRight + y.right.numLeft + y.left.right.numRight + y.left.right.numLeft;
        }
    }

    public void insert(int key) {
        insert(new Node(key));
    }

    private void insert(Node z)
    {
        Node y = nil;
        Node x = root;

        while (!isNil(x))
        {
            y = x;

            if (z.key < x.key) // if z.key is <  current key -> left
            {// Update x.numLeft if z <  x
                x.numLeft++;
                x = x.left;
            }
            else // else z.key >= x.key -> right.
            { // update x.numGreater if z is => x
                x.numRight++;
                x = x.right;
            }
        }
        z.parent = y;

        // Depending on the value of y.key, put z as the left or right child of y
        if (isNil(y))
            root = z;
        else if (z.key< y.key)
            y.left = z;
        else
            y.right = z;

        z.left = nil;
        z.right = nil;
        z.color = Node.RED;

        // Call insertFixup(z)
        insertFixup(z);

    }

    private void insertFixup(Node z)
    {

       Node y = nil;
        while (z.parent.color == Node.RED)
        {
            if (z.parent == z.parent.parent.left)
            {
                y = z.parent.parent.right;

                if (y.color == Node.RED)
                {
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else if (z == z.parent.right)
                {

                    // leftRotate around z's parent
                    z = z.parent;
                    rotateLeft(z);
                }

                else
                {
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    rotateRight(z.parent.parent);
                }
            }
            else
            {
                y = z.parent.parent.left;

                if (y.color == Node.RED)
                {// Case 1: if y is red...recolor
                    z.parent.color = Node.BLACK;
                    y.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    z = z.parent.parent;
                }
                else if (z == z.parent.left)
                {
                    z = z.parent;
                    rotateRight(z);
                }
                else
                {
                    z.parent.color = Node.BLACK;
                    z.parent.parent.color = Node.RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }

        root.color = Node.BLACK;

    }

    public Node search(int key)
    {

        Node current = root;

        while (!isNil(current)){

            if (current.key ==key)
                return current;
            else if (current.key < key)
                current = current.right;
            else
                current = current.left;
        }

        return null;
    }

    private boolean isNil(Node node)
    {
        return node == nil;
    }

    public int size()
    {
        return root.numLeft + root.numRight + 1;
    }

    public void exportToGraphViz() throws IOException
    {
        FileWriter writer = new FileWriter("graph_redblack.gv");
        writer.append("digraph G\n{\n" );

        Queue<Node> level  = new LinkedList<>();
        level.add(root);
        while(!level.isEmpty()){
            Node node = level.poll();
            if (node!=nil)
            {
                if (node.color==1)
                    writer.append(node.key +" [color=red style=filled fillcolor=red];\n");
                else
                    writer.append(node.key +"  [color=black style=filled fillcolor=black fontcolor=white];\n");
            }
            if (node.left!=nil)
                writer.append(node.key +" -> "+node.left.key+";\n");
            if (node.right!=nil)
                writer.append(node.key +" -> "+node.right.key+";\n");
            if(node.left!= nil)
                level.add(node.left);
            if(node.right!= nil)
                level.add(node.right);
        }

        writer.append("}" );

        writer.flush();
        writer.close();

        System.out.println("\n~~~~~ grpah file for red black tree created succesfully! ~~~~~\n");

    }
}
