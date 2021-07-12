package com.example.todolistii.services;

import com.example.todolistii.domain.User;
import com.example.todolistii.dto.UserDto;
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
            return new UserDto();
        }
        return convertor.userToDto(foundOptional.get());
    }

    @Override
    @Transactional
    public UserDto update(long id, User user) {
        Optional<User> foundOptional = userRepository.findById(id);
        if (foundOptional.isEmpty()) {
            return new UserDto();
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
            return String.format("User with id: %d doesn`t exist", id);
        }
        userRepository.delete(foundOptional.get());
        return String.format("User with id: %d was successfully removed", id);
    }
}
