package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @GetMapping("/foo")
  public Cat getFoo() {
    return new Cat("Bob");
  }

  @GetMapping("/bar/foo")
  public Cat getBar() {
    return new Cat("This is the one");
  }
}
