package by.mrbregovich.iba.project.repository;

import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findByContact_PhoneNumberAndCompany_Id(String phoneNumber, Long id);

    List<Request> findAllByManager_IdAndCompany_Id(Long managerId, Long companyId);

    List<Request> findAllByRequestStatusIs(RequestStatus requestStatus);

    List<Request> findAllByRequestStatusIsAndCompany_Id(RequestStatus requestStatus, Long id);

    List<Request> findAllByRequestStatusIsAndManager_Id(RequestStatus requestStatus, Long id);

    List<Request> findAllByCompany_Id(Long id);
}
