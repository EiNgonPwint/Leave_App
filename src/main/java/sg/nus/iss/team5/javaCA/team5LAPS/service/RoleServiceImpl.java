package sg.nus.iss.team5.javaCA.team5LAPS.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.nus.iss.team5.javaCA.team5LAPS.model.Role;
import sg.nus.iss.team5.javaCA.team5LAPS.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Resource
	private RoleRepository roleRepository;
	
	@Transactional
	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Transactional
	@Override
	public Role findRole(String roleId) {
		return roleRepository.findById(roleId).orElse(null);
	}

	@Transactional
	@Override
	public Role createRole(Role role) {
		role.setIsActive(true);
		return roleRepository.saveAndFlush(role);
	}

	@Transactional
	@Override
	public Role changeRole(Role role) {
		role.setIsActive(true);
		return roleRepository.saveAndFlush(role);
	}

	@Transactional
	@Override
	public void removeRole(Role role) {
		boolean status = role.getIsActive();
		if (status == false){
			role.setIsActive(true);
		}
		else {
			role.setIsActive(false);
		}
		roleRepository.saveAndFlush(role);
		//roleRepository.delete(role);
	}

	@Transactional
	@Override
	public List<String> findAllRolesNames() {
		return roleRepository.findAllRolesNames();
	}

	@Transactional
	@Override
	public List<Role> findRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}
	
	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	
}