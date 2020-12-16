package com.amos.podsupapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.RoleDTO;
import com.amos.podsupapi.model.Role;
import com.amos.podsupapi.services.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	private static Logger logger = LogManager.getLogger(RoleController.class);
		
	@CrossOrigin
	@GetMapping(value= "/get_all_roles",produces={"application/json; charset=UTF-8"})
	public ResponseEntity<APIResultDTO> getAllRoles(
			@RequestParam(name="name", required=false) String name,
			@RequestParam(name="status", required=false) String status
			) {
		List<RoleDTO> dto = null;
		ReturnCode code = ReturnCode.SUCCESS;
		try {
			Role filter = new Role();
			filter.setName(name);
			filter.setStatus(status);
			dto = roleService.getAllRoles(filter);
		}  catch (Exception ex) {
			code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
		}
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code), dto), HttpStatus.OK);
	}
	
	@CrossOrigin
	@GetMapping(value= "/get_role_by_id",produces={"application/json; charset=UTF-8"})
	public ResponseEntity<APIResultDTO> getRoleById(@RequestParam(name="id", required=true) int id) {
		
		ReturnCode code = ReturnCode.SUCCESS;
		RoleDTO dto = null;
		
		try {
			dto = roleService.getRoleById(id);
		}  catch (Exception ex) {
			code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
		}
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code), dto), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value= "/update_role", consumes= {"application/json"}, produces={"application/json; charset=UTF-8"})
	public ResponseEntity<APIResultDTO> addOrUpdateRole(@RequestBody String payload){
		
		ReturnCode code = ReturnCode.SUCCESS;
		
		try {
			code = roleService.addOrUpdateRole(payload);
		}  catch (Exception ex) {
			code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
			logger.error(String.format("Add Role ERROR. payload: %s", payload), ex);
			System.out.println(ex);
		}
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code)), HttpStatus.OK);
	}
	
}
