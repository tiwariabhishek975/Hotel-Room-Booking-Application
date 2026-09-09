<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html><head><title>Login</title><link rel="stylesheet" href="css/style.css"></head>
<body>
<div class="container small">
<h2>Login</h2>
<% if(request.getParameter("error") != null){ %><div class="error"><%= request.getParameter("error") %></div><% } %>
<% if(request.getParameter("registered") != null){ %><div class="success">Registration successful. Please login.</div><% } %>
<form action="login" method="post">
<input name="email" type="email" placeholder="Email" required>
<input name="password" type="password" placeholder="Password" required>
<button class="btn" type="submit">Login</button>
</form>
<p>New user? <a href="register.jsp">Register</a></p>
</div>
</body></html>
