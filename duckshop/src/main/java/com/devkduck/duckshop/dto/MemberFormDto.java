package com.devkduck.duckshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

//회원가입 화면으로부터 넘어오는 가입정보를 담을 dto
@Getter
@Setter
public class MemberFormDto {
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotEmpty(message = "이메일을 필수 입력값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Length(min=8, max=16, message= "비밀번호 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = " 주소는 필수 입력 값입니다.")
    private String address;
}
