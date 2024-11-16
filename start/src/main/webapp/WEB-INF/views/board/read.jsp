<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Read Board</title>
</head>
<body>
    <h2>${board.title}</h2>
    <p>${board.content}</p>
    <p>Writer: ${board.writer}</p>
    <p>Created Date: ${board.regdate}</p>
    <a href="modify?bno=${board.bno}">Modify</a>
    <form action="delete" method="post" style="display:inline;">
        <input type="hidden" name="bno" value="${board.bno}" />
        <input type="submit" value="Delete" />
    </form>
    <a href="list">Back to List</a>
</body>
</html>
