package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.EditUserDto;
import by.mrbregovich.iba.project.dto.NewUserDto;
import by.mrbregovich.iba.project.entity.*;
import by.mrbregovich.iba.project.exception.UserAlreadyExistsException;
import by.mrbregovich.iba.project.exception.UserNotFoundException;
import by.mrbregovich.iba.project.repository.RequestRepository;
import by.mrbregovich.iba.project.repository.RoleRepository;
import by.mrbregovich.iba.project.repository.UserRepository;
import by.mrbregovich.iba.project.service.UserService;
import by.mrbregovich.iba.project.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RequestRepository requestRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           RequestRepository requestRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(NewUserDto userDto) throws UserAlreadyExistsException {
        User user = userRepository.findByLoginOrContact_PhoneNumber(userDto.getLogin(), userDto.getPhoneNumber());
        if (user != null) {
            if (user.getLogin().equalsIgnoreCase(userDto.getLogin())) {
                throw new UserAlreadyExistsException(AppConstants.LOGIN_ALREADY_REGISTERED_MSG);
            } else {
                throw new UserAlreadyExistsException(AppConstants.PHONE_NUMBER_ALREADY_REGISTERED_MSG);
            }
        }
        Contact contact = Mapper.map(userDto, Contact.class);
        user = Mapper.map(userDto, User.class);
        user.setContact(contact);
        user.addRole(roleRepository.findByName("ROLE_USER"));
        user.setCreatedAt(LocalDate.now());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) throws UserNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException(AppConstants.LOGIN_NOT_FOUND_MSG);
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(AppConstants.USER_ID_NOT_FOUND_MSG));
    }

    @Override
    public List<User> findAllDeletedUsers() {
        List<User> users = userRepository.findAllByUserStatusIs(UserStatus.DELETED);
        if (users == null) {
            return new ArrayList<>();
        }
        return users;
    }

    @Override
    public void deleteById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException(AppConstants.USER_ID_NOT_FOUND_MSG));
        List<Request> userRequests = requestRepository.findAllByRequestStatusIsAndManager_Id(RequestStatus.IN_PROCESS, id);
        userRequests.forEach(request -> {
            request.setRequestStatus(RequestStatus.REGISTERED);
            request.setManager(null);
            requestRepository.save(request);
        });
        user.getJoinedCompanies().forEach(company -> company.getParticipants().remove(user));
        user.setUserStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    public User update(User user, EditUserDto form) {
        if (form.getRegion() != null && !form.getRegion().equalsIgnoreCase("")) {
            user.getContact().setRegion(form.getRegion());
        }
        if (form.getDistrict() != null && !form.getDistrict().equalsIgnoreCase("")) {
            user.getContact().setDistrict(form.getDistrict());
        }
        if (form.getCity() != null && !form.getCity().equalsIgnoreCase("")) {
            user.getContact().setCity(form.getCity());
        }
        if (form.getStreetAddress() != null && !form.getStreetAddress().equalsIgnoreCase("")) {
            user.getContact().setStreetAddress(form.getStreetAddress());
        }
        return userRepository.save(user);
    }
}
