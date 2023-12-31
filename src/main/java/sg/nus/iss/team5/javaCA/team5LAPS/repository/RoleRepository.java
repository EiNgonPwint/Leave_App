package sg.nus.iss.team5.javaCA.team5LAPS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;

public interface RoleRepository extends JpaRepository<Role, String>{
  @Query("SELECT r.name FROM Role r")
  List<String> findAllRolesNames();
  
  @Query("SELECT r FROM Role r WHERE r.name = :name")
  List<Role> findRoleByName(@Param("name") String name);
}
