package game;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import game.historyStorage.inMemoryImpl.InMemoryLapHistoryImpl;
import game.service.impl.FixedPatternDepthGameServiceImpl;
import game.service.impl.motionStrategies.MostProbablePatternStrategy;

public class GameTest {

  private Game game;

  @Before
  public void setUp() {
    game = new Game(
      new FixedPatternDepthGameServiceImpl(5, new MostProbablePatternStrategy(), new InMemoryLapHistoryImpl())
    );
  }

  /**
   * STONE, SCISSORS, PAPER, ......
   */
  @Test
  public void mostProbableMotionTest1() {
    abstractTest(i -> Motion.values()[i % 3], 90);
  }

  /**
   * STONE, STONE, SCISSORS, PAPER, ......
   */
  @Test
  public void mostProbableMotionTest2() {
    abstractTest(i -> {
      final int index = i % 4;
      final Motion currentUserMotion;
      if (index == 0 || index == 1) {
        currentUserMotion = Motion.STONE;
      } else if (index == 2) {
        currentUserMotion = Motion.SCISSORS;
      } else {
        currentUserMotion = Motion.PAPER;
      }
      return currentUserMotion;
    }, 85);
  }

  /**
   * STONE, STONE, STONE, SCISSORS, PAPER, ......
   */
  @Test
  public void mostProbableMotionTest3() {
    abstractTest(i -> {
      final int index = i % 5;
      final Motion currentUserMotion;
      if (index == 0 || index == 1 || index == 2) {
        currentUserMotion = Motion.STONE;
      } else if (index == 3) {
        currentUserMotion = Motion.SCISSORS;
      } else {
        currentUserMotion = Motion.PAPER;
      }
      return currentUserMotion;
    }, 85);
  }

  /**
   * STONE, SCISSORS, STONE, PAPER, SCISSORS......
   */
  @Test
  public void mostProbableMotionTest4() {
    abstractTest(i -> {
      final int index = i % 5;
      final Motion currentUserMotion;
      if (index == 0 || index == 2) {
        currentUserMotion = Motion.STONE;
      } else if (index == 1 || index == 4) {
        currentUserMotion = Motion.SCISSORS;
      } else {
        currentUserMotion = Motion.PAPER;
      }
      return currentUserMotion;
    }, 85);
  }

  /**
   * STONE, STONE, STONE, SCISSORS, SCISSORS, SCISSORS, STONE, PAPER, SCISSORS, PAPER......
   *
   * Yep. Here it learning slow enough
   */
  @Test
  public void mostProbableMotionTest5() {
    abstractTest(i -> {
      final int index = i % 10;
      final Motion currentUserMotion;
      if (index < 3 || index == 7) {
        currentUserMotion = Motion.STONE;
      } else if (index < 5 || index == 8) {
        currentUserMotion = Motion.SCISSORS;
      } else {
        currentUserMotion = Motion.PAPER;
      }
      return currentUserMotion;
    }, 75);
  }

  /**
   * More smart. If it wins - the same motion, else changes
   */
  @Test
  public void mostProbableMotionTest6() {
    game.start();
    Game.GameLapResult previousResult = null;
    Motion currentUserMotion = Motion.STONE;
    for (int i = 0; i < 1000; i++) {
      if (previousResult == Game.GameLapResult.DRAW || previousResult == Game.GameLapResult.COMPUTER_WIN) {
        currentUserMotion = Motion.values()[(currentUserMotion.ordinal() + 1) % 3];
      }
      previousResult = game.lap(currentUserMotion);
    }
    final List<Game.GameLapResult> results = game.finish();
    final double resultSuccessPercent = getSuccessPercentage(results);
    System.out.println(resultSuccessPercent);
    assertTrue("It doesn't learn", resultSuccessPercent > 90);
  }

  private void abstractTest(final Function<Integer, Motion> motionStrategy, final float successPercent) {
    game.start();
    for (int i = 0; i < 1000; i++) {
      game.lap(motionStrategy.apply(i));
    }
    final List<Game.GameLapResult> results = game.finish();
    final double resultSuccessPercent = getSuccessPercentage(results);
    System.out.println(resultSuccessPercent);
    assertTrue("It doesn't learn", resultSuccessPercent > successPercent);
  }

  private double getSuccessPercentage(List<Game.GameLapResult> results) {
    final Map<Game.GameLapResult, Long> stat = results.stream()
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    return (stat.get(Game.GameLapResult.COMPUTER_WIN) / (double)results.size()) * 100;
  }

}