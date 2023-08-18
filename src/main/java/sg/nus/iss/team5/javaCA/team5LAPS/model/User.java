package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User implements Serializable {
  
  private static final long serialVersionUID = 6529685098267757680L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userid")
  private int userId;
  
  @NotBlank(message = "{error.user.name.empty}")
  @Column(name = "name")
  private String name;
  
  @NotBlank(message = "{error.user.password.empty}")
  @Column(name = "password")
  private String password;
  
  @Column(name = "staffid")
  private int staffId;
  
  @Column(name="isActive")
 	private boolean isActive;

  @ManyToMany(targetEntity = Role.class, cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch=FetchType.EAGER)
  @JoinTable(name = "userrole", joinColumns = {
      @JoinColumn(name = "userid", referencedColumnName = "userid") }, inverseJoinColumns = {
          @JoinColumn(name = "roleid", referencedColumnName = "roleid") }
  )
  private List<Role> roleSet;

  public User() {}
  
  public User(int id,String name, String password, int staffId, boolean isActive) {
	    this.userId=id;
	    this.name = name;
	    this.password = password;
	    this.staffId = staffId;
	    this.isActive = isActive;
	  }

//  public User(int userId) {
//    this.userId = userId;
//  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getStaffId() {
    return staffId;
  }

  public void setStaffId(int staffId) {
    this.staffId = staffId;
  }
  
  public boolean getIsActive(){
		return isActive;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

  public List<Role> getRoleSet() {
    return roleSet;
  }

  public void setRoleSet(List<Role> roleSet) {
    this.roleSet = roleSet;
  }

  public List<String> getRoleIds() {
    List<String> retList = new ArrayList<>();
    roleSet.forEach(role -> retList.add(role.getRoleId()));
    
    return retList;
  }

}