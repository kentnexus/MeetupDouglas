package com.example.csis3275project.web;

import com.example.csis3275project.entities.TopEvents;
import com.example.csis3275project.entities.TrendingGroups;
import com.example.csis3275project.repositories.TopEventsRepository;
import com.example.csis3275project.repositories.TrendingGroupsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class BrowsingController {
    @Autowired
    TrendingGroupsRepository trendingGroupsRepository;
    @Autowired
    TopEventsRepository topEventsRepository;

    @GetMapping("/home")
    public String trending(Model model){
        List<TrendingGroups> trendingGroups = trendingGroupsRepository.findAll();
        model.addAttribute("listGroups",trendingGroups);

        List<TopEvents> topEvents = topEventsRepository.findAll();
        model.addAttribute("listEvents", topEvents);

        return "browsingPage";
    }

    @GetMapping("/")
    public String trending2(Model model){
        List<TrendingGroups> trendingGroups = trendingGroupsRepository.findAll();
        model.addAttribute("listGroups",trendingGroups);

        List<TopEvents> topEvents = topEventsRepository.findAll();
        model.addAttribute("listEvents", topEvents);

        return "browsingPage";
    }
    @GetMapping("/event/{event}")
    public String eventPage(Model model, @PathVariable String event){
        String name = "This is " + event + " group.";
        model.addAttribute("event",name);

        return "eventPage";
    }

    @GetMapping("/event/create")
    public String createEvent(){
        return "Create_EventPage";
    }
}
