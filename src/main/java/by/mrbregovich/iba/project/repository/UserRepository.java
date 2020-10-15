package by.mrbregovich.iba.project.repository;

import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.entity.UserStatus;
import by.mrbregovich.iba.project.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login) throws UserNotFoundException;

    User findByLoginOrContact_PhoneNumber(String login, String phoneNumber);

    List<User> findAllByUserStatusIs(UserStatus userStatus);
}
