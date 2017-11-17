package org.javers.mongo.javersmongoproblem.domain;

import com.google.common.collect.Sets;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class Role extends AbstractPermission {

	@DBRef
	private Set<Permission> permissions;

	public Role(String name) {
		this(name, new Permission[]{});
	}

	public Role(String name, Permission... permissions) {
		super(name);
		setPermissions(Sets.newHashSet(permissions));
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Role{" +
				"permissions=" + permissions +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
