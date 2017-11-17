package org.javers.mongo.javersmongoproblem.controller;


import com.google.common.collect.Sets;
import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.mongo.javersmongoproblem.domain.CoreOperation;
import org.javers.mongo.javersmongoproblem.domain.Permission;
import org.javers.mongo.javersmongoproblem.domain.Role;
import org.javers.mongo.javersmongoproblem.repository.PermissionRepository;
import org.javers.mongo.javersmongoproblem.repository.RoleRepository;
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

		Role roleSuperAdmin = new Role("SUPER_ADMIN");
		roleSuperAdmin = roleRepository.save(roleSuperAdmin);

		for (CoreOperation coreOperation : CoreOperation.values()) {
			Permission permission = permissionRepository.save(new Permission(coreOperation.name() + "_USER"));
			roleSuperAdmin.getPermissions().add(permission);
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
		roleAdmin.setPermissions(Sets.newHashSet(permissionReadUser, permissionViewUser, permissionCountUser, permissionExistsUser, permissionCreateUser, permissionUpdateUser));

		Role roleUser = new Role("USER");

		roleSuperAdmin = roleRepository.save(roleSuperAdmin);
		roleAdmin = roleRepository.save(roleAdmin);
		roleUser = roleRepository.save(roleUser);

		roleAdmin.getPermissions().add(permissionDisableUser);
		roleAdmin.getPermissions().add(permissionDeleteUser);
		roleAdmin = roleRepository.save(roleAdmin);

		System.out.println(roleSuperAdmin);

		QueryBuilder jqlQuery = QueryBuilder.byInstanceId(roleSuperAdmin.getId(), Role.class);

		List<Change> changes = javers.findChanges(jqlQuery.build());
		List<Shadow<Role>> shadows = javers.findShadows(jqlQuery.build());
		List<CdoSnapshot> snapshots = javers.findSnapshots(jqlQuery.build());

		System.out.println("-----------------------------------------------------------------------------------------------------");
		changes.forEach(e -> System.out.println(e));
		System.out.println("-----------------------------------------------------------------------------------------------------");
		shadows.forEach(e -> System.out.println(e.get()));
		System.out.println("-----------------------------------------------------------------------------------------------------");
		snapshots.forEach(e -> System.out.println(e.getChanged()));
		System.out.println("-----------------------------------------------------------------------------------------------------");

		System.out.println(javers.getJsonConverter().toJson(changes));



		QueryBuilder jqlQuery2 = QueryBuilder.byInstanceId(permissionReadUser.getId(), Permission.class);

		List<Change> changes2 = javers.findChanges(jqlQuery2.build());
		List<Shadow<Role>> shadows2 = javers.findShadows(jqlQuery2.build());
		List<CdoSnapshot> snapshots2 = javers.findSnapshots(jqlQuery2.build());

		System.out.println("-----------------------------------------------------------------------------------------------------");
		changes2.forEach(e -> System.out.println(e));
		System.out.println("-----------------------------------------------------------------------------------------------------");
		shadows2.forEach(e -> System.out.println(e.get()));
		System.out.println("-----------------------------------------------------------------------------------------------------");
		snapshots2.forEach(e -> System.out.println(e.getChanged()));
		System.out.println("-----------------------------------------------------------------------------------------------------");

		System.out.println(javers.getJsonConverter().toJson(changes2));
	}
}
