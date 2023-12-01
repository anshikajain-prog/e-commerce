package cn.tech.controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			if(request.getSession().getAttribute("auth")!=null)
			{
				request.getSession().removeAttribute("auth");
				response.sendRedirect("login.jsp");
			}
			else {
				response.sendRedirect("index.jsp");
			}
		}
		catch(IOException ex) {
			out.println(ex.getStackTrace());
		}
	}
}
