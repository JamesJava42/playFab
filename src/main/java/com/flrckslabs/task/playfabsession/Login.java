package com.flrckslabs.task.playfabsession;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flrckslabs.task.models.Dataresponse;
import com.flrckslabs.task.models.Player;
import com.playfab.PlayFabClientAPI;
import com.playfab.PlayFabClientModels.LoginResult;
import com.playfab.PlayFabClientModels.LoginWithEmailAddressRequest;
import com.playfab.PlayFabClientModels.LoginWithPlayFabRequest;
import com.playfab.PlayFabClientModels.RegisterPlayFabUserResult;
import com.playfab.PlayFabErrors.PlayFabResult;
import com.playfab.PlayFabSettings;

public class Login {
	
	
	@Autowired
	RestTemplate restTemplate;
	private static final String API_URL = "https://AA0CF.playfabapi.com/Client/RegisterPlayFabUser";
    private static final String TITLE_ID = "AA0CF";
     private static final String SECRET_KEY = "E1OCQY4I1ADMIFGDRBTI3CUTTHMU11J8ZO9P5K11WAS1E3J573";
     private static final String PLAYFAB_API_URL = "https://AA0CF.playfabapi.com/Client/GetUserData";


	
    public String retrivePlayer(String playFabId) {
    	 HttpHeaders headers = new HttpHeaders();
         headers.set("X-SecretKey", SECRET_KEY);
         String url = PLAYFAB_API_URL + "?PlayFabId=" + playFabId + "&TitleId=" + TITLE_ID;
         ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
         if(response.getStatusCode().is2xxSuccessful()) {
      	   System.out.println("Sucessfull");
      	   return "sucess";
         }else {
      	   System.out.println("fail");
      	   return "fail";
         }
//		return playFabId;
    	
    }
    
    public String addPlayer(String username, String email, String password, String customData) {
    	
    	 
	      HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("X-SecretKey", SECRET_KEY);
    	String requestBody = String.format("{\"TitleId\":\"%s\",\"Username\":\"%s\",\"Email\":\"%s\",\"Password\":\"%s\",\"CustomData\":%s}",
                TITLE_ID, username, email, password, customData);
    	
    	HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    	ResponseEntity<String> response = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );
    	if (response.getStatusCode().is2xxSuccessful()) {
           System.out.println("Sucessfull data"+response.getBody());
            return extractPlayFabId(response.getBody());
        } else {
            return "fails";
        }

    	
    }
	
	private String extractPlayFabId(String body) {
		// TODO Auto-generated method stub
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(body);
            JsonNode dataNode = responseJson.get("data");
            if (dataNode != null) {
            	
                JsonNode playFabIdNode = dataNode.get("PlayFabId");
                if (playFabIdNode != null) {
                    return playFabIdNode.asText();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}

	public String createSession(String playFabId) {
//        PlayFabSettings.staticSettings.DeveloperSecretKey = playFabSecretKey;
		  
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.set("X-SecretKey", SECRET_KEY);
	       
	        String request = String.format("{\"PlayFabId\":\"%s\",\"Keys\":null}", playFabId);

	        HttpEntity<String> entity = new HttpEntity<>(request, headers);
	        System.out.println("entity "+entity);
	        ResponseEntity<String> response = restTemplate.exchange(
	        		PLAYFAB_API_URL,
	                HttpMethod.POST,
	                entity,
	                String.class
	        );
	        System.out.println(response);

           if(response.getStatusCode().is2xxSuccessful()) {
        	   return ""+response.getBody();
           }else {
        	   System.out.println("fail");
        	   return "fail";
           }

        
    }

}
