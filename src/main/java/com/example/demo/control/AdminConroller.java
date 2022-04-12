package com.example.demo.control;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('Admin')")
public class AdminConroller {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/edit")
    public String userUpdate(@RequestParam(name = "id")String id, @RequestParam(name = "password")String password,
                             @RequestParam(name = "username")String username, @RequestParam(name = "admin",required = false)String admin,
                             @RequestParam(name = "user",required = false)String user, Model model){
        String a = id;
        String b = password;
        String c = username;
        String d = admin;
        String e = user;
        List<Role> list=new ArrayList<>();
        if (admin!=null){
            list.add(Role.Admin);
        }
        if (user!=null){
            list.add(Role.User);
        }

        User user1 = new User();
        user1.setUsername(c);
        user1.setPassword(b);
        user1.setId(Long.parseLong(a));
        user1.setRoles(list);
        userRepository.save(user1);
        return "redirect:/allusers";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id")String id){
        if (id.matches("\\d+")){
            userRepository.deleteById(Long.parseLong(id));
        }
        return "redirect:/allusers";
    }

    @GetMapping("/edit/{id}")
    public String userEdit(@PathVariable(name = "id") Long id, Model model){
        User user= userRepository.findById(id).get();
        model.addAttribute("users",user);
        return "userEdit";
    }
    @GetMapping("/allusers")
    public String allUsers(Model model){
        Iterable<User>userList= userRepository.findAll();
        model.addAttribute("users",userList);
        return "allusers";
    }
}
