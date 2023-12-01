package cn.tech.controllers;
import cn.tech.connection.*;
import cn.tech.model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		response.setContentType("type/html");
		PrintWriter out=response.getWriter();
	    String email=request.getParameter("email");
	    String pass=request.getParameter("password");
	    UserModel user=DBConnection.getAllDetails(email,pass);
	    if(user!=null)
	    {
	    	request.getSession().setAttribute("auth",user);
	    	response.sendRedirect("index.jsp");
	    }
	    else {
	    	out.println("wrong id password");
	    }
	    	
		}
		catch(IOException ex)
		{
			
			ex.printStackTrace();
		}
	}

}