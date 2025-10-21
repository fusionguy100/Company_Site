package com.fasttrack.greenteam.GroupFinal.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 3146054180939216847L;

    private String message;

}
