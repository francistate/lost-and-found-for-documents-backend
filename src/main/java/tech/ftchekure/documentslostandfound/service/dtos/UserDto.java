package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

@Data
public class UserDto {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;

    private Long groupId;

    private Boolean active;

    private Long stationId;

}
