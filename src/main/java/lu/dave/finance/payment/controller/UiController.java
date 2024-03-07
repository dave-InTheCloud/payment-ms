package lu.dave.finance.payment.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@Hidden
public class UiController {
    @RequestMapping(value = "/{path:[^\\.]*}")
    public String redirect(HttpServletRequest request) {
        log.warn("redirect ot index.html, No proper router for SPA," +
                " could have side effect check url :  {}", request.getRequestURI());
        return "forward:/";
    }
}
