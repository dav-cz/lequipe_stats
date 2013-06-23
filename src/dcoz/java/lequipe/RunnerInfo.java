package dcoz.java.lequipe;

public class RunnerInfo {
  public int timeSec = 0;

  public enum Category {
    ALL,
    MEN,
    WOMEN,
  }
  public Category category = Category.MEN;
}
