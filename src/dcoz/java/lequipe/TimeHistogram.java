package dcoz.java.lequipe;



public class TimeHistogram {
  private static final int NUM_CAT_SELECTORS = 3;
  private static final int CAT_INDEX_ALL = 0;
  private static final int CAT_INDEX_MEN = 1;
  private static final int CAT_INDEX_WOMEN = 2;
  private final int binSecs;
  private final int RACE_MAX_SECS = 3600 * 2;
  private final int bins[][];
  private int minBinIndex;
  private int maxBinIndex = 0;

  /*
  static final public Set<RunnerInfo.Category> MEN_SELECTOR =
      new HashSet<RunnerInfo.Category>(Arrays.asList(RunnerInfo.Category.MEN));

  static final public Set<RunnerInfo.Category> WOMEN_SELECTOR =
      new HashSet<RunnerInfo.Category>(Arrays.asList(RunnerInfo.Category.WOMEN));

  static final public Set<RunnerInfo.Category> ALL_SELECTOR =
      new HashSet<RunnerInfo.Category>(
          Arrays.asList(RunnerInfo.Category.WOMEN, RunnerInfo.Category.MEN));
  */

  public TimeHistogram(int binSecs) {
    this.binSecs = binSecs;
    assert this.binSecs > 0;

    int numBins = RACE_MAX_SECS / binSecs + 1;

    minBinIndex = numBins;

    bins = new int[NUM_CAT_SELECTORS][numBins];
    for (int i = 0; i < numBins; ++i) {
      bins[0][i] = 0;
      bins[1][i] = 0;
      bins[2][i] = 0;
    }
  }


  public int addRunnerInfo(RunnerInfo info) {
    // Compute right bin.
    int binIndex = info.timeSec / binSecs;

    if (binIndex < minBinIndex) {
      minBinIndex = binIndex;
    } else if (binIndex > maxBinIndex) {
      maxBinIndex = binIndex;
    }

    bins[CAT_INDEX_ALL][binIndex] += 1;

    if (info.category == RunnerInfo.Category.MEN) {
      bins[CAT_INDEX_MEN][binIndex] += 1;
    } else if (info.category == RunnerInfo.Category.WOMEN) {
      bins[CAT_INDEX_WOMEN][binIndex] += 1;
    }

    return binIndex;
  }

  private String getTimeStr(int binIndex) {
    int secTotal = binIndex * binSecs;

    int hours = secTotal / 3600;
    int mins = secTotal / 60;
    int secs = secTotal % 60;
    // return String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
    return "" + secTotal / 60.0;
  }

  public String printCSV() {
    String csv = new String();

    for (int i = minBinIndex; i < maxBinIndex; ++i) {
      csv += getTimeStr(i) + ", " + bins[0][i] + ", " + bins[1][i] + ", " + bins[2][i] + "\n";
    }
    return csv;
  }

}
