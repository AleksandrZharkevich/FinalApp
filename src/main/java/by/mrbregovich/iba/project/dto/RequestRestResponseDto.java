package by.mrbregovich.iba.project.dto;

import by.mrbregovich.iba.project.entity.Request;
import lombok.Data;

@Data
public class RequestRestResponseDto {
    private Long id;
    private String region;
    private String district;
    private String city;
    private String streetAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public static RequestRestResponseDto of(Request request) {
        RequestRestResponseDto requestRestResponseDto = new RequestRestResponseDto();
        requestRestResponseDto.setId(request.getId());
        requestRestResponseDto.setRegion(request.getContact().getRegion());
        requestRestResponseDto.setDistrict(request.getContact().getDistrict());
        requestRestResponseDto.setCity(request.getContact().getCity());
        requestRestResponseDto.setStreetAddress(request.getContact().getStreetAddress());
        requestRestResponseDto.setFirstName(request.getContact().getFirstname());
        requestRestResponseDto.setLastName(request.getContact().getLastname());
        requestRestResponseDto.setPhoneNumber(request.getContact().getPhoneNumber());
        return requestRestResponseDto;
    }
}
