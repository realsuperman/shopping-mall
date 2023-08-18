package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.AdminController;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.service.AdminService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("*.bit")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, HttpServlet> urlMapper = new HashMap<>();
	private Logger log = Logger.getLogger("work");

	public DispatcherServlet() {
        super();
		urlMapper.put("/cargo/get",new AdminController(new AdminService(new CargoDao())));
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String method = request.getMethod();
		request.setCharacterEncoding("UTF-8");
		String path = uri.substring(0, uri.lastIndexOf("."));

		if(urlMapper.containsKey(path)){
			HttpServlet controller = urlMapper.get(path);
			request.setAttribute("method",method);
			controller.service(request,response);
		}
	}

}





