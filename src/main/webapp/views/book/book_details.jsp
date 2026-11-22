<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="uz.pdp.library.entity.Book" %>
<%@ page import="uz.pdp.library.entity.Upload" %>
<%@ page import="uz.pdp.library.enums.Role" %>
<%
    Book book = (Book) request.getAttribute("book");
    boolean isAdmin = Role.ADMIN == session.getAttribute("role");
    Upload file = book.getFile();
    String description = book.getDescription() == null ? "" : book.getDescription();
    boolean isImage = file != null
            && java.util.List.of("png", "jpg", "jpeg", "gif", "webp", "bmp")
            .contains(file.getExtension().toLowerCase());
%>
<html>
<head>
    <title>Book details</title>
    <jsp:include page="/fragments/css.jsp"/>
</head>
<body>
<jsp:include page="/fragments/navbar.jsp"/>

<div class="container mt-4" style="max-width: 720px;">
    <h1 class="mb-4"><%= book.getTitle() %>
    </h1>

    <table class="table">
        <tr>
            <th style="width: 160px;">Title</th>
            <td><%= book.getTitle() %>
            </td>
        </tr>
        <tr>
            <th>Description</th>
            <td><%= description %>
            </td>
        </tr>
        <tr>
            <th>File</th>
            <td>
                <% if (file == null) { %>
                <span class="text-muted">fayl yuklanmagan</span>
                <% } else { %>
                <a href="/file/<%= file.getId() %>" target="_blank">
                    <%= file.getOriginName() %>
                </a>
                <span class="text-muted">(<%= file.getSize() / 1024 %> KB)</span>
                <a href="/file/<%= file.getId() %>?download=true" class="btn btn-sm btn-outline-primary ms-2">
                    Download
                </a>
                <% } %>
            </td>
        </tr>
        <tr>
            <th>Created at</th>
            <td><%= book.getCreatedAt() %>
            </td>
        </tr>
        <tr>
            <th>Updated at</th>
            <td><%= book.getUpdatedAt() %>
            </td>
        </tr>
    </table>

    <% if (isImage) { %>
    <div class="mb-4">
        <img src="/file/<%= file.getId() %>" alt="" class="img-fluid rounded border" style="max-height: 400px;">
    </div>
    <% } %>

    <a href="/book/list" class="btn btn-secondary">Back</a>
    <% if (isAdmin) { %>
    <a href="/admin/book/update?id=<%= book.getId() %>" class="btn btn-warning">Update</a>
    <a href="/admin/book/delete?id=<%= book.getId() %>" class="btn btn-danger">Delete</a>
    <% } %>
</div>

<jsp:include page="/fragments/js.jsp"/>
</body>
</html>