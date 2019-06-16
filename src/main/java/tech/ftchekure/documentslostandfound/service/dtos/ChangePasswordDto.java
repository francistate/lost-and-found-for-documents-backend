package tech.ftchekure.documentslostandfound.service.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {

    private String oldPassword;

    private String newPassword;

    private String confirmPassword;

}
