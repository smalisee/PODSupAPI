package com.amos.podsupapi.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.common.CommonUtils;
import com.amos.podsupapi.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<User> getAllUsers() {
    String hql = "FROM User as user ORDER BY user.id";
    return entityManager.createQuery(hql, User.class).getResultList();
  }

  @Override
  public List<User> getAllUsers(User filter) {
    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = builder.createQuery(User.class);
    Root<User> root = query.from(User.class);

    List<Predicate> predicates = new ArrayList<>();

    if (!CommonUtils.isNullOrEmpty(filter.getUsername())) {
      predicates.add(builder.like(builder.lower(root.get("username")), filter.getUsername().toLowerCase() + "%"));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getType())) {
      predicates.add(builder.equal(root.get("type"), filter.getType()));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getName())) {
      predicates.add(builder.like(builder.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getEmail())) {
      predicates.add(builder.like(root.get("email"), filter.getEmail() + "%"));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getPhoneNo())) {
      predicates.add(builder.like(root.get("phoneNo"), filter.getPhoneNo() + "%"));
    }
    if (!CommonUtils.isNullOrEmpty(filter.getStatus())) {
      predicates.add(builder.equal(root.get("status"), filter.getStatus()));
    }
    if (filter.getRoles().size() > 0) {
      predicates.add(builder.equal(root.join("roles"), filter.getRoles()));
    }
    query.select(root).where(predicates.toArray(new Predicate[] {}));
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public User getUserById(int userId) {
    return entityManager.find(User.class, userId);
  }

  @Override
  public void addUser(User user) {
    entityManager.persist(user);
  }

  @Override
  public void updateUser(User user) {
    User usrcl = getUserById(user.getId());
    usrcl.setName(user.getName());
    usrcl.setPassword(user.getPassword());
    usrcl.setStatus(user.getStatus());
    usrcl.setPhoneNo(user.getPhoneNo());
    usrcl.setRoles(user.getRoles());
    usrcl.setProdlines(user.getProdlines());
    usrcl.setVendorNo(user.getVendorNo());
    usrcl.setType(user.getType());
    usrcl.setUsername(user.getUsername());
    entityManager.flush();
    // entityManager.persist(new User());
  }

  @Override
  public User getUserByEmail(String email) {
    String hql = "FROM User as usrl WHERE usrl.email = :email";
    List<User> usrL = entityManager.createQuery(hql, User.class).setParameter("email", email).getResultList();
    return (!usrL.isEmpty()) ? usrL.get(0) : null;
  }

  @Override
  public User getUserByUsername(String username) {
    String hql = "FROM User as usrl WHERE usrl.username = :username";
    List<User> usrL = entityManager.createQuery(hql, User.class).setParameter("username", username).getResultList();
    return (!usrL.isEmpty()) ? usrL.get(0) : null;
  }

  @Override
  public boolean userExists(String username) {
    String hql = "FROM User as usrl WHERE usrl.username = :username";
    int count = entityManager.createQuery(hql).setParameter("username", username).getResultList().size();
    return count > 0;
  }

  @Override
  public void updatePassword(User usr, String hashPassword_New) {
    // TODO Auto-generated method stub
    usr.setPassword(hashPassword_New);
    entityManager.flush();
  }
}
