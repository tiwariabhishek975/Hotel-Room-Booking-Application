<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,com.hotel.model.Room" %>
<%
List<Room> rooms=(List<Room>)request.getAttribute("rooms");
int pageNo=(Integer)request.getAttribute("page");
int totalPages=(Integer)request.getAttribute("totalPages");
%>
<!DOCTYPE html><html><head><title>Rooms</title><link rel="stylesheet" href="css/style.css"></head>
<body>
<div class="nav"><b>Available Rooms</b><a href="index.jsp">Home</a></div>
<div class="container">
<form method="get" action="rooms" class="search">
<input name="keyword" placeholder="Room number / type">
<select name="type"><option value="">All Types</option><option>STANDARD</option><option>DELUXE</option><option>SUITE</option></select>
<button class="btn">Search</button>
</form>
<% if(request.getParameter("error") != null){ %><div class="error"><%=request.getParameter("error")%></div><%}%>
<div class="grid">
<% for(Room r: rooms){ %>
<div class="room card">
<h3>Room <%=r.getRoomNumber()%></h3>
<p>Type: <%=r.getRoomType()%></p>
<p>Capacity: <%=r.getCapacity()%></p>
<p>Price: ₹<%=r.getPrice()%> / night</p>
<% if(session.getAttribute("user") != null){ %>
<form action="user/book" method="post">
<input type="hidden" name="roomId" value="<%=r.getId()%>">
<input type="date" name="checkIn" required>
<input type="date" name="checkOut" required>
<button class="btn">Book</button>
</form>
<% } else { %><a class="btn" href="login.jsp">Login to Book</a><% } %>
</div>
<% } %>
</div>
<div class="pagination">
<% for(int p=1;p<=totalPages;p++){ %><a href="rooms?page=<%=p%>"><%=p%></a><% } %>
</div>
</div></body></html>
