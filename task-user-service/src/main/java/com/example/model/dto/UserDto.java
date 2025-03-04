package com.example.model.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Mật khẩu phải có ít nhất 8 ký tự, chứa ít nhất một chữ cái, một số và một ký tự đặc biệt")
    private String password;

    @NotBlank(message = "Vai trò không được để trống")
    @Pattern(regexp = "ROLE_ADMIN|ROLE_CUSTOMER", message = "Vai trò chỉ có thể là ADMIN hoặc CUSTOMER")
    private String role;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 3, max = 50, message = "Họ tên phải từ 3 đến 50 ký tự")
    private String fullName;
}
