import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Error: Grid size and trials must be greater than 0.");
    }

    double[] results = new double[trials];

    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);

      int openedSites = 0;
      while (!perc.percolates()) {
        int row;
        int column;

        do {
          row = StdRandom.uniform(1, n + 1);
          column = StdRandom.uniform(1, n + 1);
        } while (perc.isOpen(row, column));

        perc.open(row, column);
        openedSites++;
      }

      results[i] = (double) openedSites / (n * n);
    }

    mean = StdStats.mean(results);
    stddev = StdStats.stddev(results);
    double confidenceResult = (1.96 * stddev()) / Math.sqrt(trials);
    confidenceLo = mean - confidenceResult;
    confidenceHi = mean + confidenceResult;
  }

  // sample mean of percolation threshold
  public double mean() {
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return stddev;
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return confidenceLo;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return confidenceHi;
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats percStats = new PercolationStats(n, t);

    String confidence = percStats.confidenceLo() + ", " + percStats.confidenceHi();
    StdOut.println("mean                    = " + percStats.mean());
    StdOut.println("stddev                  = " + percStats.stddev());
    StdOut.println("95% confidence interval = " + confidence);
  }
}
