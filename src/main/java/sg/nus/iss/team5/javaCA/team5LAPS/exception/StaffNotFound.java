package sg.nus.iss.team5.javaCA.team5LAPS.exception;

public class StaffNotFound extends Exception{
  private static final long serialVersionUID = 1L;
  
  public StaffNotFound() {}
  
  public StaffNotFound(String msg) {
    super(msg);
  }
}