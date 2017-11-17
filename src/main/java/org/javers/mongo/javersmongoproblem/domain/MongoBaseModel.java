package org.javers.mongo.javersmongoproblem.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.io.Serializable;
import java.util.Objects;

public abstract class MongoBaseModel<ID extends Serializable & Comparable<ID>> implements Comparable<MongoBaseModel<ID>> {

	@Version
	protected int version = 1;

	@Id
	protected ID id;

	public final ID getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MongoBaseModel)) return false;
		MongoBaseModel that = (MongoBaseModel) o;
		return Objects.equals(getId(), that.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public int compareTo(MongoBaseModel<ID> o) {
		return getId().compareTo(o.getId());
	}

}
