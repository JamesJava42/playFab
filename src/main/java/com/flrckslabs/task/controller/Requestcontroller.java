package com.flrckslabs.task.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flrckslabs.task.models.Locationdata;
import com.flrckslabs.task.models.Requestdata;
import com.flrckslabs.task.playfabsession.Login;
import com.flrckslabs.task.services.Weatherdata;

@RestController
@RequestMapping("/call")
public class Requestcontroller {
	@Autowired
	private Weatherdata weatherdata;
	@Autowired
	Login login;
	
	
	
	@GetMapping("/get/player")
	public ResponseEntity<?> getPlayerData(){
		
//		String result = login.retrivePlayer();
		String res = login.createSession("9227E82AC7FBC56F");
		return new ResponseEntity<>(res,  HttpStatus.OK);
	}
	
	@GetMapping("/add/player")
	public ResponseEntity<?> addPlayer(@RequestBody Requestdata requestdata ){
		Locationdata result = weatherdata.getWeatherData( requestdata.getLongt(),requestdata.getLat());

		String jsonValue;
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonValue = objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        String response = login.addPlayer("pit4", "pit4@gmail.com", "pit4#4244", jsonValue);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
