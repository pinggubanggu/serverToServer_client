package com.example.client.service;

import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateService {

  //http://localhost/api/server/hello
  public UserResponse hello() {
    URI uri = UriComponentsBuilder
              .fromUriString("http://localhost:9191")
              .path("/api/server/hello")
              .queryParam("name", "bella")
              .queryParam("age", 30)
              .encode()
              .build()
              .toUri();
    System.out.println(uri.toString());

    RestTemplate restTemplate = new RestTemplate();
    // String result = restTemplate.getForObject(uri, String.class);
    ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

    System.out.println(result.getStatusCode());
    System.out.println(result.getBody());

    return result.getBody();
  }

  public UserResponse post() {
    // http://localhost:9191/api/server/user/{userId}/name/{username} - user를 등록시키는 용도
    URI uri = UriComponentsBuilder
              .fromUriString("http://localhost:9191")
              .path("/api/server/user/{userId}/name/{userName}")
              .encode()
              .build()
              .expand(100, "bella")
              .toUri();

    System.out.println(uri);

    // http body -> object -> object mapper -> json -> rest template -> http body json
    UserRequest req = new UserRequest();
    req.setName("bella");
    req.setAge(100);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<UserResponse> response = restTemplate
        .postForEntity(uri, req, UserResponse.class);

    System.out.println(response.getStatusCode());
    System.out.println(response.getHeaders());
    System.out.println(response.getBody());

    return response.getBody();
  }
}
