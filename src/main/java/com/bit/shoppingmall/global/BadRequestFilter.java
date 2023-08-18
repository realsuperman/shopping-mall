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
		// 1. request 파라미터를 이용한 요청 필터 작업 수행
		request.setCharacterEncoding("UTF-8");
		String uri = ((HttpServletRequest)request).getRequestURI();

		String path = uri.substring(uri.indexOf("/")+1);
		String[] urlParts = path.split("/");

		if(!urlParts[0].equals("static")){
			RequestDispatcher rd = request.getRequestDispatcher("/"+path+".bit");
			rd.forward(request, response);
			return;
		}

		// 2. 체인의 다음 필터 처리
		chain.doFilter(request, response);

		// 3. response 를 이용한 요청 필터링 작업 수행
	}
}