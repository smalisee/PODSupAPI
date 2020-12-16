package com.amos.podsupapi.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amos.podsupapi.dto.ChannelDTO;
import com.amos.podsupapi.dto.OrderStatusDTO;
import com.amos.podsupapi.dto.ProdLineDTO;
import com.amos.podsupapi.dto.ProvinceDTO;
import com.amos.podsupapi.dto.VendorDTO;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class KeyServiceImpl implements KeyService {

  @PersistenceContext
  private EntityManager entityManager;

  // private static Logger logger = LogManager.getLogger(AutoFactoryPOController.class);

  private ChannelDTO cnn;

  @Override
  public List<ChannelDTO> getAllChannel() {
    String sqlSelect = " select I_VALUE, S_KEYWORD, S_SHORTNAME "
        + " from s_key where S_SUBTOPIC = 'iordersource' AND S_TOPIC = 'iordersource' order by I_VALUE ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<ChannelDTO> channList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    cnn = null;

    for (Object[] obj : dataList) {
      cnn = new ChannelDTO();

      cnn.setId(((BigDecimal) obj[0]).intValue());
      cnn.setKeyword(obj[1].toString());
      cnn.setShortName(obj[2].toString());

      channList.add(cnn);
    }

    return channList;
  }

  @Override
  public List<OrderStatusDTO> getAllOrderStatus() {
    String sqlSelect = " SELECT S_SUB_KEYWORD, S_VALUE FROM s_key_gosoft_det  WHERE S_KEYWORD = 'POD_SENDBYSUP_STATUS' ORDER BY I_SORTING ASC ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<OrderStatusDTO> channList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    OrderStatusDTO ordStat = null;

    for (Object[] obj : dataList) {
      ordStat = new OrderStatusDTO();

      ordStat.setKeyword(obj[0].toString());
      ordStat.setValue(obj[1].toString());

      channList.add(ordStat);
    }

    return channList;
  }

  @Override
  public List<VendorDTO> getAllVendor() {
    String sqlSelect = " select I_VENDOR,S_VENDORNAME , I_VENDOR|| ' - '||S_VENDORNAME AS MAP_VENDOR from p_vendor where c_vendortype='S' "
        +
        " and exists (select 1 from p_item where length(i_itemno)=6 " +
        " and c_itemprofile='F' and i_vendor=p_vendor.i_vendor) ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<VendorDTO> vendorList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    VendorDTO vend = null;

    for (Object[] obj : dataList) {
      vend = new VendorDTO();

      vend.setVendorNo(((BigDecimal) obj[0]).intValue());
      vend.setVendorName(obj[1].toString());
      vend.setMapVendor(obj[2].toString());
      vendorList.add(vend);
    }

    return vendorList;
  }

  @Override
  public List<ProdLineDTO> getAllProdline() {
    String sqlSelect = " SELECT DISTINCT i_prodline1, i_prodline3, to_char (i_prodline1 || ' - ' || i_prodline3) AS map_prodline "
        +
        "  FROM p_item WHERE c_itemprofile = 'F' AND LENGTH (i_itemno) = 6 ORDER BY i_prodline1 ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<ProdLineDTO> vendorList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    ProdLineDTO prod = null;

    for (Object[] obj : dataList) {
      prod = new ProdLineDTO();

      prod.setProd1(((BigDecimal) obj[0]).intValue());
      prod.setProd3(((BigDecimal) obj[1]).intValue());
      prod.setMapProd(obj[2].toString());

      vendorList.add(prod);
    }

    return vendorList;
  }

  @Override
  public List<OrderStatusDTO> getAllDeliveryBy() {
    String sqlSelect = " SELECT S_SUB_KEYWORD, S_VALUE FROM s_key_gosoft_det  WHERE S_KEYWORD = 'POD_SENDBYSUP_DELIVERY_BY' ORDER BY I_SORTING ASC ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<OrderStatusDTO> channList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    List<Object[]> dataList = query.getResultList();

    OrderStatusDTO ordStat = null;

    for (Object[] obj : dataList) {
      ordStat = new OrderStatusDTO();

      ordStat.setKeyword(obj[0].toString());
      ordStat.setValue(obj[1].toString());

      channList.add(ordStat);
    }

    return channList;
  }

  @Override
  public String getAllProvince() {
    String sqlSelect = " SELECT DISTINCT C.S_NAME_TH AS s_province_th, " +
        "                B.S_NAME_TH AS s_district, " +
        "                a.S_NAME_TH AS S_SUBDISTRICT, " +
        "                A.S_ZIP_CODE " +
        "  FROM P_SUBDISTRICT a, p_district b, P_PROVINCE_MASTER c" +
        " WHERE     A.I_DISTRINCT_ID = B.I_ID " +
        "       AND B.I_PROVINCE_ID = C.I_ID " +
        "       AND a.s_zip_code <> '0' " +
        "       order by C.S_NAME_TH ";

    Query query = entityManager.createNativeQuery(sqlSelect);

    List<ProvinceDTO> provinceList = new ArrayList<>();
    @SuppressWarnings("unchecked")

    List<Object[]> dataList = query.getResultList();

    String[][] arr = new String[dataList.size()][4];

    int j = 0;

    for (Object[] obj : dataList) {

      arr[j][0] = obj[0].toString();
      arr[j][1] = obj[1].toString();
      arr[j][2] = obj[2].toString();
      arr[j][3] = obj[3].toString();

      j++;
    }

    String returning = "";

    for (int i = 0; i < arr.length; i++) {

      if (i > 0) {
        if (arr[i][0].equals(arr[i - 1][0])) {
          if (arr[i][1].equals(arr[i - 1][1])) {
            returning += "],[" + "\"" + arr[i][2] + "\"," + arr[i][3] + "" + "";
          } else {
            returning += "]]],[\"" + arr[i][1] + "\",[[" + "\"" + arr[i][2] + "\"," + arr[i][3] + "" + "";
          }
        } else {
          if (arr[i][1].equals(arr[i - 1][1])) {
            returning += "\",[[\"" + arr[i][1] + "\",[[" + "\"" + arr[i][2] + "\"," + arr[i][3] + "" + "]]";
          } else {
            returning += "]]]]],[\"" + arr[i][0] + "\"," + "[[\"" + arr[i][1] + "\",[[" + "\"" + arr[i][2] + "\"," + arr[i][3]
                + "" + "";
          }
        }
      } else {
        returning += "[\"" + arr[i][0] + "\",[[\"" + arr[i][1] + "\",[[" + "\"" + arr[i][2] + "\"," + arr[i][3] + "";
      }
    }

    returning += "]]]]]";

    return "{\"data\":[" + returning + "]}";
  }

}
