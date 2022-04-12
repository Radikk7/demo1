package com.example.demo.repository;

import com.example.demo.model.News;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NewsRepository extends CrudRepository<News,Long > {
    public List<News> findByCategory(String category);
    public List<News> findByDescriptionContaining(String word);
    public List<News> findByTimestampAfterAndTimestampBefore(Date date1,Date date2);
    public List<News> findByTimestampBefore(Date date1);
    public List<News> findByTimestampAfter(Date date2);
    public List<News> findByUser(User user);
}
