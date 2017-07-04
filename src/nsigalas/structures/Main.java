package nsigalas.structures;

//for test purposes

public class Main
{
    public static void main(String args[])
    {
        BinarySearchTree tree = new BinarySearchTree();

        tree.insert(20);
        tree.insert(30);
        tree.insert(10);

        if (tree.search(20, false) != null)
        {
            System.out.println("Vrethike");
        }



        System.out.println();
    }
}
