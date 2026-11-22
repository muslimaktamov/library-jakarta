<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.pdp.library.entity.Book" %>
<%@ page import="uz.pdp.library.enums.Role" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    boolean isAdmin = Role.ADMIN == (session.getAttribute("role"));
%>
<html>
<head>
    <title>Book list</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<jsp:include page="/fragments/navbar.jsp"/>

<div class="container mt-4">
    <h1 class="mb-4">Book list</h1>

    <% if (books == null || books.isEmpty()) { %>
    <div class="alert alert-info">Hozircha kitob yo'q.</div>
    <% } else { %>
    <table class="table table-striped align-middle">
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th style="width: 260px;">Amallar</th>
        </tr>
        </thead>
        <tbody>
        <% int i = 1; %>
        <% for (Book book : books) { %>
        <tr>
            <td><%= i++ %>
            </td>
            <td><%= book.getTitle() %>
            </td>
            <td>
                <a href="/book/details?id=<%= book.getId() %>" class="btn btn-sm btn-primary">Details</a>
                <% if (isAdmin) { %>
                <a href="/admin/book/update?id=<%= book.getId() %>" class="btn btn-sm btn-warning">Update</a>
                <a href="/admin/book/delete?id=<%= book.getId() %>" class="btn btn-sm btn-danger">Delete</a>
                <% } %>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>

<jsp:include page="/fragments/js.jsp"/>
</body>
</html>