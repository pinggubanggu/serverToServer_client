package com.example.client.service;

import com.example.client.dto.Req;
import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import java.net.URI;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
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
    // -> 응답받을 데이터가 뭔지 모를때는 String으로 받아서 데이터를 확인한다.
    ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);

    System.out.println(result.getStatusCode());
    System.out.println(result.getBody());

    return result.getBody();
  }
  //http://localhost/api/server/post
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

  public UserResponse exchange() {
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

    RequestEntity<UserRequest> requestEntity = RequestEntity
        .post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-authorization", "abcd")
        .header("custom-header", "fffff")
        .body(req);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<UserResponse> response = restTemplate
        .exchange(requestEntity, UserResponse.class);

    return response.getBody();
  }

  public Req<UserResponse> genericExchange() {
    URI uri = UriComponentsBuilder
        .fromUriString("http://localhost:9191")
        .path("/api/server/user/{userId}/name/{userName}")
        .encode()
        .build()
        .expand(100, "bella")
        .toUri();

    System.out.println(uri);

    UserRequest userRequest = new UserRequest();
    userRequest.setName("bella");
    userRequest.setAge(100);

    Req req = new Req<UserRequest>();
    req.setHeader(
      new Req.Header()
    );

    req.setResBody(
      userRequest
    );

    RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
        .post(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .header("x-authorization", "abcd")
        .header("custom-header", "fffff")
        .body(req);

    RestTemplate restTemplate = new RestTemplate();
    // 제네릭은 .class를 쓸 수가 없다. 하여 ParamterizedTypeReference를 사용해서 쓴다.
    ResponseEntity<Req<UserResponse>> response = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Req<UserResponse>>(){});

    // response.getBody() -> Req
    // response.getBody().getResBody() -> Reg.resBody
//    return response.getBody().getResBody();
    return response.getBody();

  }

}
