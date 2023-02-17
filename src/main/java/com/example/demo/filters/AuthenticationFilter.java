package com.example.demo.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log(">>> AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.context.log("Working \'doFilter\' in AuthenticationFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();

        this.context.log("Requested Resource::http://localhost:8080" + uri);

        HttpSession session = req.getSession(false);

        if (session == null && !(uri.endsWith("demo/saveServlet") || uri.endsWith("demo/loginServlet") ||
                uri.endsWith("demo/viewServlet") || uri.endsWith("demo/viewByIDServlet") ||
                uri.endsWith("demo/deleteServlet") || uri.endsWith("demo/putServlet") ||
                uri.endsWith("demo/saveGameServlet") || uri.endsWith("demo/putGameServlet") ||
                uri.endsWith("demo/viewGameServlet") || uri.endsWith("demo/viewByIDGameServlet") ||
                uri.endsWith("demo/deleteGameServlet"))) {
            this.context.log("<<< Unauthorized access request");
            PrintWriter out = res.getWriter();
            if (uri.endsWith("demo/logoutServlet")){
                out.println("You are already logout");
            } else out.println("No access to: " + uri);
        } else {
            if (session != null) {
                this.context.log("::Session ID: " + session.getId());
                this.context.log("::Creation time: " + new Date(session.getCreationTime()));
                this.context.log("::Last accessed time: " + new Date(session.getLastAccessedTime()));
            }
            Cookie method = new Cookie("method", req.getMethod());
            Cookie contextPath = new Cookie("context", req.getContextPath());
            method.setMaxAge(30 * 60);
            contextPath.setMaxAge(30 * 60);
            res.addCookie(method);
            res.addCookie(contextPath);
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        //close any resources here
        this.context.log("<<< AuthenticationFilter destroyed");
    }
}
