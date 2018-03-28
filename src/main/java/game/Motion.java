package game;

/**
 * Motion enum
 */
public enum Motion {
  STONE,
  SCISSORS,
  PAPER;

  /**
   * comparing what's win against fist motion
   *
   * @return 1 - fist win, -1 - second win, 0 - draw
   */
  public static int compare(Motion firstMotion, Motion secondMotion) {
    int res = secondMotion.ordinal() - firstMotion.ordinal();
    if (res == 2) {
      res = -1;
    } else if (res == -2) {
      res = 1;
    }
    return res;
  }

  /**
   * get winner Motion against current Motion
   *
   * @param motion - current motion
   * @return - winner motion
   */
  public static Motion getWinner(Motion motion) {
    switch (motion) {
      case STONE:
        return PAPER;
      case SCISSORS:
        return STONE;
      case PAPER:
        return SCISSORS;
      default:
        throw new RuntimeException("Unexpected input");
    }
  }
}
