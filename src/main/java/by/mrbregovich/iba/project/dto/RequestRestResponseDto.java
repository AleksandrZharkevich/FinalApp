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

    public static RequestRestResponseDto of(Request request) {
        RequestRestResponseDto requestRestResponseDto = new RequestRestResponseDto();
        requestRestResponseDto.setId(request.getId());
        requestRestResponseDto.setRegion(request.getContact().getRegion());
        requestRestResponseDto.setDistrict(request.getContact().getDistrict());
        requestRestResponseDto.setCity(request.getContact().getCity());
        requestRestResponseDto.setStreetAddress(request.getContact().getStreetAddress());
        return requestRestResponseDto;
    }
}
