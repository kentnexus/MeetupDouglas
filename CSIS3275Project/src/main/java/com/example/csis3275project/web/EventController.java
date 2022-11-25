package com.example.csis3275project.web;

import com.example.csis3275project.entities.*;
import com.example.csis3275project.repositories.*;
import jdk.jfr.Event;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class EventController {

    @Autowired
    EventsRepository eventsRepository;
    EventGroupUserRepository eventGroupUserRepository;
    GroupUserRepository groupUserRepository;
    GroupsRepository groupsRepository;

    @GetMapping("/event/{eventName}/event")
    public String eventPage(Model model, @PathVariable String eventName, Account account, long id){
//        event details
        Events event = eventsRepository.findById(id).orElseThrow();
//        participants
        List<EventGroupUser> elist = eventGroupUserRepository.findAll();
        List<EventGroupUser> eventUsers = new ArrayList<>();
        EventGroupUser admin = new EventGroupUser();

        for(EventGroupUser egu:elist){
            if(egu.getEvent().getEvent_id()==id)
                eventUsers.add(egu);
            if(egu.isOrganizer())
                admin = egu;
        }


        model.addAttribute("ev",event);
        model.addAttribute("evSize",eventUsers.size());
        model.addAttribute("organizer",admin);

        return "EventPage";
    }

    @GetMapping("/event/create")
    public String createEvent(Model model, Account account){
        model.addAttribute("event", new Events());

//        returning specific groups to the user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        account = (Account) auth.getPrincipal();
        List<Group_User> groupsUserList = groupUserRepository.findAll();
        List<Group_User> newGrpUserList = new ArrayList<>();

        for(Group_User grpUser: groupsUserList){
            if(grpUser.getAccount().getUser_id()== account.getUser_id())
                newGrpUserList.add(grpUser);
        }

        model.addAttribute("groups",newGrpUserList);

        return "Create_EventPage";
    }

    @PostMapping("/event/save")
    public String saveEvent(Account account, Groups group, Events event,
                            EventGroupUser eventGroupUser, HttpServletRequest request){
//        new events
            eventsRepository.save(event);
            eventGroupUser.setEvent(event);

//        account to eventAssociateTable
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            account = (Account) auth.getPrincipal();
            eventGroupUser.setAccount(account);

//        group to eventAssociateTable
            long grpId = Long.parseLong(request.getParameter("group"));
            List<Groups> groupsList = groupsRepository.findAll();
            for (Groups g : groupsList) {
                if (g.getGroup_id() == grpId)
                    group = g;
            }
            eventGroupUser.setGroup(group);

            eventGroupUser.setOrganizer(true);
            eventGroupUserRepository.save(eventGroupUser);

        return "redirect:/event/manage";
    }

        @PostMapping("/event/saveEdit")
        public String saveEvent2(Events event, EventGroupUser eventGroupUser, HttpServletRequest request){

            long idd = 0;
//            try{
                idd = Long.parseLong(request.getParameter("id"));
                Events ev = eventsRepository.findById(idd).orElseThrow();
                String newName = request.getParameter("name");
                String newDescription = request.getParameter("description");
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String date = request.getParameter("schedule");
                LocalDate newDate = ev.getSchedule();
                if(!date.isEmpty()) {
                    newDate = LocalDate.parse(date, dateFormat);
                    ev.setSchedule(newDate);
                }
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
                String timeStart = request.getParameter("time_start");
                String timeEnd = request.getParameter("time_end");
                LocalTime newTimeStart = ev.getTime_start(), newTimeEnd = ev.getTime_end();
                if(!timeStart.isEmpty()){
                    newTimeStart = LocalTime.parse(timeStart,timeFormat);
                    ev.setTime_start(newTimeStart);
                }
                if(!timeEnd.isEmpty()) {
                    newTimeEnd = LocalTime.parse(timeEnd, timeFormat);
                    ev.setTime_end(newTimeEnd);
                }

                if(!newName.isEmpty())
                    ev.setName(newName);
                if(!newDescription.isEmpty())
                    ev.setDescription(newDescription);
                eventsRepository.save(ev);
//            }
//            catch(Exception e) {}

            return "redirect:/event/manage";
        }

    @GetMapping("/event/manage")
    public String manageEvent(Model model, Account account){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        account = (Account) auth.getPrincipal();

        List<EventGroupUser> eventGroupUsers = eventGroupUserRepository.findAll();
        List<EventGroupUser> newEvents = new ArrayList<>();

        for(EventGroupUser e:eventGroupUsers){
            if(e.getAccount().getUser_id()==account.getUser_id())
                newEvents.add(e);
        }

        model.addAttribute("listEvents",newEvents);
        model.addAttribute("evnt", new Events());

        return "ManageEvent";
    }

    @GetMapping("/event/delete")
    public String deleteEvent(long id){
        List<EventGroupUser> eventGroupUsersList = eventGroupUserRepository.findAll();
        for(EventGroupUser e: eventGroupUsersList){
            if(e.getEvent().getEvent_id()==id){
                eventGroupUserRepository.delete(e);
            }
        }
        eventsRepository.deleteById(id);

        return "redirect:/event/manage";
    }

}
