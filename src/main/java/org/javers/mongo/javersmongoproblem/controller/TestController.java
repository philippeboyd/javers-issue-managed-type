package org.javers.mongo.javersmongoproblem.controller;


import com.google.common.collect.Lists;
import org.javers.core.Javers;
import org.javers.mongo.javersmongoproblem.domain.CoreOperation;
import org.javers.mongo.javersmongoproblem.domain.Permission;
import org.javers.mongo.javersmongoproblem.domain.Role;
import org.javers.mongo.javersmongoproblem.repository.PermissionRepository;
import org.javers.mongo.javersmongoproblem.repository.RoleRepository;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class TestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

	private final RoleRepository roleRepository;
	private final PermissionRepository permissionRepository;
	private final Javers javers;

	public TestController(RoleRepository roleRepository, PermissionRepository permissionRepository, Javers javers) {
		this.roleRepository = roleRepository;
		this.permissionRepository = permissionRepository;
		this.javers = javers;
	}


	@PostConstruct
	private void createTestUsers() {

		for (CoreOperation coreOperation : CoreOperation.values()) {
			permissionRepository.save(new Permission(coreOperation.name() + "_USER"));
		}

		permissionRepository.findAll().forEach(System.out::println);

		Permission permissionReadUser = permissionRepository.findByName("READ_USER");
		Permission permissionViewUser = permissionRepository.findByName("VIEW_USER");
		Permission permissionCountUser = permissionRepository.findByName("COUNT_USER");
		Permission permissionExistsUser = permissionRepository.findByName("EXISTS_USER");
		Permission permissionCreateUser = permissionRepository.findByName("CREATE_USER");
		Permission permissionUpdateUser = permissionRepository.findByName("UPDATE_USER");
		Permission permissionDisableUser = permissionRepository.findByName("DISABLE_USER");
		Permission permissionDeleteUser = permissionRepository.findByName("DELETE_USER");

		// ========================================================


		Role roleAdmin = new Role("ADMIN");
		roleAdmin.setPermissions(Lists.newArrayList(permissionReadUser, permissionViewUser, permissionCountUser, permissionExistsUser, permissionCreateUser));
		roleAdmin = roleRepository.save(roleAdmin); // First version of role admin (5 permissions)

		roleAdmin.setDescription("Admin role :D");
		roleAdmin = roleRepository.save(roleAdmin); // Second version of role admin (5 permissions + description)

		roleAdmin.getPermissions().add(permissionUpdateUser);
		roleAdmin.getPermissions().add(permissionDisableUser);
		roleAdmin.getPermissions().add(permissionDeleteUser);
		roleAdmin = roleRepository.save(roleAdmin); // Third version of role admin (8 permissions + description)


		System.out.println("roleAdmin permission size : " + roleAdmin.getPermissions().size());

		JqlQuery jqlQuery = QueryBuilder.byInstanceId(roleAdmin.getId(), Role.class)
				.withChildValueObjects()
//				.withScopeCommitDeep()
				.withScopeDeepPlus()
				.build();

		List<Shadow<Role>> shadows = javers.findShadows(jqlQuery);

		shadows.forEach(e -> {
			System.out.println(e.get());
			System.out.println("SNAPSHOT roleAdmin permission size : " + e.get().getPermissions().size());
		});
	}
}
