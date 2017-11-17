package org.javers.mongo.javersmongoproblem.domain;

import com.google.common.collect.Lists;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Role extends AbstractPermission {

	@DBRef
	private List<Permission> permissions;

	public Role(String name) {
		this(name, new Permission[]{});
	}

	public Role(String name, Permission... permissions) {
		super(name);
		setPermissions(Lists.newArrayList(permissions));
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
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
