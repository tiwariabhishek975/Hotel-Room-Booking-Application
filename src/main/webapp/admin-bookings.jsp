<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.hotel.model.Booking" %>
<% List<Booking> bookings=(List<Booking>)request.getAttribute("bookings"); %>
<!DOCTYPE html><html><head><title>Manage Bookings</title><link rel="stylesheet" href="css/style.css"></head>
<body><div class="nav"><b>Admin - Bookings</b><a href="dashboard.jsp">Dashboard</a></div>
<div class="container"><table><tr><th>ID</th><th>User</th><th>Room</th><th>Dates</th><th>Status</th><th>Action</th></tr>
<% for(Booking b: bookings){ %>
<tr><td><%=b.getId()%></td><td><%=b.getUserId()%></td><td><%=b.getRoomId()%></td><td><%=b.getCheckInDate()%> → <%=b.getCheckOutDate()%></td><td><%=b.getStatus()%></td>
<td><form method="post" action="bookings"><input type="hidden" name="id" value="<%=b.getId()%>"><select name="status"><option>CONFIRMED</option><option>CHECKED_IN</option><option>CHECKED_OUT</option><option>CANCELLED</option></select><button class="btn">Update</button></form></td></tr>
<% } %></table></div></body></html>
