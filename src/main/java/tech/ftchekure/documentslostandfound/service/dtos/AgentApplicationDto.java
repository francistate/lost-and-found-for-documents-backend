package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

@Data
public class AgentApplicationDto {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;

    private String stationName;

    private String stationAddress;

    private String stationLocation;
}
