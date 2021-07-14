package com.example.todolistii.services;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
import com.example.todolistii.exceptions.EmptyDataException;
import com.example.todolistii.repositories.UserRepository;
import com.example.todolistii.services.interfaces.IUserService;
import com.example.todolistii.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserSevice implements IUserService {

    private final Convertor convertor;

    private final UserRepository userRepository;

    @Autowired
    public UserSevice(Convertor convertor, UserRepository userRepository) {
        this.convertor = convertor;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(convertor::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto create(User user) {
        userRepository.save(user);
        return convertor.userToDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(long id) {
        Optional<User> foundOptional = userRepository.findById(id);
        if (foundOptional.isEmpty()) {
//            throw new EmptyDataException(String.format("Unable to get user with id: %d", id));
            return null;
        }
        return convertor.userToDto(foundOptional.get());
    }

    @Override
    @Transactional
    public UserDto findUserByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
        if (userOptional.isEmpty()) {
            throw new EmptyDataException("Unable to get user with such email and password");
        }
        return convertor.userToDto(userOptional.get());
    }

    @Override
    @Transactional
    public UserDto update(long id, User user) {
        Optional<User> foundOptional = userRepository.findById(id);
        if (foundOptional.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to update user with id: %d", id));
        }
        User target = foundOptional.get();
        target.setEmail(user.getEmail());
        target.setPassword(user.getPassword());
        return convertor.userToDto(target);
    }

    @Override
    @Transactional
    public String delete(long id) {
        Optional<User> foundOptional = userRepository.findById(id);
        if (foundOptional.isEmpty()) {
            throw new EmptyDataException(String.format("Unable to delete user with id: %d ", id));
        }
        userRepository.delete(foundOptional.get());
        return String.format("User with id: %d was successfully removed", id);
    }
}
