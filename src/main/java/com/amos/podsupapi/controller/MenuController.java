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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.dto.APIResultDTO;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.amos.podsupapi.dto.admin.MenuSubDTO;
import com.amos.podsupapi.model.Menu;
import com.amos.podsupapi.repository.MenuRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MenuController {

	private static final Logger LOGGER = LogManager.getLogger(MenuController.class);

	@Autowired
	private MenuRepository menuRepository;

	@CrossOrigin
	@GetMapping(value = "/get_all_menu", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<APIResultDTO> getAllMenu() {
		List<MenuSubDTO> dto = new ArrayList<>();
		for (Menu source : menuRepository.getAllMenues()) {
			MenuSubDTO target = new MenuSubDTO();
			BeanUtils.copyProperties(source, target);
			dto.add(target);
		}
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), dto), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/get_menu_by_id", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<APIResultDTO> getMenuById(@RequestParam(name = "id", required = true) int id) {
		Menu dto = menuRepository.getMenuById(id);
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(ReturnCode.SUCCESS), dto), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/update_menu", produces = { "application/json; charset=UTF-8" })
	public ResponseEntity<APIResultDTO> addOrUpdateMenu(@RequestBody String payload) {

		ReturnCode code = ReturnCode.INTERNAL_WEB_SERVICE_ERROR;
		try {
			ObjectMapper mapper = new ObjectMapper();
			Menu menu = mapper.readValue(payload, Menu.class);
			if (menu.getId() == 0) {
				menuRepository.addMenu(menu);
			} else {
				menuRepository.updateMenu(menu);
			}
			code = ReturnCode.SUCCESS;

		} catch (Exception e) {
			LOGGER.error(String.format("Insert/Upadte Menu ERROR. payload: %s", payload), e);
		}
		LOGGER.info(String.format("Insert/Upadate Menu SUCCESS. payload: %s", payload));
		return new ResponseEntity<>(new APIResultDTO(new ReturnStatusDTO(code)), HttpStatus.OK);
	}
}
