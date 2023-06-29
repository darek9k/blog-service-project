package darek9k.tracking;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@Order(2)
public class LoggingFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("2Start request: {}", request);

        long start = System.currentTimeMillis();

        chain.doFilter(request,response);

        long end = System.currentTimeMillis();
        log.info("2Stop request {}, took {} ms", request,(end-start));
    }
}
