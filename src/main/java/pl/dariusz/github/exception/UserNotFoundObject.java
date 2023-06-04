package pl.dariusz.github.exception;

import lombok.Data;

@Data
public class UserNotFoundObject {

    private Integer statusCode;
    private String message;
}
