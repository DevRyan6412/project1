<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Register Board</title>
</head>
<body>
    <h2>Register Board</h2>
    <form action="register" method="post">
        Title: <input type="text" name="title" /><br />
        Content: <textarea name="content"></textarea><br />
        Writer: <input type="text" name="writer" /><br />
        <input type="submit" value="Register" />
    </form>
</body>
</html>
