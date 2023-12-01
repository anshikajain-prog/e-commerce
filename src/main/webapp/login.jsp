<%@page import="cn.tech.connection.DBConnection"%>
    <%@page import="cn.tech.model.*,java.util.*"%>
    <%UserModel auth=(UserModel)request.getSession().getAttribute("auth");
    if(auth!=null)
    {
    	response.sendRedirect("index.jsp");
    }
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
	if (cart_list != null) {
		request.setAttribute("cart_list", cart_list);
	}
	
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shopping Cart Login</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
<%@include file="includes/navbar.jsp"%>
<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="LoginServlet" method="post">
					<div class="form-group">
						<label>Email address</label> 
						<input type="email" name="email" class="form-control" placeholder="Enter email" required>
					</div>
					<div class="form-group">
						<label>Password</label> 
						<input type="password" name="password" class="form-control" placeholder="Password" required>
					</div>
					<div class="text-center">
						<button type="submit" class="btn btn-primary">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%@include file="/includes/footer.jsp"%>k
</body>
</html>