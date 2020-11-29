import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private int n;
    private int trials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("PercolationStats: in constructor invalid argument");
        this.n = n;
        this.trials = trials;
        thresholds = new double[trials];
        runTests();
    }

    private void runTests() {
        for (int iteration = 0; iteration < this.trials; iteration++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int randRow = StdRandom.uniform(1, this.n + 1);
                int randCol = StdRandom.uniform(1, this.n + 1);

                grid.open(randRow, randCol);
            }
            this.thresholds[iteration] = (double)grid.numberOfOpenSites() / (double)(this.n * this.n);
        }
    }

    // sample mean of percolation threshold
     public double mean() {
         return StdStats.mean(this.thresholds);
     }

    // // sample standard deviation of percolation threshold
     public double stddev() {
         return (this.trials == 1) ? Double.NaN : StdStats.stddev(this.thresholds);
     }

    // // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (this.mean() - ((1.96 * this.stddev())/Math.sqrt(this.trials)));
    }

    // // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (this.mean() + ((1.96 * this.stddev())/Math.sqrt(this.trials)));
    }

   // test client (see below)
   public static void main(String[] args) {
       if (args.length < 2) {
           System.out.println("inavlid number of arguments");
           return;
       }
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);

       PercolationStats percStats = new PercolationStats(n, trials);

       StdOut.println("mean\t\t\t= " + percStats.mean());
       StdOut.println("stddev\t\t\t= " + percStats.stddev());
       StdOut.println("95% confidence interval\t= [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]" );

   }
}
