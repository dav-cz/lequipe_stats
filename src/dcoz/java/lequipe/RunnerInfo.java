package dcoz.java.lequipe;

/**
 * Simple structure that holds some basic information for a given Runner.
 */
public class RunnerInfo {
  // Race time in seconds.
  public int timeSec = 0;

  public enum Category {
    ALL,
    MEN,
    WOMEN,
  }

  // Runner's category.
  public Category category = Category.MEN;
}
