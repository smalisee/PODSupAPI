package com.amos.podsupapi.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.model.ProductLine;

@Transactional
@Repository
public class ProductlineRepositoryImpl implements ProductlineRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public ProductLine getProdlineById(int prod1, int prod3) {

    ProductLine prodAll = new ProductLine();
    prodAll.setProdline1(prod1);
    prodAll.setProdline3(prod3);

    return entityManager.find(ProductLine.class, prodAll);
  }

  @Override
  public List<ProductLine> getProdlineByUserId(int UserId) {
    String sql = " select i_prodline1,i_prodline3 from webapp.w05_p_user_prodline a where i_user_id = :userId order by i_prodline1";
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("userId", UserId);

    List<ProductLine> prodList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    ProductLine prod = null;

    for (Object[] obj : dataList) {
      prod = new ProductLine();

      prod.setProdline1(((BigDecimal) obj[0]).intValue());
      prod.setProdline3(((BigDecimal) obj[1]).intValue());
      prod.setMapProd(prod.getProdline3() + " - " + prod.getProdline1());

      prodList.add(prod);
    }

    return prodList;
  }

}
