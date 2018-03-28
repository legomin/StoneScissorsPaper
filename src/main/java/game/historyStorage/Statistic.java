package game.historyStorage;

import game.Motion;

/**
 * value class for storing statistics
 */
public class Statistic {

  private int stoneCount;
  private int scissorsCount;
  private int paperCount;

  public Statistic() {
    this.stoneCount = 0;
    this.scissorsCount = 0;
    this.paperCount = 0;
  }

  /**
   * measure of solidity of statistic
   *
   * @return statistic weight
   */
  public int getWeight() {
    return stoneCount + scissorsCount + paperCount;
  }

  /**
   * @return more probable next user motion
   */
  public Motion getSuggestedMotion() {
    final int maxCount = Math.max(Math.max(stoneCount, scissorsCount), paperCount);
    if (maxCount == stoneCount) {
      return Motion.STONE;
    } else if (maxCount == scissorsCount) {
      return Motion.SCISSORS;
    } else {
      return Motion.PAPER;
    }
  }

  /**
   * @return probability of next user motion
   */
  public double getSuggestedMotionProbability() {
    int weight = getWeight();
    if (weight == 0) {
      return 0;
    } else {
      return Math.max(Math.max(stoneCount, scissorsCount), paperCount) / getWeight();
    }
  }

  /**
   * update stats with new data
   *
   * @param motion - new user motion
   */
  public void updateStat(Motion motion) {
    switch (motion) {
      case STONE:
        stoneCount++;
        break;
      case SCISSORS:
        scissorsCount++;
        break;
      case PAPER:
        paperCount++;
        break;
      default:
        throw new RuntimeException("invalid index");
    }
  }
}
