package com.bit.shoppingmall.global;

import com.bit.shoppingmall.domain.Consumer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BadRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String uri = ((HttpServletRequest) request).getRequestURI();
        String method = ((HttpServletRequest) request).getMethod();

        String path = uri.substring(uri.indexOf("/") + 1);
        String[] urlParts = path.split("/");

        if (!urlParts[0].equals("static")) {
            RequestDispatcher rd;
            if (path.equals("")) {
                rd = request.getRequestDispatcher("/home.bit");
                if (isAdmin(request)) {
                    rd = request.getRequestDispatcher("/admin.bit");
                }
            } else {
                rd = request.getRequestDispatcher("/" + path + ".bit");
                boolean nonLoginPath = path.startsWith("user") || path.startsWith("home")
                        || path.startsWith("itemJson") || path.startsWith("categories") || path.startsWith("status");

                if (!isLogin(request)) {
                    if (!nonLoginPath) {
                        rd = request.getRequestDispatcher("/user.bit");
                    }
                } else {
                    boolean isAdminPath = (path.startsWith("admin") || path.startsWith("stock")
                            || path.startsWith("categories") || path.startsWith("status")
                            || (method.equalsIgnoreCase("POST") && path.startsWith("item")));

                    if (path.startsWith("user") || (!isAdmin(request) && isAdminPath) || (isAdmin(request) && !isAdminPath)) {
                        rd = request.getRequestDispatcher("/non-found.bit");
                    }
                }
            }
            rd.forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean isLogin(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        return session != null && session.getAttribute("login_user") != null;
    }

    private boolean isAdmin(ServletRequest request) {

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            Object loginUser = session.getAttribute("login_user");
            if (loginUser instanceof Consumer) {
                Consumer loginConsumer = (Consumer) loginUser;
                return loginConsumer.getIsAdmin() == 1;
            }
        }
        return false;
    }


}