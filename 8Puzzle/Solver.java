import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode previous;
        private int moves;
        private int manhattan;

        public SearchNode(Board board) {
            this.board = board;
            this.moves = 0;
            this.previous = null;
            this.manhattan = this.board.manhattan();
        }

        @Override
        public int compareTo(Solver.SearchNode other) {
            if (this.manhattan + this.moves > other.manhattan + other.moves)
                return 1;

            if (this.manhattan + this.moves < other.manhattan + other.moves)
                return -1;

            return 0;
        }
    }

    private SearchNode solutionNode;
    private SearchNode twinStartNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        solve(initial);
    }

    private void solve(Board initial) {
        solutionNode = new SearchNode(initial);
        twinStartNode = new SearchNode(initial.twin());
        MinPQ<SearchNode> solutionPath = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinSolutionPath = new MinPQ<SearchNode>();

        solutionPath.insert(solutionNode);
        twinSolutionPath.insert(twinStartNode);

        while (true) {
            solutionNode = solutionPath.delMin();
            twinStartNode = twinSolutionPath.delMin();

            if (twinStartNode.board.isGoal()) {
                solutionNode.moves = -1;
                solutionNode.previous = null;
                return;
            }

            if (solutionNode.board.isGoal())
                return;

            addNeighbours(solutionPath, solutionNode);
            addNeighbours(twinSolutionPath, twinStartNode);
        }
    }

    private void addNeighbours(MinPQ<SearchNode> pq, SearchNode curr) {
        for (Board neighbour : curr.board.neighbors()) {
            if (curr.previous == null || !neighbour.equals(curr.previous.board)) {
                SearchNode neighbourNode = new SearchNode(neighbour);
                neighbourNode.moves = curr.moves + 1;
                neighbourNode.previous = curr;
                pq.insert(neighbourNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves() >= 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.solutionNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> sol = new Stack<Board>();
        while (solutionNode != null) {
            sol.push(solutionNode.board);
            solutionNode = solutionNode.previous;
        }
        return sol;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}