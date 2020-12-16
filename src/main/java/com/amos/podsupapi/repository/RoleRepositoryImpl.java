package com.amos.podsupapi.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.model.User;

@Transactional
@Repository
public class RoleRepositoryImpl implements RoleRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<Role> getAllRoles() {
    String hql = "FROM Role as role ORDER BY role.id";
    return entityManager.createQuery(hql, Role.class).getResultList();
  }

  @Override
  public List<Role> getAllRoles(Role filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Role> query = builder.createQuery(Role.class);
    Root<Role> root = query.from(Role.class);
    List<Predicate> predicates = new ArrayList<>();

    if (!CommonUtils.isNullOrEmpty(filter.getName())) {
      predicates.add(builder.like(builder.upper(root.get("name")), filter.getName().toUpperCase() + "%"));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getStatus())) {
      predicates.add(builder.equal(root.get("status"), filter.getStatus()));
    }
    query.select(root).where(predicates.toArray(new Predicate[] {}));
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public List<User> getAllUsersByRole(int roleId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Role getRoleById(int roleId) {
    return entityManager.find(Role.class, roleId);
  }

  @Override
  public void addRole(Role role) {
    entityManager.persist(role);
  }

  @Override
  public void updateRole(Role role) {
    Role r = getRoleById(role.getId());
    r.setName(role.getName());
    r.setStatus(role.getStatus());
    r.setMenus(role.getMenus());
    entityManager.flush();
  }

  @Override
  public void deleteRole(Role role) {
    entityManager.remove(role);
  }

  @Override
  public boolean roleExists(String rolename) {
    String hql = "FROM Role as rl WHERE rl.name = :name";
    int count = entityManager.createQuery(hql).setParameter("name", rolename).getResultList().size();
    return count > 0;
  }

}
