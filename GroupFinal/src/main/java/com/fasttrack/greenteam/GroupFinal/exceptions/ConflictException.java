package com.fasttrack.greenteam.GroupFinal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class ConflictException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = -3589848691875090488L;

    private String message;

}
