import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] grid;
    private WeightedQuickUnionUF unionFind;
    private int numOpenSites;
    private int virtualTop;
    private int virtualBottom;
    private int size;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Percolation: grid size cannot be 0");
        grid = new int[n + 1][n + 1];
        unionFind = new WeightedQuickUnionUF((n * n) + 2); // +2, one for row 0 the virutal top, and one for the virtual bottom
        numOpenSites = 0;
        virtualTop = 0;
        virtualBottom = (n * n) + 1;
        size = n;


        for (int row = 1; row <= n; row++)
            for (int column = 1; column <= n; column++)
                grid[row][column] = -1 * ((row - 1) * n + column); // Blocked sites are marked as a negative number
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Percolation: in open out of range");
        if (grid[row][col] > 0)
            return;
        
        grid[row][col] *= -1;

        int curr = grid[row][col];

        if (row == 1) {
            unionFind.union(curr, virtualTop);
            if (isOpen(row + 1, col))
                unionFind.union(curr, grid[row + 1][col]);
        } else if (row == size) {
            if (isOpen(row - 1, col))
                unionFind.union(curr, grid[row - 1][col]);
            unionFind.union(curr, virtualBottom);
        } else {
            if (isOpen(row - 1, col))
                unionFind.union(curr, grid[row - 1][col]);
            if (isOpen(row + 1, col))
                unionFind.union(curr, grid[row + 1][col]);
        }

        if (col == 1) {
            if (isOpen(row, col + 1))
                unionFind.union(curr, grid[row][col + 1]);
        } else if (col == size) {
            if (isOpen(row, col - 1))
                unionFind.union(curr, grid[row][col - 1]);
        } else {
            if (isOpen(row, col + 1))
                unionFind.union(curr, grid[row][col + 1]);
            if (isOpen(row, col - 1))
                unionFind.union(curr, grid[row][col - 1]);
        }
        
        numOpenSites += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Percolation: in isOpen out of bounds");
        return grid[row][col] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size)
            throw new IllegalArgumentException("Percolation: in isFull out of bounds");
        if (!isOpen(row, col))
            return false;
        return unionFind.find(grid[row][col]) == unionFind.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(virtualTop) == unionFind.find(virtualBottom);
    }
}