package com.bit.shoppingmall.global;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BadRequestFilter implements Filter{
	@Override
	public void init(FilterConfig filterConfig){}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		String uri = ((HttpServletRequest)request).getRequestURI();

		String path = uri.substring(uri.indexOf("/")+1);
		String[] urlParts = path.split("/");

		if(!urlParts[0].equals("static")){
			RequestDispatcher rd;
			if(path.equals("")){
				rd = request.getRequestDispatcher("/home.bit");
				if(isAdmin(request)){
					rd = request.getRequestDispatcher("/admin.bit");
				}
			}else{
				if(path.startsWith("admin") && !isAdmin(request)){
					throw new RuntimeException(); // TODO
				}
				rd = request.getRequestDispatcher("/"+path+".bit");
			}

			rd.forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isAdmin(ServletRequest request) {
		//request.getAttribute("user");
		return true;
	}
}