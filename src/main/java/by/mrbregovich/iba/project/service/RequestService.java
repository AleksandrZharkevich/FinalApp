package by.mrbregovich.iba.project.service;

import by.mrbregovich.iba.project.dto.ContactDto;
import by.mrbregovich.iba.project.entity.Request;

import java.util.List;

public interface RequestService {

    Request create(ContactDto contactDto);

    List<Request> allActiveRequests();

    List<Request> allDoneRequests();

    List<Request> allByManager(String login);

    List<Request> allByManagerId(Long id);
}
