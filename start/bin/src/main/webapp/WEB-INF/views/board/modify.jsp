<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Modify Board</title>
</head>
<body>
    <h2>Modify Board</h2>
    <form action="modify" method="post">
        <input type="hidden" name="bno" value="${board.bno}" />
        Title: <input type="text" name="title" value="${board.title}" /><br />
        Content: <textarea name="content">${board.content}</textarea><br />
        <input type="submit" value="Modify" />
    </form>
</body>
</html>
