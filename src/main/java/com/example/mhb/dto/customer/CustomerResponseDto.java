package com.example.mhb.dto.customer;

import com.example.mhb.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Customer response object")
public class CustomerResponseDto {

    private Long id;
    private String name;
    private String nida;
    private String contact;
    private Gender gender;
    private String idType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
