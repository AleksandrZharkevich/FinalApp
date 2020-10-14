package by.mrbregovich.iba.project.repository;

import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findByContact_PhoneNumber(String phoneNumber);

    List<Request> findAllByManager_Login(String login);

    List<Request> findAllByManager_Id(Long id);

    List<Request> findAllByRequestStatusIs(RequestStatus requestStatus);
}
