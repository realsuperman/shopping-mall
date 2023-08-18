package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.AdminController;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.service.AdminService;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.bit")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, HttpServlet> urlMapper = new HashMap<>();
	private Logger log = Logger.getLogger("work");

	public DispatcherServlet() {
        super();
		urlMapper.put("/admin",new AdminController(new AdminService(new CargoDao())));
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uri = request.getRequestURI();
		String method = request.getMethod();
		request.setCharacterEncoding("UTF-8");
		String path = uri.substring(0, uri.lastIndexOf("."));

		if(urlMapper.containsKey(path)){
			HttpServlet controller = urlMapper.get(path);
			invokeAppropriateMethod(controller, method, request, response);
			//request.setAttribute("method",method);
			//controller.service(request,response);
		}
		//TODO 매핑 핸들러 없으면 예외 던지는거 관련 생각
	}

	private void invokeAppropriateMethod(HttpServlet controller, String method, HttpServletRequest request, HttpServletResponse response) {
		try {
			Method targetMethod;

			if (method.equalsIgnoreCase("GET")) {
				targetMethod = HttpServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
			} else if (method.equalsIgnoreCase("POST")) {
				targetMethod = HttpServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
			} else if (method.equalsIgnoreCase("PUT")) {
				targetMethod = HttpServlet.class.getDeclaredMethod("doPut", HttpServletRequest.class, HttpServletResponse.class);
			} else if (method.equalsIgnoreCase("DELETE")) {
				targetMethod = HttpServlet.class.getDeclaredMethod("doDelete", HttpServletRequest.class, HttpServletResponse.class);
			} else {
				throw new UnsupportedOperationException("Unsupported HTTP method: " + method);
			}

			// 접근성 확장
			targetMethod.setAccessible(true);

			// 메소드 호출
			targetMethod.invoke(controller, request, response);
		}catch (Exception e) {
			throw new RuntimeException("Error invoking method", e);
		}
	}
}