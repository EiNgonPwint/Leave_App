package sg.nus.iss.team5.javaCA.team5LAPS.exception;

public class UserNotFound extends Exception {
  private static final long serialVersionUID = 1L;
  
  public UserNotFound() {}
  
  public UserNotFound(String msg) {
    super(msg);
  }
}