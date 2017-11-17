package org.javers.mongo.javersmongoproblem.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class Permission extends AbstractPermission implements GrantedAuthority {

	public Permission(String name) {
		super(name);
	}

	@Override
	public String getAuthority() {
		return name;
	}

	@Override
	public String toString() {
		return getAuthority();
	}
}
