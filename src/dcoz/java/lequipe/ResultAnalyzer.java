package dcoz.java.lequipe;

import java.io.PrintWriter;
import java.util.List;

/**
 * Outputs the time distribution from the 10km Lequipe race, Paris June 23rd 2013.
 */
public class ResultAnalyzer {
  // Number of seconds in histogram bins.
  private static final int HISTO_BIN_SECS = 30;

  // Number of result pages to browse.
  private static final int NUM_RESULT_PAGES = 277;

  // Save result string to file.
  static private void saveToFile(String filename, String result) {
    try {
      PrintWriter out = new PrintWriter(filename);
      out.print(result);
      out.close();
    } catch (Exception e) {
    }
  }

  public static void main(String [] args) {
    PageFetcher fetcher = new PageFetcher();
    PageParser parser = new PageParser();
    TimeHistogram timeHisto = new TimeHistogram(HISTO_BIN_SECS);

    System.out.println("start");

    // Go over all the result pages.
    for (int i = 1; i <= NUM_RESULT_PAGES; ++i) {
      System.out.println("page " + i);

      // Fetch the page and parse its RunnerInfos.
      List<RunnerInfo> infos = parser.parse(fetcher.fetch(i));

      // Add all the RunnerInfos to our time histogram.
      timeHisto.addRunnerInfos(infos);
    }

    // Output the time histogram to CSV.
    saveToFile("/tmp/results.csv", timeHisto.printCSV());
  }
}
