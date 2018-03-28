package game;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import game.service.GameService;

/**
 * the main game class
 *
 * Should be initialized with computer motions provider (GameService)
 *
 * The game is consist of start game, then some laps, and finish after
 * class gathers statistics of user wins/loses between start & finish and returns that in `finish` function
 */
public class Game {

  public enum GameLapResult {
    USER_WIN,
    COMPUTER_WIN,
    DRAW
  }

  private final GameService gameService;

  private boolean active = false;
  private List<GameLapResult> userGameStats;

  public Game(final GameService gameService) {
    this.gameService = requireNonNull(gameService);
  }

  /**
   * starts the game
   */
  public void start() {
    if (!active) {
      gameService.initialize();
      userGameStats = new ArrayList<>();
      active = true;
      System.out.println("game.Game is started");
    } else {
      System.err.println("game.Game is started already");
    }
  }

  /**
   * finishes the game
   *
   * @return user wins/loses statistics
   * @throws RuntimeException when game is not active
   */
  public List<GameLapResult> finish() {
    if (active) {
      active = false;
      System.out.println("game.Game is finished");
      return userGameStats;
    } else {
      System.err.println("Game is not active");
      throw new RuntimeException("Game is not active");
    }
  }

  /**
   * evaluates next game lap
   *
   * @param userMotion - user motion
   */
  public GameLapResult lap(final Motion userMotion) {
    final Motion computerMotion = gameService.getNextMotion(userMotion);
    int userResult = Motion.compare(userMotion, computerMotion);
    switch (userResult) {
      case -1:
        userGameStats.add(GameLapResult.COMPUTER_WIN);
        System.out.println("computer win");
        return GameLapResult.COMPUTER_WIN;
      case 0:
        userGameStats.add(GameLapResult.DRAW);
        System.out.println("draw");
        return GameLapResult.DRAW;
      case 1:
        userGameStats.add(GameLapResult.USER_WIN);
        System.out.println("user win");
        return GameLapResult.USER_WIN;
      default:
        System.out.println("Unexpected round result");
        throw new RuntimeException("Unexpected round result");
    }
  }

}
