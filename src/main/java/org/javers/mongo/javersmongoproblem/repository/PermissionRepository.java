package org.javers.mongo.javersmongoproblem.repository;

import org.javers.mongo.javersmongoproblem.domain.Permission;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface PermissionRepository extends AbstractPermissionRepository<Permission> {

}
