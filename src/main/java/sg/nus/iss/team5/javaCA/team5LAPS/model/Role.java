package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {
  private static final long serialVersionUID = 6529685098267757690L;
  
  @Id
  @Column(name = "roleid")
  private String roleId;
  
  @Column(name = "name")
  private String name;
  
  @Column(name = "description")
  private String description;
  
  @Column(name="isActive")
	private boolean isActive;
  
  public Role() {}
  
  public Role(String roleId) {
    this.roleId = roleId;
  }
  
  public Role(String roleId, String name, String description, boolean isActive) {
	    this.roleId = roleId;
	    this.name = name;
	    this.description = description;
	    this.isActive = isActive;
	  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
  public boolean getIsActive(){
		return isActive;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

  @Override
  public int hashCode() {
    return Objects.hash(roleId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Role other = (Role) obj;
    return Objects.equals(roleId, other.roleId);
  }
}