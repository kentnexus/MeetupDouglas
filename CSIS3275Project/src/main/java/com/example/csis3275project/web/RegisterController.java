package com.example.csis3275project.web;

import com.example.csis3275project.entities.Account;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path="api/v1/registration")
@AllArgsConstructor
public class RegisterController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path="confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }

    @PostMapping(path="/process_register")
    public String processRegister(HttpServletRequest request, Model model){
        String errorMsg = request.getParameter("errorMsg");
        String fname = request.getParameter("firstName");
        String lname = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("First name is " + fname);
        System.out.println("Last name is " + lname);
        System.out.println("Email is " + email);
        System.out.println("Password is " + password);
        RegistrationRequest accountReq = new RegistrationRequest(fname, lname, email, password);
        return registrationService.register(accountReq);
    }

}
