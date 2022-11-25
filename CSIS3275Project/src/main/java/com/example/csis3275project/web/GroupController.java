package com.example.csis3275project.web;

import com.example.csis3275project.entities.Account;
import com.example.csis3275project.entities.Group_User;
import com.example.csis3275project.entities.Groups;
import com.example.csis3275project.repositories.AccountRepository;
import com.example.csis3275project.repositories.GroupUserRepository;
import com.example.csis3275project.repositories.GroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class GroupController {

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    GroupUserRepository groupUserRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/group/{group}")
    public String groupPage(Model model, @PathVariable String group){
        String name = "This is " + group + " group.";
        model.addAttribute("group",name);

        return "groupPage";
    }

    @GetMapping("/group/create")
    public String createGroup(Model model){

        model.addAttribute("group", new Groups());
        return "Create_GroupPage";
    }

    @PostMapping("/group/save")
    public String saveGroup(Account account,  Model model, Groups group, Group_User group_user){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        account = (Account) auth.getPrincipal();
        groupsRepository.save(group);
        group_user.setAccount(account);
        group_user.setGroup(group);
        groupUserRepository.save(group_user);

        return "redirect:/";
    }

}
