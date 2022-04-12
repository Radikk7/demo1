package com.example.demo.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @GetMapping
    public static String hello(){
        return "hello";
    }
    @GetMapping("abc")
    public String travel(Model model){
      char [] array = new char[10];
      for (int i = 0; i < array.length; i++){
          array[i] = ((char) (Math.random() * 60000));
      }
      String a = new String(array);
      model.addAttribute("word",a);

      return "novay";
    }
    @GetMapping("umn")
    public  String umnojenie(@RequestParam(name = "number")String num,Model model){
      int a = Integer.parseInt(num);
      model.addAttribute("num",a*2);
      return "novay";
    }
    @GetMapping("count")
    public String countr(@RequestParam(name = "number")String num1,@RequestParam(name ="number2") String num2, Model model){
        int a = Integer.parseInt(num1);
        int b = Integer.parseInt(num2);
        model.addAttribute("cou",a+b);
        return "count";
    }
    @GetMapping("stroka") //создать функцию которая принимает строки и выводит рандомную строку этого размера
    public  String stroka(@RequestParam(name = "count")String count, Model model){
        int a = Integer.parseInt(count);
        char [] array = new char[a];
        for (int i =0; i < array.length; i++){
            array[i]= ((char) (Math.random() * 50000));
        }
        String result = new String(array);

        model.addAttribute("count",result);
        return "stroka";
    }
    @PostMapping("/registr")
    public  void reg(@RequestParam(name = "name")String name,@RequestParam(name = "surname")String surname,
                             @RequestParam(name ="age")String age,@RequestParam(name ="email")String email,
                             @RequestParam(name = "password")String password, Model model){
        String a =name;
        String b = surname;
        String c = age;
        String d = email;
        String e = password;
        System.out.println(a + " " + b + " " + c + " " + d + " " +e);
    }
    @GetMapping("/reg")
    public  String regis(){
        return "registration";
    }
    @PostMapping("/greeting")
    public String from(@RequestParam(name = "name")String name,@RequestParam(name = "message")String message,Model model){
        model.addAttribute("cou",name+message);
        return "count";
    }
}
