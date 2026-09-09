<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.hotel.model.User" %>
<% User user=(User)session.getAttribute("user"); %>
<!DOCTYPE html><html><head><title>User Dashboard</title><link rel="stylesheet" href="../css/style.css"></head>
<body>
<div class="nav"><b>Hotel Booking</b><span>Welcome, <%= user.getName() %></span><a href="../logout">Logout</a></div>
<div class="container">
<h1>User Dashboard</h1>
<div class="grid">
<a class="card" href="../rooms">Search Rooms</a>
<a class="card" href="bookings">My Bookings</a>
</div>
<h3>Upload Image / PDF</h3>
<form action="upload" method="post" enctype="multipart/form-data">
<input type="file" name="file" accept=".jpg,.jpeg,.png,.pdf" required>
<button class="btn">Upload</button>
</form>
</div></body></html>
