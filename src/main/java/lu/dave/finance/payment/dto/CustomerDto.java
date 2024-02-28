package lu.dave.finance.payment.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CustomerDto {

        private Long id;

        @NotBlank(message = "Your Customer needs a name.")
        @Size(min = 3, max = 30)
        private String name;

        @NotBlank(message = "email should  present and follow this regular expression .+[@].+[\\.].+. ")
        @Size(min = 1, max = 200)
        @Email(regexp = ".+[@].+[\\.].+")
        private String email;

        @NotNull
        @DateTimeFormat( iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate dateOfBirth;
}
