package sg.nus.iss.team5.javaCA.team5LAPS.exception;

public class LeaveNotFound extends Exception {
  private static final long serialVersionUID = 1L;
  
  public LeaveNotFound() {}
  
  public LeaveNotFound(String msg) {
    super(msg);
  }
}