package academy.devdojo.springboot2_essentials.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected int status;
    protected String detais;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}
