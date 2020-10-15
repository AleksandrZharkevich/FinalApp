package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.NewUserDto;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.UserAlreadyExistsException;
import by.mrbregovich.iba.project.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User register(NewUserDto userDto) throws UserAlreadyExistsException;

    User findByLogin(String login) throws UserNotFoundException;

    User findById(Long id);

    List<User> findAll();

    List<User> findAllDeletedUsers();

    List<User> findAllActiveUsers();

    void deleteById(Long id) throws UserNotFoundException;

    void deleteByLogin(String login) throws UserNotFoundException;
}
