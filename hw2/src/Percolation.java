import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int N; // Grid size
    private final int[] grid; // Grid
    private int size; // No. of open sites
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF sites;

    public Percolation(int N) {
        this.N = N;
        grid = new int[N*N];
        size = 0;
        top = N * N;
        bottom = N * N + 1;
        sites = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int row, int col) {
        if(!isInGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        int ind = xyTo1D(row, col);
        grid[ind] = 1;
        ++size;
        unionNeighbour(row, col);
        // Union top and bottom
        if(row == 0) {
            sites.union(xyTo1D(row, col), top);
        }
        if(row == N-1 && isFull(row, col)) {
            sites.union(xyTo1D(row, col), bottom);
        }
    }

    public boolean isOpen(int row, int col) {
        int ind = xyTo1D(row, col);
        return grid[ind] == 1;
    }

    public boolean isFull(int row, int col) {
        return sites.connected(xyTo1D(row, col), top);
    }

    public int numberOfOpenSites() {
        return this.size;
    }

    public boolean percolates() {
        return sites.connected(top, bottom);
    }

    // Helper functions
    // Convert 2D coordinates to 1D indices
    private int xyTo1D(int x, int y) {
        return x * N + y;
    }

    // Union neighbouring open sites
    private void unionNeighbour(int row, int col) {
        if(isInGrid(row+1, col) && isOpen(row+1, col)) {
            sites.union(xyTo1D(row, col), xyTo1D(row+1, col));
        }
        if(isInGrid(row-1, col) && isOpen(row-1, col)) {
            sites.union(xyTo1D(row, col), xyTo1D(row-1, col));
        }
        if(isInGrid(row, col+1) && isOpen(row, col+1)) {
            sites.union(xyTo1D(row, col), xyTo1D(row, col+1));
        }
        if(isInGrid(row, col-1) && isOpen(row, col-1)) {
            sites.union(xyTo1D(row, col), xyTo1D(row, col-1));
        }
    }

    // Detect if a 2d coordinate is in bound
    private boolean isInGrid(int row, int col) {
        return row < N && row >= 0 && col < N && col >= 0;
    }
}
