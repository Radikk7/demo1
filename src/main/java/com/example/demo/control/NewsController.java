package com.example.demo.control;

import com.example.demo.model.News;
import com.example.demo.model.User;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@PreAuthorize("hasAuthority('Admin')")
@Controller
public class NewsController {
    @Value("${upload.path}")
    private String url;
    @Autowired
    NewsRepository newsRepository;
    @PostMapping("/postmaping")
        public String news (@AuthenticationPrincipal User user, @RequestParam(name ="title") String title, @RequestParam(name = "description") String description,
                            @RequestParam(name = "category") String category,
                            @RequestParam(name = "file") MultipartFile multipartFile, Model model) throws IOException {
        if (title.equals("") || description.equals("")){
            return "redirect:/allnews";
        }
        MultipartFile a = multipartFile;
        String result = "";
        String r = "";
        if (a != null){ // добавление файлов
            String uuid = UUID.randomUUID().toString();
            result=uuid + "." + a.getOriginalFilename();
            r = url + "/" + result;
            System.out.println(r);
            System.out.println(result);
            System.out.println(url);
            multipartFile.transferTo(new File(r));
        }

        Timestamp timestamp = new Timestamp(new Date().getTime());
        News news = new News(title,description,category,timestamp,user);

        news.setFilename(result);

        newsRepository.save(news);
        return "redirect:/allnews";
        }
        @GetMapping("/newsmaping")
    public String maping(){
        return "registration";
        }

      @GetMapping("/editnews/{id}")
     public String createNews(@PathVariable(name = "id")Long id,Model model){
        News news = newsRepository.findById(id).get();
        model.addAttribute("listnews",news);
        return "/newsedit";
     }
     @PostMapping("/editnews")
    public String editNews(@RequestParam(name = "id")String id,@RequestParam(name = "title")String title,
                           @RequestParam(name = "description")String description,@RequestParam(name = "category")String category){
        News news = new News();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        news.setId(Long.parseLong(id));
        news.setTitle(title);
        news.setDescription(description);
        news.setCategory(category);
        news.setTimestamp(timestamp);
        newsRepository.save(news);
        return "redirect:/allnews";
     }
     @GetMapping("/deletenews/{id}")
    public String newsDelete(@PathVariable(name = "id")String id){
        if (id.matches("\\d+")){
            newsRepository.deleteById(Long.parseLong(id));
        }
        return "redirect:/allnews";
     }

}
