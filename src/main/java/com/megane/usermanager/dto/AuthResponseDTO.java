package com.megane.usermanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO<Void> {
    public int status;
    public String msg;
    public String resendEmail;
    public String complete;

    public AuthResponseDTO(int status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
        this.resendEmail = resendEmail;
        this.complete = complete;
    }
}
