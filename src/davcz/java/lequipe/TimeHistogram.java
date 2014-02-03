package davcz.java.lequipe;

import java.util.List;

/**
 * Computes a time histogram from a list of runner infos.
 *
 * This actually manages three time histograms:
 * 1) one for all the runners.
 * 2) one for the MEN runners.
 * 3) one for the WOMEN runners.
 */
public class TimeHistogram {
  // Number of time histograms.
  private static final int NUM_CAT_HISTOGRAMS = 3;

  private static final int CAT_INDEX_ALL = 0;
  private static final int CAT_INDEX_MEN = 1;
  private static final int CAT_INDEX_WOMEN = 2;

  // The maximum running time we expect to get. This is a pessimistic value only used to initialize
  // the histogram array.
  private final int RACE_MAX_SECS = 3600 * 3;

  // The number of seconds that a histogram bins represents.
  private final int binSecs;

  // The actual bins.
  // bins[category][binIndex] represents the number of runners for that "category" that performed a
  // time in that "binIndex", which means their running time satisfies
  // binIndex * binSecs <= time < (binIndex + 1) * binSecs.
  private final int bins[][];

  // The range of bin indexes for which we have data.
  // This allows us to limit the CSV output to just what was actually "covered" by the runners.
  // These two values will be updated as new data is added.
  private int minBinIndex = 0;
  private int maxBinIndex = 0;

  public TimeHistogram(int binSecs) {
    this.binSecs = binSecs;
    assert this.binSecs > 0;

    int numBins = RACE_MAX_SECS / binSecs + 1;

    minBinIndex = numBins;

    // Initialize the bin array.
    bins = new int[NUM_CAT_HISTOGRAMS][numBins];
  }

  // Adds the given list of infos into the time histogram.
  // Can be called multiple times.
  public void addRunnerInfos(List<RunnerInfo> infos) {
    for (RunnerInfo info : infos) {
      addRunnerInfo(info);
    }
  }

  private void addRunnerInfo(RunnerInfo info) {
    // Compute the right bin.
    int binIndex = info.timeSec / binSecs;

    // Update the bin range.
    if (binIndex < minBinIndex) {
      minBinIndex = binIndex;
    } else if (binIndex > maxBinIndex) {
      maxBinIndex = binIndex;
    }

    // Update the ALL category for that binIndex.
    bins[CAT_INDEX_ALL][binIndex] += 1;

    // Update the right MEN-WOMEN category depending on the RunnerInfo.
    if (info.category == RunnerInfo.Category.MEN) {
      bins[CAT_INDEX_MEN][binIndex] += 1;
    } else if (info.category == RunnerInfo.Category.WOMEN) {
      bins[CAT_INDEX_WOMEN][binIndex] += 1;
    }
  }

  private String getTimeStr(int binIndex) {
    int secTotal = binIndex * binSecs;
    return "" + secTotal / 60.0;
  }

  // Outputs the time histogram into a CSV string.
  public String printCSV() {
    String csv = new String("time, all, men, women\n");

    for (int i = minBinIndex; i <= maxBinIndex; ++i) {
      csv += getTimeStr(i) + ", " + bins[0][i] + ", " + bins[1][i] + ", " + bins[2][i] + "\n";
    }
    return csv;
  }
}
