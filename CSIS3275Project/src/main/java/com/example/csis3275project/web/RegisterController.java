package com.example.csis3275project.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/registration")
@AllArgsConstructor
public class RegisterController {

    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String login(){ return "loginPage";}

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path="confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

}
