package estm.dsic.jee.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;

@WebFilter("/*")
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Allow requests from any origin
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // Allow GET, POST, OPTIONS methods
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        // Allow specific headers
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // Allow credentials
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}