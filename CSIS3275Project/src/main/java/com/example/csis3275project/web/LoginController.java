package com.example.csis3275project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private RegisterRestController registerController;

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/registration")
    public String register(){ return "signUpPage";}

    @GetMapping("/howItWork")
    public String hiw(){ return "hiw";}

    @GetMapping(path = "/logout")
    public String logout() {return "loginPage";}
}
