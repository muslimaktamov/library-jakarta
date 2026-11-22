<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<html>
<head>
    <title>Register authUser</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<div class="container" style="max-width: 420px; margin-top: 60px;">
    <h1 class="mb-4">Register</h1>

    <% if (errorMessage != null) { %>
    <div class="alert alert-danger"><%= errorMessage %></div>
    <% } %>

    <form method="post" <%--action="/auth/register"--%>>
        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Password</label>
            <input type="password" name="password" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Confirm password</label>
            <input type="password" name="confirmPassword" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Register</button>
    </form>

    <p class="mt-3">Akkount bormi? <a href="/auth/login">Login</a></p>
</div>
<jsp:include page="/fragments/js.jsp"/>
</body>
</html>