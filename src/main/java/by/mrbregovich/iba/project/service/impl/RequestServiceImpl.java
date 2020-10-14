package by.mrbregovich.iba.project.service.impl;

import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.entity.Contact;
import by.mrbregovich.iba.project.entity.Request;
import by.mrbregovich.iba.project.entity.RequestStatus;
import by.mrbregovich.iba.project.repository.RequestRepository;
import by.mrbregovich.iba.project.service.RequestService;
import by.mrbregovich.iba.project.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Request create(ContactDto contactDto) {
        if (requestRepository.findByContact_PhoneNumber(contactDto.getPhoneNumber()) != null) {
            return null;
        }
        Request request = new Request();
        Contact contact = Mapper.map(contactDto, Contact.class);
        request.setContact(contact);
        request.setPlacedAt(LocalDate.now());
        request.setRequestStatus(RequestStatus.REGISTERED);
        return requestRepository.save(request);
    }

    @Override
    public List<Request> allActiveRequests() {
        return requestRepository.findAllByRequestStatusIs(RequestStatus.REGISTERED);
    }

    @Override
    public List<Request> allDoneRequests() {
        return requestRepository.findAllByRequestStatusIs(RequestStatus.DONE);
    }

    @Override
    public List<Request> allByManager(String login) {
        return requestRepository.findAllByManager_Login(login);
    }

    @Override
    public List<Request> allByManagerId(Long id) {
        return requestRepository.findAllByManager_Id(id);
    }
}
