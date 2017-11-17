package org.javers.mongo.javersmongoproblem.domain;

import com.google.common.collect.Sets;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Document
public class Permission extends AbstractPermission implements GrantedAuthority {

	private Set<String> inclusions = Sets.newHashSet("firstName");
	private Set<String> exclusions = Sets.newHashSet();

	public Permission(String name) {
		super(name);
	}

	@Override
	public String getAuthority() {
		return name;
	}

	public Set<String> getInclusions() {
		return inclusions;
	}

	public void setInclusions(Set<String> inclusions) {
		this.inclusions = inclusions;
	}

	public Set<String> getExclusions() {
		return exclusions;
	}

	public void setExclusions(Set<String> exclusions) {
		this.exclusions = exclusions;
	}

	@Override
	public String toString() {
		return "Permission{" +
				"id='" + id + '\'' +
				", inclusions=" + inclusions +
				", exclusions=" + exclusions +
				", authority='" + getAuthority() + '\'' +
				'}';
	}
}
