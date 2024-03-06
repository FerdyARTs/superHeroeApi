package com.plexus.superHeroeApi.controller.exception_handler;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class  ErrorResponse {

    private int httpStatus;

    private String type, message, clazz, method;

    private int line;

}
