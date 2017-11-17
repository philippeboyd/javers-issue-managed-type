package org.javers.mongo.javersmongoproblem.repository;

import org.javers.mongo.javersmongoproblem.domain.AbstractPermission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractPermissionRepository<T extends AbstractPermission> extends MongoRepository<T, String> {

	T findByName(String name);
}
