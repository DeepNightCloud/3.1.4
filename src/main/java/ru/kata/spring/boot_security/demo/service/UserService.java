package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public User showUser(Long id);
    public List<User> listUser();
    public void saveUser(User user);
    public void updateUser(User user);
    public void delete(Long id);
    void addDefaultUser();
    User getUserByLogin(String username);
    User passwordCoder(User user);


}
