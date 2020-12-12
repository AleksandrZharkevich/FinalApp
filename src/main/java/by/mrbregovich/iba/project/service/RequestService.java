package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.dto.RequestRestResponseDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.RequestAlreadyRegistered;
import by.mrbregovich.iba.project.exception.RequestNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequestService {

    Request create(ContactDto contactDto, Long companyId) throws RequestAlreadyRegistered;

    Request findById(Long requestId) throws RequestNotFoundException;

    List<Request> findAllActiveRequestsByUser(User user);

    List<RequestRestResponseDto> findAllActiveRequestsByUserId(Long userId);

    List<RequestRestResponseDto> findAllActiveRequestsByUserIdAndCompanyId(Long userId, Long companyId);

    List<Request> allByManagerId(Long managerId, Long companyId);

    List<RequestRestResponseDto> findRegisteredRequestsByCompanyIdAndPageNumber(Long id, Integer pageNumber);

    Request save(Request request);

    long getAllRequestsCount();

    long getAllDoneRequestsCount();

    void returnRequest(Request request);

    void closeRequest(Request request);
}
