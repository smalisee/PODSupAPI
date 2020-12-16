package com.amos.podsupapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.model.Key;

@Transactional
@Repository
public class KeyRepositoryImpl implements KeyRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @PersistenceContext
  EntityManager session;

  @Override
  public List<Key> getAllCompany() {
    String hql = "FROM Key WHERE pk.item='TRANSPORT_COMPANY_NAME' ";
    return entityManager.createQuery(hql, Key.class).getResultList();
  }

}
