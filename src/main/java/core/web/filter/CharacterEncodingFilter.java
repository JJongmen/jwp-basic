package core.web.filter;

import org.slf4j.Logger;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.slf4j.LoggerFactory.getLogger;

public class CharacterEncodingFilter implements Filter {
    private final Logger log = getLogger(CharacterEncodingFilter.class);
    private static final String DEFAULT_ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Initialized Character Encoding Filter!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding(DEFAULT_ENCODING);
        response.setCharacterEncoding(DEFAULT_ENCODING);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
