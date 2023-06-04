package pl.dariusz.github.exception;

import lombok.Data;

@Data
public class UnsupportedHeaderObjectError {
        private Integer statusCode;
        private String message;
}
