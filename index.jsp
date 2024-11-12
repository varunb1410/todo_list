<!-- index.jsp -->
<html>
	<head>
		<title>Login Form</title>
	</head>
	<body>
		<form	method="post" action="./LoginServlet">
			<table width="30%" align="center" border="1" >
				<tr>
					<th>Email</th>
					<td><input type="text" name="email" ></td>
				</tr>
				<tr>
					<th>Pass</th>
					<td><input type="password" name="pass" ></td>
				</tr>
				<tr>
					<th><input type="submit" name="submit" value="Login"></th>
					<td><input type="reset" name="reset" value="Clear"></td>
				</tr>
			</table>
		</form>
		<center><%=request.getAttribute("error")%></center>
		<center><a href="./Register.html">SignUp</a></center>
	</body>
</html>
