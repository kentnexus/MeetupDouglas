package com.example.csis3275project.web;

import com.example.csis3275project.entities.Account;
import com.example.csis3275project.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
//    @GetMapping(path = "/")
//    public String accounts() {
//
//        List<Account> accounts = accountRepository.findAll();
//        model.addAttribute("listAccounts", accounts);
//
//        return "browsingPage";
//    }

    @GetMapping("/profile")
    public String profile(){

        return "AccountProfile";
    }
}
