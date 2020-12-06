import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int[][] tiles;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(" " + this.tiles[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int incorrectTiles = 0;

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int expectedVal = (i * n) + j + 1;
                if (this.tiles[i][j] != expectedVal && this.tiles[i][j] != 0)
                    incorrectTiles++;
            }
        }
        return incorrectTiles;
    }

    private int abs(int num) {
        if (num < 0) {
            num *= -1;
        }
        return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int row = (this.tiles[i][j] - 1) / this.n;
                int col = (this.tiles[i][j] - 1) % this.n;
                if (this.tiles[i][j] != 0)
                    sum += (abs(i - row) + abs(j - col));
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y instanceof Board) {
            Board other = (Board) y;
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.n; j++) {
                    if (this.tiles[i][j] != other.tiles[i][j])
                        return false;
                }
            }
            return true;

        } else
            return false;
    }

    private int[][] copyTiles() {
        int[][] copy = new int[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                copy[i][j] = this.tiles[i][j];
            }
        }
        return copy;
    }

    private int[] findBlank(int[][] tiles) {
        int[] blankPos = new int[2];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    blankPos[0] = i;
                    blankPos[1] = j;
                    return blankPos;
                }
            }
        }
        return null;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] blankPos = findBlank(this.tiles);
        int row = blankPos[0];
        int col = blankPos[1];
        Queue<Board> queue = new Queue<Board>();

        if (row > 0) {
            int[][] copy = copyTiles();
            int temp = copy[row][col];
            copy[row][col] = copy[row - 1][col];
            copy[row - 1][col] = temp;
            queue.enqueue(new Board(copy));
        }

        if (row < this.n - 1) {
            int[][] copy = copyTiles();
            int temp = copy[row][col];
            copy[row][col] = copy[row + 1][col];
            copy[row + 1][col] = temp;
            queue.enqueue(new Board(copy));
        }

        if (col > 0) {
            int[][] copy = copyTiles();
            int temp = copy[row][col];
            copy[row][col] = copy[row][col - 1];
            copy[row][col - 1] = temp;
            queue.enqueue(new Board(copy));
        }

        if (col < this.n - 1) {
            int[][] copy = copyTiles();
            int temp = copy[row][col];
            copy[row][col] = copy[row][col + 1];
            copy[row][col + 1] = temp;
            queue.enqueue(new Board(copy));
        }
        return queue;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = copyTiles();
        int prevVal = copy[0][0];

        for (int i = 0; i < copy.length; i++) {
            for (int j = 0; j < copy[0].length; j++) {
                int val = copy[i][j];
                if (j > 0 && val != 0 && prevVal != 0) {
                    copy[i][j] = prevVal;
                    copy[i][j - 1] = val;
                    return new Board(copy);
                }
                prevVal = val;
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        Iterable<Board> a = initial.neighbors();
        System.out.println(initial.toString());
        for (var i : a) {
            System.out.println(i);
        }
        Board twin = initial.twin();
        System.out.println(twin);

        System.out.println("Manhattan: " + initial.manhattan());
        System.out.println("Hamming: " + initial.hamming());
        System.out.println(initial.equals(twin));
    }

}
