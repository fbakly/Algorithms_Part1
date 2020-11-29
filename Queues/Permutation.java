import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
       if (args.length < 1) {
           System.out.println("inavlid number of arguments");
           return;
       }
       int k = Integer.parseInt(args[0]);
       RandomizedQueue<String> randq = new RandomizedQueue<String>();
       while (!StdIn.isEmpty()) {
           randq.enqueue(StdIn.readString());
       }
       for (int i = 0; i < k; i++)
            StdOut.println(randq.dequeue());
    }

}
