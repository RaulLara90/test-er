package com.apitran.tran.dao;


import com.apitran.tran.models.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User,Long> {

    User findByNameAndLastNameAndPhone(String name, String lastName, String phone);
}
