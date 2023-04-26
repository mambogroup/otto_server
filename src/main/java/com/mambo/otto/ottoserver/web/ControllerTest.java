package com.mambo.otto.ottoserver.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {
    
    @GetMapping("/home")
    public String home(){
        return "테스트 시작";
    }
}
