<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html><head><title>Register</title><link rel="stylesheet" href="css/style.css"></head>
<body>
<div class="container small">
<h2>Create Account</h2>
<% if(request.getParameter("error") != null){ %><div class="error"><%= request.getParameter("error") %></div><% } %>
<form action="register" method="post">
<input name="name" placeholder="Full Name" required>
<input name="email" type="email" placeholder="Email" required>
<input name="password" type="password" minlength="6" placeholder="Password (6+ characters)" required>
<button class="btn" type="submit">Register</button>
</form>
<p>Already registered? <a href="login.jsp">Login</a></p>
</div>
</body></html>
