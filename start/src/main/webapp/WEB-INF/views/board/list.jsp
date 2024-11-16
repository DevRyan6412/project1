<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Board List</title>
</head>
<body>
    <h2>Board List</h2>
    <a href="register">Register</a>
    <table border="1">
        <tr>
            <th>BNO</th>
            <th>Title</th>
            <th>Writer</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="board" items="${boards}">
            <tr>
                <td>${board.bno}</td>
                <td><a href="read?bno=${board.bno}">${board.title}</a></td>
                <td>${board.writer}</td>
                <td>
                    <a href="modify?bno=${board.bno}">수정</a>
                    <form action="delete" method="post" style="display:inline;">
                        <input type="hidden" name="bno" value="${board.bno}" />
                        <input type="submit" value="Delete" />
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
