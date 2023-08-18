package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;

public interface RoleService {
	List<Role> findAllRoles();
	Role findRole(String roleId);
	Role createRole(Role role);
	Role changeRole(Role role);
	void removeRole(Role role);
	List<String> findAllRolesNames();
	List<Role> findRoleByName(String name);
	
	Role save(Role role);
}
