package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.dto.RequestRestResponseDto;
import by.mrbregovich.iba.project.entity.Contact;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.entity.User;
import by.mrbregovich.iba.project.exception.RequestAlreadyRegistered;
import by.mrbregovich.iba.project.exception.RequestNotFoundException;
import by.mrbregovich.iba.project.repository.CompanyRepository;
import by.mrbregovich.iba.project.repository.RequestRepository;
import by.mrbregovich.iba.project.service.RequestService;
import by.mrbregovich.iba.project.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private CompanyRepository companyRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository, CompanyRepository companyRepository) {
        this.requestRepository = requestRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Request create(ContactDto contactDto, Long companyId) throws RequestAlreadyRegistered {
        if (requestRepository.findByContact_PhoneNumberAndCompany_Id(contactDto.getPhoneNumber(), companyId) != null) {
            throw new RequestAlreadyRegistered(AppConstants.REQUEST_WITH_PHONE_ALREADY_REGISTERED);
        }
        Request request = new Request();
        Contact contact = Mapper.map(contactDto, Contact.class);
        request.setContact(contact);
        request.setPlacedAt(LocalDate.now());
        request.setRequestStatus(RequestStatus.REGISTERED);
        request.setCompany(companyRepository.findById(companyId).get());
        return requestRepository.save(request);
    }

    @Override
    public Request findById(Long requestId) throws RequestNotFoundException {
        return requestRepository.findById(requestId).orElseThrow(() -> {
            return new RequestNotFoundException(AppConstants.REQUEST_ID_NOT_FOUND_MSG);
        });
    }

    @Override
    public List<Request> allActiveRequests(Long companyId) {
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.REGISTERED, companyId);
        return Objects.requireNonNullElse(requests, Collections.emptyList());
    }

    @Override
    public List<Request> findAllActiveRequestsByUser(User user) {
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndManager_Id(RequestStatus.IN_PROCESS, user.getId());
        return Objects.requireNonNullElse(requests, Collections.emptyList());
    }

    @Override
    public List<Request> allDoneRequests(Long companyId) {
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.DONE, companyId);
        return Objects.requireNonNullElse(requests, Collections.emptyList());
    }

    @Override
    public List<Request> allByManager(String login, Long companyId) {
        List<Request> requests = requestRepository.findAllByManager_LoginAndCompany_Id(login, companyId);
        if (requests == null) {
            requests = Collections.emptyList();
        }
        return requests;
    }

    @Override
    public List<Request> allByManagerId(Long managerId, Long companyId) {
        List<Request> requests = requestRepository.findAllByManager_IdAndCompany_Id(managerId, companyId);
        if (requests == null) {
            requests = Collections.emptyList();
        }
        return requests;
    }

    @Override
    public List<RequestRestResponseDto> findRegisteredRequestsByCompanyIdAndPageNumber(Long companyId, Integer pageNumber) {
        int pageSize = AppConstants.REQUESTS_PAGE_SIZE;
        int itemsToSkip = (pageNumber - 1) * pageSize;
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.REGISTERED, companyId);
        if (requests == null) {
            return Collections.emptyList();
        }
        return requests.stream()
                .sorted((r1, r2) -> r2.getPlacedAt().compareTo(r1.getPlacedAt()))
                .skip(itemsToSkip)
                .limit(pageSize)
                .map(RequestRestResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }
}
