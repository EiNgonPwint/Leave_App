package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.util.List;

public class UserSession {
  private User user;
  private Staff staff;
  private List<Staff> subordinates;
  
  public UserSession() {}
  
  public UserSession(User user, Staff staff, List<Staff> subordinates) {
    this.user = user;
    this.staff = staff;
    this.subordinates = subordinates;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Staff getStaff() {
    return staff;
  }

  public void setStaff(Staff staff) {
    this.staff = staff;
  }

  public List<Staff> getSubordinates() {
    return subordinates;
  }

  public void setSubordinates(List<Staff> subordinates) {
    this.subordinates = subordinates;
  }
  
  
}