package com.oms.msinventory.exception;

import com.oms.msinventory.exception.custom.BadRequestException;
import com.oms.msinventory.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
    log.info("handleNotFoundException exception: {}", e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
    log.info("handleBadRequestException exception: {}", e.getMessage());
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleInternalException(Exception e) {
    log.error("handleInternalException exception: {}", exceptionStacktraceToString(e));
    return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private String exceptionStacktraceToString(Exception e) {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      try (PrintStream printStream = new PrintStream(byteArrayOutputStream)) {
        e.printStackTrace(printStream);
      }
      return byteArrayOutputStream.toString();
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

}
