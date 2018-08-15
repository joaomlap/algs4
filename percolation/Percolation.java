import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean[][] grid;
  private final int gridSize;
  private int openedSites;

  private final WeightedQuickUnionUF weightedQU;
  private final WeightedQuickUnionUF weightedQUBackwash;
  private final int virtualTop;
  private final int virtualBottom;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("Error: 'n' must be at least 1!");
    }

    grid = new boolean[n][n];
    gridSize = n;
    openedSites = 0;

    // n matrix + 2 for top and bottom virtual sites
    weightedQU = new WeightedQuickUnionUF(n * n + 2);

    // avoid backwash problem:
    weightedQUBackwash = new WeightedQuickUnionUF(n * n + 1);

    virtualTop = 0;
    virtualBottom = n * n + 1;
  }

  public void open(int row, int col) {
    if (outOfBounds(row, col)) {
      throw new IllegalArgumentException("Error: Row or column out of bounds!");
    }

    if (!isOpen(row, col)) {
      grid[row - 1][col - 1] = true;
      int index = rowColToIndex(row, col);

      // connect to virtual if 1st row or virtual if last row
      if (row == 1) {
        weightedQU.union(index, virtualTop);
        weightedQUBackwash.union(index, virtualTop);
      }

      if (row == gridSize) {
        weightedQU.union(index, virtualBottom);
      }

      // now we need to connect site to any open neighbour
      if (col - 1 >= 1 && isOpen(row, col - 1)) {
        int leftNeighbour = rowColToIndex(row, col - 1);
        weightedQU.union(index, leftNeighbour);
        weightedQUBackwash.union(index, leftNeighbour);
      }

      if (row - 1 >= 1 && isOpen(row - 1, col)) {
        int topNeighbour = rowColToIndex(row - 1, col);
        weightedQU.union(index, topNeighbour);
        weightedQUBackwash.union(index, topNeighbour);
      }

      if (col + 1 <= gridSize && isOpen(row, col + 1)) {
        int rightNeighbour = rowColToIndex(row, col + 1);
        weightedQU.union(index, rightNeighbour);
        weightedQUBackwash.union(index, rightNeighbour);
      }

      if (row + 1 <= gridSize && isOpen(row + 1, col)) {
        int bottomNeighbour = rowColToIndex(row + 1, col);
        weightedQU.union(index, bottomNeighbour);
        weightedQUBackwash.union(index, bottomNeighbour);
      }

      openedSites++;
    }
  }

  public boolean isOpen(int row, int col) {
    if (outOfBounds(row, col)) {
      throw new IllegalArgumentException("Error: Row or column out of bounds!");
    }

    return grid[row - 1][col - 1];
  }

  public boolean isFull(int row, int col) {
    if (outOfBounds(row, col)) {
      throw new IllegalArgumentException("Error: Row or column out of bounds!");
    }

    boolean result = false;

    if (isOpen(row, col)) {
      int index = rowColToIndex(row, col);

      result = weightedQUBackwash.connected(index, virtualTop);
    }

    return result;
  }

  public int numberOfOpenSites() {
    return openedSites;
  }

  public boolean percolates() {
    return weightedQU.connected(virtualTop, virtualBottom);
  }

  private int rowColToIndex(int row, int col) {
    return (row - 1) * gridSize + col; // need to account for virtual top site
  }

  private boolean outOfBounds(int row, int col) {
    return row < 1 || row > gridSize || col < 1 || col > gridSize;
  }
}
