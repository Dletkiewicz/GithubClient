package pl.dariusz.github.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderResponse {

    private int status;
    private String message;
}
