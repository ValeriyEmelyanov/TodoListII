package com.example.todolistii.services;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
import com.example.todolistii.services.interfaces.IUserService;
import com.example.todolistii.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSevice implements IUserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final Convertor convertor;

    @Autowired
    public UserSevice(Convertor convertor) {
        this.convertor = convertor;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        List<User> users = entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
        List<UserDto> result = users.stream()
                .map(convertor::userToDto)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    @Transactional
    public UserDto create(User user) {
        entityManager.persist(user);

        return convertor.userToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(long id) {
        User foundUser = entityManager
                .createQuery("SELECT user FROM User user WHERE user.id=:id", User.class)
                .setParameter("id", id)
                .getSingleResult();

        return convertor.userToDto(foundUser);
    }

    @Override
    public UserDto update(long id, User user) {
        return null;
    }

    @Override
    public UserDto delete(long id) {
        return null;
    }
}
