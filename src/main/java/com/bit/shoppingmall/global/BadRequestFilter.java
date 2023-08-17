package com.bit.shoppingmall.global;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BadRequestFilter implements Filter{
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 이 부분에서 static에 있는 것들은 그냥 main.servlet을 안타고 통과
		// 그리고 이거 이상한게 이거 사실 그냥 다음 필터 쓴다는 개념 자체가 여기서 new하는 거잖아 ㅋㅋ
		// 이게 약간 이상해



		// 1. request 파라미터를 이용한 요청 필터 작업 수행
		request.setCharacterEncoding("UTF-8");
		String uri = ((HttpServletRequest)request).getRequestURI();
		//work_log.debug("doFilter----"+uri);

		String path = uri.substring(uri.indexOf("/"));
		System.out.println(path);
		//work_log.debug("doFilter----"+path);
		//System.out.println(path);
		if(path.equals("/")) {
			((HttpServletResponse)response).sendRedirect("main.bit");
			return;
		}

		// 2. 체인의 다음 필터 처리
		chain.doFilter(request, response);
		
		// 3. response 를 이용한 요청 필터링 작업 수행
	}
}