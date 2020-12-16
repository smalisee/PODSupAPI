package com.amos.podsupapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.model.AutoFactoryPO;
import com.amos.podsupapi.model.PODFile;

@Repository
public class AutoFactoryPORepositoryImpl implements AutoFactoryPORepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<AutoFactoryPO> getAutoFactoryPO(int pono) {
		String hql = "FROM AutoFactoryPO as autoFactoryPO where autoFactoryPO.pono = :pono";
		return entityManager.createQuery(hql, AutoFactoryPO.class).setParameter("pono", pono).getResultList();
	}

	@Override
	public List<AutoFactoryPO> getAllAutoPO(int pono, int order) {

		String hql = "FROM AutoFactoryPO as autoFactoryPO where autoFactoryPO.pono = :pono and autoFactoryPO.order = :order";
		return entityManager.createQuery(hql, AutoFactoryPO.class).setParameter("pono", pono)
				.setParameter("order", order).getResultList();

	}

	@Override
	public boolean autoPOExists(int pono) {
		String hql = "FROM AutoFactoryPO as autoFactoryPO WHERE autoFactoryPO.pono = :pono";
		int count = entityManager.createQuery(hql).setParameter("pono", pono).getResultList().size();
		return count > 0;
	}

	@Override
	public AutoFactoryPO getAutoPOByPOno(int pono) {
		String hql = "FROM AutoFactoryPO as autoFactoryPO WHERE autoFactoryPO.pono = :pono";
		List<AutoFactoryPO> autoPOL = entityManager.createQuery(hql, AutoFactoryPO.class).setParameter("pono", pono)
				.getResultList();
		return (!autoPOL.isEmpty()) ? autoPOL.get(0) : null;
	}

	@Override
	public boolean autoPOCheckStatus(int order) {
		String hql = "FROM AutoFactoryPO as autoFactoryPO WHERE autoFactoryPO.status in ('G','D') and autoFactoryPO.order = :order";
		int count = entityManager.createQuery(hql).setParameter("order", order).getResultList().size();
		return (count > 0) ? true : false;
	}

	@Override
	public PODFile getFileByKey(String poNo, int imageId) {
		String sql = "select fil from PODFile fil where fil.i_file = :imageId and fil.poNo = :poNo ";

		int iPono = Integer.parseInt(poNo);
		
		return entityManager.createQuery(sql, PODFile.class)
				.setParameter("poNo", iPono)
				.setParameter("imageId", imageId)
			.getSingleResult();
	}
}
