package com.example.client.controller;

import com.example.client.dto.Req;
import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

  // 생성자 주입 방식 -  명시적으로 보여준다.
  private final RestTemplateService restTemplateService;

  public ApiController(
      RestTemplateService restTemplateService) {
    this.restTemplateService = restTemplateService;
  }

  @GetMapping("/hello")
  public UserResponse getHello() {
    return restTemplateService.hello();
  }

  @PostMapping("/post")
  public UserResponse getPost() {
    return restTemplateService.post();
  }

  @PostMapping("/exchange")
  public UserResponse getExchange() {
    return restTemplateService.exchange();
  }

  @PostMapping("/genericExchange")
  public Req<UserResponse> getGenericExchange() {
    return restTemplateService.genericExchange();
  }
}
