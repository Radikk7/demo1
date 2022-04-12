package com.example.demo.repository;

import com.example.demo.model.News;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository <User,Long> {
    public User findUserByUsername(String username); //это функция которая сама находит в базе sql юзера по имени

}
