package com.amos.podsupapi.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.common.CommonJWT;
import com.amos.podsupapi.common.JWTResultDTO;
import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.MenuDTO;
import com.amos.podsupapi.dto.admin.MenuSubDTO;
import com.amos.podsupapi.model.Menu;
import com.amos.podsupapi.repository.MenuRepository;

@RestController
public class PermissionController {
	
	private static final Logger logger = LogManager.getLogger(PermissionController.class);
	
	@Autowired
	private MenuRepository menuDAO;

	@CrossOrigin
	@RequestMapping(value= "/get_permission", produces={"application/json; charset=UTF-8"})
	public ResponseEntity<APIResultDTO> getLoginPermissionMenu(@RequestHeader(name="Authorization") String auth){
		logger.debug("get Permission for :" + auth);
		JWTResultDTO result = CommonJWT.jwtTokenVerify(auth.replace("Bearer ", ""));		
		
		if(result.isVerify()) {
			//Get role id from token
			List<Integer> roleIdList = new ArrayList<>();
			for(String roleIdStr:result.getAudience()) {
				roleIdList.add(Integer.parseInt(roleIdStr));
			}
			
			//Get menu from role id
			List<MenuDTO> dto = new ArrayList<>();
			for (Menu source: menuDAO.getMenuByPermission(roleIdList)) {
				List<MenuSubDTO> sList = new ArrayList<>();
				for(Menu sub : source.getSubMenues()) {
					MenuSubDTO st = new MenuSubDTO();
					BeanUtils.copyProperties(sub, st);
					sList.add(st);
				}
				MenuDTO target= new MenuDTO();
		        BeanUtils.copyProperties(source , target);
		        target.setSubMenues(sList);
		        dto.add(target);
		     }
			
			return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), dto), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.UNAUTHORIZED)), HttpStatus.OK);
		}
	}
}
