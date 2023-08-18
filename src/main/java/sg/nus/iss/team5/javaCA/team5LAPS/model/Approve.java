package sg.nus.iss.team5.javaCA.team5LAPS.model;

import java.util.Objects;

public class Approve {
  private String decision;
  private String comment;
  
  public Approve() {}
  
  public Approve(String decision, String comment) {
    this.decision = decision;
    this.setComment(comment);
  }

  public String getDecision() {
    return decision;
  }

  public void setDecision(String decision) {
    this.decision = decision;
  }

  @Override
  public String toString() {
    return "Approve [decision=" + decision + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(decision);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Approve other = (Approve) obj;
    return Objects.equals(decision, other.decision);
  }

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

  
}