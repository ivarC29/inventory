package com.allicaihub.inventory.web.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private String code;
    private String message;

}
