package com.bit.shoppingmall.global;

import com.bit.shoppingmall.controller.AdminController;
import com.bit.shoppingmall.dao.CargoDao;
import com.bit.shoppingmall.service.AdminService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/main.bit")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, HttpServlet> urlMapper = new HashMap<>();
	private Logger log = Logger.getLogger("work");

	public DispatcherServlet() {
        super();
		urlMapper.put("/cargo/list",new AdminController(new AdminService(new CargoDao())));
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		request.setCharacterEncoding("UTF-8");
		String path = uri.substring(uri.lastIndexOf("/"));
		path = path.substring(1, path.lastIndexOf("."));
		log.info(path);
		//((HttpServletResponse)response).sendRedirect("main.bit");

		//String next = "main.jsp";
		//if(path != null) {
		//	next = path;
		//}
		//RequestDispatcher rd = request.getRequestDispatcher(next);
		//rd.forward(request, response);
		//RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
		//rd.forward(request, response);
	}
	
	
	

}





