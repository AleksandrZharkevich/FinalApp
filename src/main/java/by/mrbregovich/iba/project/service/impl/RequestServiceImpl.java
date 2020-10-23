package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.constants.AppConstants;
import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.entity.Company;
import by.mrbregovich.iba.project.entity.Contact;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.exception.RequestAlreadyRegistered;
import by.mrbregovich.iba.project.repository.CompanyRepository;
import by.mrbregovich.iba.project.repository.RequestRepository;
import by.mrbregovich.iba.project.service.RequestService;
import by.mrbregovich.iba.project.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
    public List<Request> allActiveRequests(Long companyId) {
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.REGISTERED, companyId);
        if (requests == null) {
            requests = Collections.emptyList();
        }
        return requests;
    }

    @Override
    public List<Request> allDoneRequests(Long companyId) {
        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.DONE, companyId);
        if (requests == null) {
            requests = Collections.emptyList();
        }
        return requests;
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
    public Page<Request> findRegisteredRequestsByPageAndCompanyId(Pageable pageable, Long companyId) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Request> requests = requestRepository.findAllByRequestStatusIsAndCompany_Id(RequestStatus.REGISTERED, companyId);
        List<Request> list;
        if (requests == null) {
            requests = Collections.emptyList();
        }
        if (requests.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, requests.size());
            list = requests.subList(startItem, toIndex);
        }

        return new PageImpl<Request>(list, PageRequest.of(currentPage, pageSize), requests.size());
    }
}
