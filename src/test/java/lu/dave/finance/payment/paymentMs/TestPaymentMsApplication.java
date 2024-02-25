package lu.dave.finance.payment.paymentMs;

import lu.dave.finance.payment.PaymentMsApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPaymentMsApplication {

	public static void main(String[] args) {
		SpringApplication.from(PaymentMsApplication::main).with(TestPaymentMsApplication.class).run(args);
	}

}
