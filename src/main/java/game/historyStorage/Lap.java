package game.historyStorage;

import java.util.Objects;

import game.Motion;

/**
 * Just pair of user motion & comp motion
 */
public class Lap {
  private Motion userMotion;
  private Motion computerMotion;

  public Lap(Motion userMotion, Motion computerMotion) {
    this.userMotion = userMotion;
    this.computerMotion = computerMotion;
  }

  public Motion getUserMotion() {
    return userMotion;
  }

  public Motion getComputerMotion() {
    return computerMotion;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Lap lap = (Lap) o;
    return getUserMotion() == lap.getUserMotion() && getComputerMotion() == lap.getComputerMotion();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUserMotion(), getComputerMotion());
  }
}
