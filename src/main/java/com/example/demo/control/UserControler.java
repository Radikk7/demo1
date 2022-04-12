package com.example.demo.control;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.demo.model.News;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;

import com.example.demo.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class UserControler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    MailSender mailSender;

    @PostMapping("/usercreate")
    public String user(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                       @RequestParam(name = "email") String email, Model model) {
      User user1= userRepository.findUserByUsername(username);
        if (user1 != null){
            return "redirect:/usercreate";

        }
        boolean a = false;
        boolean b = false;
        char[] array = password.toCharArray();
        for (int i = 0; i < array.length; i++) {
            if ('A' <= array[i] && array[i] <= 'Z') {
                a = true;
            }
            if (('0' <= array[i] && array[i] <= '9')) {
                b = true;
            }
        }
        if (!a || !b) {
            return "redirect:/usercreate";
        }


        List<Role> roles = new ArrayList<>();
        roles.add(Role.User);
        //roles.add(Role.Admin);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        user.setActive(true);
        user.setAktivCode(UUID.randomUUID().toString());
        userRepository.save(user);
        if (!(StringUtils.isEmpty(user.getEmail()))){
            mailSender.send(user.getEmail(),"aktivCode","Hello! This is  your code ...   Please, visit next link: http://localhost:8080/activate/ ");
        }

        return "/login";
    }

    @GetMapping("/usercreate")
    public String maping() {
        return "/userregistration";
    }

    @GetMapping("/allnews")
    public String allNews(Model model, @AuthenticationPrincipal User user) {
        Iterable<News> newsList = newsRepository.findAll();
        model.addAttribute("listnews", newsList);
        if (user.getRoles().contains(Role.Admin)) {
            return "allnews";
        }
        return "allnewsforusers";
    }

}

