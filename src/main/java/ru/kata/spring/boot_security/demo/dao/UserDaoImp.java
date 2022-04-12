package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> listUser() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User showIdUser(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(showIdUser(id));
    }

    @Override
    public User getUserByLogin(String username) {
        TypedQuery<User> q = (entityManager.createQuery("select users from User users " +
                "join fetch users.roles where users.username = :username",User.class));
        q.setParameter("username",username);
        return q.getResultList().stream().findFirst().orElse(null);
    }


}
