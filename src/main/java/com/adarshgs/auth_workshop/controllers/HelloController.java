package com.adarshgs.auth_workshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hello")
public class HelloController {
    
    @GetMapping("/public")
    public String sayHelloPublic() {
        return new String("This endpoint is publicly accessible");
    }

    @GetMapping("/private")
    public String sayHelloPrivate() {
        return new String("This endpoint is accessible only by the private user");
    }

    @GetMapping("/admin")
    public String sayHelloAdmin() {
        return new String("This endpoint is accessible only by the admin");
    }
}
