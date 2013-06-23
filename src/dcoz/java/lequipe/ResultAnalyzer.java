package dcoz.java.lequipe;

import java.io.PrintWriter;
import java.util.List;

public class ResultAnalyzer {
  private static final int HISTO_BIN_SECS = 30;

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

    for (int i = 1; i < 277; ++i) {
      System.out.println("page " + i);

      List<RunnerInfo> infos = parser.parse(fetcher.fetch(i));

      for (RunnerInfo info : infos) {
        timeHisto.addRunnerInfo(info);
      }
    }

    System.out.println("");

    //System.out.println("num infos " + infos.size());
    saveToFile("/tmp/results.csv", timeHisto.printCSV());
  }
}
