<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="cn.tech.connection.DBConnection"%>
    <%@page import="cn.tech.model.*"%>
    <%@page import="java.util.*"%>
    <%UserModel auth=(UserModel)request.getSession().getAttribute("auth");
    if(auth!=null)
    {
    	request.setAttribute("auth",auth);
    }
    List<Product> products = DBConnection.getAllProductDetails();
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    if (cart_list != null) {
    	request.setAttribute("cart_list", cart_list);
    }
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Welcome To Shopping Cart!</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
<%@include file="includes/navbar.jsp"%>
<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
		<%
		if(!products.isEmpty())
		{
			for(Product p:products)
			{ %>
				<div class="col-md-3 my-3">
				<div class="card w-100" styles="width: 18rem;">
					<img class="card-img-top" src="product-image/<%=p.getImage()%>" width="200" height="200"
						alt="Card image cap">
					<div class="card-body">
						<h5 class="card-title"><%=p.getName() %></h5>
						<h6 class="price">Price: $<%=p.getPrice() %></h6>
						<h6 class="category">Category: <%=p.getCategory() %></h6>
						<div class="mt-3 d-flex justify-content-between">
							<a class="btn btn-dark" href="AddToCartServlet?id=<%=p.getId()%>">Add to Cart</a>
							 <a class="btn btn-primary" href="OrderNowServlet?quantity=1&id=<%=p.getId()%>">Buy Now</a>
						</div>
					</div>
				</div>
			</div>
			<%
			}
		}
		%>

		</div>
	</div>

<%@include file="includes/footer.jsp"%>
</body>
</html>