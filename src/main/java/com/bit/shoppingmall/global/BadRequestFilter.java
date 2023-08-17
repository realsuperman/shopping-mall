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
		// �� �κп��� static�� �ִ� �͵��� �׳� main.servlet�� ��Ÿ�� ���
		// �׸��� �̰� �̻��Ѱ� �̰� ��� �׳� ���� ���� ���ٴ� ���� ��ü�� ���⼭ new�ϴ� ���ݾ� ����
		// �̰� �ణ �̻���



		// 1. request �Ķ���͸� �̿��� ��û ���� �۾� ����
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

		// 2. ü���� ���� ���� ó��
		chain.doFilter(request, response);
		
		// 3. response �� �̿��� ��û ���͸� �۾� ����
	}
}