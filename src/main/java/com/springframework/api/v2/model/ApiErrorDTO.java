package com.springframework.api.v2.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Data
public class ApiErrorDTO {
  private HttpStatus status;
  private String message;
  private List<String> errors;

  public ApiErrorDTO(HttpStatus status, String error) {
    super();
    this.status = status;
    this.message = "Unexpected error";
    errors = Collections.singletonList(error);
  }

  public ApiErrorDTO(HttpStatus status, String message, List<String> errors) {
    super();
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public ApiErrorDTO(HttpStatus status, String message, String error) {
    super();
    this.status = status;
    this.message = message;
    errors = Collections.singletonList(error);
  }
}
