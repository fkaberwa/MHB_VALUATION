package com.example.mhb.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseDto {
    private Long id;
    private String username;
    private String role;
}
