package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO<T> {
    public int status;
    public String msg;
    public String resendEmail;
    public String complete;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T error;

    public AuthResponseDTO(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
        this.resendEmail = resendEmail;
        this.complete = complete;
    }
}
