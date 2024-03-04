package lu.dave.finance.payment.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class ErrorDto {
    private LocalDateTime timestamp;
    private int status;
    private List<String> error;
    private String path;

    public ErrorDto(int status, List<String> messages, HttpServletRequest httpServletRequest) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.path = httpServletRequest.getRequestURI();
        this.error = messages;
    }
    public ErrorDto(int status, String message, HttpServletRequest httpServletRequest) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.path = httpServletRequest.getRequestURI();
        this.error = List.of(message);
    }
}
