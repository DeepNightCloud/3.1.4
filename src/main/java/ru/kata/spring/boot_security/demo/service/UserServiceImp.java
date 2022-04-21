package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImp implements UserService {

        private final UserDao userDao;
        private final RoleService roleService;
        private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User showUser(Long id) {
        return userDao.showIdUser(id);
    }

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public User saveUser(User user) {
         userDao.saveUser(user);

        return user;
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
        public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public void addDefaultUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.findById(1L));
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleService.findById(1L));
        roleSet2.add(roleService.findById(2L));
        User user1 = new User("danil", "danil", (byte) 27, "danil@mail.ru",  roleSet);
        User user2 = new User("lida", "lida", (byte) 22, "lida@mail.ru", roleSet2);
        saveUser(user1);
        saveUser(user2);
    }

    @Override
    public User getUserByLogin(String email) {
        return userDao.getUserByLogin(email);
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User showIdUser(Long id) {
        return userDao.showIdUser(id);
    }
}
