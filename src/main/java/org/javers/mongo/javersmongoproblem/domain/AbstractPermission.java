package org.javers.mongo.javersmongoproblem.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

public abstract class AbstractPermission extends MongoBaseModel<String> implements Serializable {

	@Indexed(unique = true)
	protected String name;

	protected String description;

	protected AbstractPermission(String name) {
		Validate.isTrue(StringUtils.isNotBlank(name), "A granted authority textual representation is required");
		this.name = name.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(MongoBaseModel<String> o) {
		return name.compareTo(((AbstractPermission) o).name);
	}

}
