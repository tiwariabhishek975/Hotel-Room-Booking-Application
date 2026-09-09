<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map,java.math.BigDecimal" %>
<% Map<String,Long> report=(Map<String,Long>)request.getAttribute("statusReport"); BigDecimal amount=(BigDecimal)request.getAttribute("confirmedAmount"); %>
<!DOCTYPE html><html><head><title>Reports</title><link rel="stylesheet" href="css/style.css"></head>
<body><div class="nav"><b>Reports</b><a href="dashboard.jsp">Dashboard</a></div>
<div class="container"><h1>Booking Report</h1>
<div class="grid">
<% for(Map.Entry<String,Long> e: report.entrySet()){ %><div class="card"><h3><%=e.getKey()%></h3><p><%=e.getValue()%> booking(s)</p></div><% } %>
<div class="card"><h3>Confirmed Amount</h3><p>₹<%=amount%></p></div>
</div></div></body></html>
