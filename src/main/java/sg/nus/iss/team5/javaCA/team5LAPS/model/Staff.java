package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "staff")
public class Staff implements Serializable {
  private static final long serialVersionUID = 6529685098267757670L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "staffid")
  private int staffId;
  
  private String name;
  
  @Column(name = "managerid")
  private int managerId;
  
//  @Column(name="isActive")
//	private boolean isActive;
  
  @Column(name= "email")
  private String email;
  
  
  public Staff() { }
  
  public Staff(String name) {
	    this(name,0, null);
	  }

	  public Staff(String name, int managerId, String email) {
	    
	    this.name = name;
	    this.managerId = managerId;
//	    this.isActive = isActive;
	    this.email = email;
	  }
  
  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getManagerId() {
    return managerId;
  }

  public void setManagerId(int managerId) {
    this.managerId = managerId;
  }
  
//  public boolean getIsActive(){
//		return isActive;
//	}
//
//	public void setIsActive(boolean isActive){
//		this.isActive = isActive;
//	}

  public String getEmail(){
    return email;
  }

  public void setEmail(String email){
    this.email = email;

}
}