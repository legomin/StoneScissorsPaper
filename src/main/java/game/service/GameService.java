package game.service;

import game.Motion;

/**
 * computer motions provider interface
 *
 * Suggested that learning process occurs into one game only, between game laps
 */
public interface GameService {

  /**
   * requires userMotion cause save user data & motion producing should be done in one atomic operation
   *
   * @param userMotion - user motion
   * @return produced motion
   */
  Motion getNextMotion(Motion userMotion);

  /**
   * initialize state for new game (learning process)
   */
  void initialize();

}
