package com.amos.podsupapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

  @RequestMapping(value = { "/", "home" }, produces = { MediaType.TEXT_PLAIN_VALUE })
  public ResponseEntity<String> home() {
    LOGGER.info("POD From Supplier API Application Start!!!");
    return new ResponseEntity<>("POD From Supplier API Application Start!!!", HttpStatus.OK);
  }

}
