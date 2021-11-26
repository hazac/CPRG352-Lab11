<%-- 
    Document   : resetNewPassword
    Created on : Nov 24, 2021, 8:13:04 PM
    Author     : hazco
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>        
        <h1>Enter a new password</h1>
        <form method="POST" action="reset">
            <input type="password" name="newPassword" >
            <input type="hidden" name="uuid" value="${uuid}">
            <input type="hidden" name="action" value="changePassword">
            <input type="submit" value="submit">
        </form>
    </body>
</html>
