package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        List<String> paths = List.of("/loginPage", "/login", "/registration", "/index");
        String uri = req.getRequestURI();
        if (paths.contains(uri) || req.getSession().getAttribute("user") != null) {
            filterChain.doFilter(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath() + "/loginPage");
    }
}
