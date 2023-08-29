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

        boolean mainCommonPath = path.startsWith("categories") || path.startsWith("status")
                || path.startsWith("itemDetail") || (method.equalsIgnoreCase("GET") && path.startsWith("item"))
                || path.startsWith("home") || path.startsWith("itemJson");

        boolean needLoginPath = path.startsWith("user"); // 로그인 필수 페이지

        boolean isAdminPath = (path.startsWith("admin") || path.startsWith("stock")
                || (method.equalsIgnoreCase("POST") && path.startsWith("item")) || path.startsWith("upload"));

        if (!urlParts[0].equals("static")) {
            RequestDispatcher rd = request.getRequestDispatcher("/" + path + ".bit");

            if (path.isEmpty()) {
                rd = request.getRequestDispatcher("/home.bit");
                if (isAdmin(request)) {
                    rd = request.getRequestDispatcher("/admin.bit");
                }
            } else if (!mainCommonPath) {
                if (!isLogin(request) && !needLoginPath ) {
                    rd = request.getRequestDispatcher("/user.bit");
                } else if (isLogin(request) && needLoginPath) {
                    rd = request.getRequestDispatcher("/non-found.bit");
                } else if (!path.startsWith("logout") && (isAdmin(request) != isAdminPath) ) {
                    rd = request.getRequestDispatcher("/non-found.bit");
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
        return isLogin(request) && ((Consumer)((HttpServletRequest) request).getSession(false).getAttribute("login_user")).getIsAdmin() == 1;
    }
}