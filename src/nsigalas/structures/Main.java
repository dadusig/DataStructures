package nsigalas.structures;

//for test purposes

import java.io.IOException;

public class Main
{
    public static void main(String args[]) throws IOException
    {
        RedBlackTree rb = new RedBlackTree();

        rb.insert(10);
        rb.insert(40);
        rb.insert(50);
        rb.insert(5);
        rb.insert(20);
        rb.insert(30);
        rb.insert(15);

        rb.exportToGraphViz();


        System.out.println();
    }
}
