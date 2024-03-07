package lu.dave.finance.payment.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1)
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        // Log request details (method, URI, headers, etc.)
        log.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        log.info("Response: {}", response.getStatus());
        filterChain.doFilter(request, response);
    }
}
