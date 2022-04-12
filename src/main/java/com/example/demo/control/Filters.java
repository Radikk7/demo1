package com.example.demo.control;

import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class Filters {
    @Autowired
    NewsRepository newsRepository;
    @Autowired
    UserRepository userRepository;
    @PostMapping("/filtercategory")
    public String getElementsByCategory(@RequestParam(name = "category")String categoty ,Model model){
        if (categoty.equals("")){
            return "redirect:/allnews";
        }
        List<News> newsList= newsRepository.findByCategory(categoty);
       model.addAttribute("listnews",newsList);
        return "allnews";
    }
    @PostMapping("/filterword")
    public String searchElementWord(@RequestParam(name = "word")String word,Model model){
        List<News>newsList= newsRepository.findByDescriptionContaining(word);
        int count = newsList.size();
        model.addAttribute("size",count);
        model.addAttribute("listnews",newsList);
        return "allnews";
    }
    @PostMapping("/datefilter")
    public String dateFilter(@RequestParam(name = "date1",required = false)@DateTimeFormat(pattern="yyyy-MM-dd") Date date1,
                             @RequestParam(name = "date2",required = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")Date date2, Model model){
        if (date1 == null && date2 ==null){
            return "redirect:/allnews";
        }
        List<News>newsList1 = new ArrayList<>();
         if (date1 != null && date2 == null){
           newsList1= newsRepository.findByTimestampBefore(date1);
        }
        else if (date1 == null && date2 !=null){
           newsList1= newsRepository.findByTimestampAfter(date2);

        }
        else {
            newsList1=newsRepository.findByTimestampAfterAndTimestampBefore(date1,date2);
        }
        model.addAttribute("listnews",newsList1);
        return "allnews";
    }
    @PostMapping("/userfilter")
    public String authorFilter(@RequestParam(name = "userName")String user,Model model){
      User user1 = userRepository.findUserByUsername(user);
        List<News> newsList = newsRepository.findByUser(user1);
        model.addAttribute("listnews",newsList);
        return "allnews";
    }


}
