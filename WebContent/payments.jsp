<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.payment"%>
<%@page import="java.sql.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Service</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.3.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-8">
				<h1 class="m-3">Payments Management</h1>

				<form id="formPayment" name="formPayment" method="post" action="payments.jsp">
					Patient name: <input id="paymentName" name="paymentName" type="text" class="form-control form-control-sm"> <br> 
					Appointment Number: <input id="aptId" name="aptId" type="text" class="form-control form-control-sm"> <br> 
					Time: <input id="time" name="time" type="text" class="form-control form-control-sm"> <br>
					Payment Date: <input id="paymentDate" name="paymentDate" type="text" class="form-control form-control-sm"> <br>
					Payment Amount: <input id="paymentAmount" name="paymentAmount" type="text" class="form-control form-control-sm"> <br>
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				
				<br>
				<div id="divPaymentsGrid">
					<%
					payment pObj = new payment();
					out.print(pObj.viewPayment());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>