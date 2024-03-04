package lu.dave.finance.payment.paymentMs.service;

import lu.dave.finance.payment.entity.enumaration.MovementType;
import lu.dave.finance.payment.service.MovementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class MovementServiceTest {

    MovementServiceImpl movementService;

    @Test
    void contextLoads() {
    }
}
