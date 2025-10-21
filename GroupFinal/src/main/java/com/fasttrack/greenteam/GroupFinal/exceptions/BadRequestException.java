package com.fasttrack.greenteam.GroupFinal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3776307881335862810L;

    private String message;
}
