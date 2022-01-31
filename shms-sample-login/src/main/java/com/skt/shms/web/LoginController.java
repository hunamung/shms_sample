package com.skt.shms.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.skt.shms.oauth2.config.OAuthToken;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class LoginController {
    @GetMapping(value = "")
    public ModelAndView auth() {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
    
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public OAuthToken getToken(@RequestParam(value = "username")String username
                             , @RequestParam(value = "password")String password) {
        System.out.println("getToken~~");
        
        String credentials = "auth_id:auth_secret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("scope", "shms_user_info");
        params.add("username", username);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", request,
                String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        
        return null;
    }
    
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public OAuthToken refreshToken(@RequestParam(value = "refresh_token")String refresh_token) {
        System.out.println("refreshToken~~");
        
        String credentials = "auth_id:auth_secret";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("scope", "shms_user_info");
        params.add("refresh_token", refresh_token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        Gson gson = new Gson();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", request,
                String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), OAuthToken.class);
        }
        
        return null;
    }
}
