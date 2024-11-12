<!-- ViewTasks.jsp -->
<%@ page import="java.sql.*" %>
<p align="right">
	<a href="./LogoutServlet">Logout</a>
</p>
<table align="center" width="70%" border="1">
	<tr>
		<th>ID</th>
		<th>Name</th>
		<th>Date</th>
		<th>Status</th>
		<th></th>
	</tr>
	<%
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "pat", "pat");
			Statement stmt=con.createStatement();
			String email=(String)session.getAttribute("email");
			ResultSet rs=stmt.executeQuery("select * from tasks where regid=(select regid from register where email='"+email+"')");
			while(rs.next()) {
			%>
			<tr>
				<%
					int taskid=rs.getInt(1);
				%>
				<td><%=taskid%></td>
				<td><%=rs.getString(2)%></td>	
				<td><%=rs.getString(3)%></td>
				<%
					int taskStatus=rs.getInt(4);
					int regid=rs.getInt(5);
					if(taskStatus==3) {
				%>
					<td><strike><font color="black">Completed</font></strike></td>
				<%
					} else {
				%>
					<td><a href="./TaskCompletedServlet?taskid=<%=taskid%>"><font color="red">Completed</font></a></td>
				<%		
					}
				%>	
			</tr>
			<%
			}// while()
			rs.close(); stmt.close(); con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	%>
</table>

<form method="post" action="./AddTaskServlet">
	<table align="center" border="1" width="30%">
		<tr>
			<th>Task Name</th>
			<td><input type="text" name="taskName"></td>
		</tr>
		<tr>
			<th>Task Date</th>
			<td><input type="text" name="taskDate" placeholder="dd-mm-yyyy"></td>
		</tr>
		<tr>
			<th>Task Status</th>
			<td>
				<select name="taskStatus">
					<option value="1">Not Yet Started</option>
					<option value="2">Pending</option>
					<option value="3">Completed</option>
				</select>
			</td>
		</tr>
		<tr>
			<th><input type="submit" name="submit" value="Add Task"></th>
			<td><input type="reset" name="reset" value="Clear"></td>
		</tr>
	</table>
</form>
