<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.hotel.model.Booking" %>
<% List<Booking> bookings=(List<Booking>)request.getAttribute("bookings"); %>
<!DOCTYPE html><html><head><title>My Bookings</title><link rel="stylesheet" href="../css/style.css"></head>
<body><div class="nav"><b>My Bookings</b><a href="dashboard.jsp">Dashboard</a></div>
<div class="container"><table><tr><th>ID</th><th>Room</th><th>Check-in</th><th>Check-out</th><th>Status</th><th>Total</th></tr>
<% for(Booking b: bookings){ %><tr><td><%=b.getId()%></td><td><%=b.getRoomId()%></td><td><%=b.getCheckInDate()%></td><td><%=b.getCheckOutDate()%></td><td><%=b.getStatus()%></td><td>₹<%=b.getTotalAmount()%></td></tr><% } %>
</table></div></body></html>
