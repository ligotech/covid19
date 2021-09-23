package com.creditsuisse.covid19.exception.handler;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class Covid19ResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(Covid19ResponseEntityExceptionHandler.class);

    private class ExceptionResponseObject{
        private Date timestamp;
        private String message;
        private String details;

        public ExceptionResponseObject(Date timestamp, String message, String details) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }
    }

    @ExceptionHandler({Covid19Exception.class})
    public final ResponseEntity handleCovid19Exception(Covid19Exception ex, WebRequest request){
        logger.error("Exception:", ex.getMessage(), ex);
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity handleNotFoundException(NotFoundException ex, WebRequest request){
        logger.error("Exception:", ex.getMessage(), ex);
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, HttpStatus.NOT_FOUND);
    }

    //MongoWriteException
    @ExceptionHandler({MongoWriteException.class})
    public final ResponseEntity handleMongoWriteException(MongoWriteException ex, WebRequest request){
        logger.error("Exception:", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, httpStatus);
    }

    @ExceptionHandler({MongoBulkWriteException.class})
    public final ResponseEntity handleMongoBulkWriteException(MongoBulkWriteException ex, WebRequest request){
        logger.error("Exception:", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, httpStatus);
    }

    @ExceptionHandler({BadRequestException.class})
    public final ResponseEntity handleBadRequestException(BadRequestException ex, WebRequest request){
        logger.error("Exception:", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, httpStatus);
    }

    //@ExceptionHandler({BindException.class})
    public ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("Exception:", ex.getMessage(), ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponseObject exceptionResponseObject = new ExceptionResponseObject(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponseObject, httpStatus);
    }
}
