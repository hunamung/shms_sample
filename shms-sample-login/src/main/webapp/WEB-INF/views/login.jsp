<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Static content -->
<title>Spring Boot</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
  <h1>SHMS LOGIN WEB</h1>
  <form name="user_info" action="http://localhost:8080/login/getToken" method="post">
    <fieldset style = "width:150">
        <legend>로그인 정보 입력</legend>
            시스템ID : <input type="text" name="username" id="username" value="taes"/><br><br>
            시스템PW : <input type="text" name="password" id="password" value="luke"/><br><br>
        <!--<input id="submit_btn" type="button" value="로그인"/>-->
        <input id="submit_btn" type="button" value="로그인"/>
    </fieldset>
  </form>
  

  <fieldset style = "width:150">
    <legend>생성된 토큰 정보</legend>
    <textarea id="token_info" name="token" rows="5" cols="100"></textarea><br><br>
  </fieldset>

  <fieldset style = "width:150">
    <legend>ACCESS 토큰 입력</legend>
    <textarea id="input_access_token" name="access_token" rows="5" cols="100"></textarea><br><br>
    <input id="redirect_btn" type="button" value="사용자정보호출"/>
  </fieldset>
  
  <fieldset style = "width:150">
    <legend>ACCESS 토큰 재발행</legend>
    REFRESH 토큰 입력<br><textarea id="input_refresh_token" name="input_refresh_token" rows="5" cols="100"></textarea><br><br>
    재발행된 ACCESS 토큰<br><textarea id="access_token_info" name="access_token_info" rows="5" cols="100"></textarea><br><br>
    <input id="refresh_btn" type="button" value="재발행"/>
  </fieldset>
  
  <script>
    $(function() {
        $("#submit_btn").on("click", function() {
            $.ajax({
              "type": "POST",
              "url": "http://localhost:8080/login/getToken",
              /*
              "headers": {
                "Accept": "application/json",
                "Authorization": "Basic " + btoa(username + ":" + password)
              },
              */
              "data": {
                "username": $('#username').val(),
                "password": $('#password').val()
              },
              "success": function(response) {
                console.log(response);
                $("#token_info").val(JSON.stringify(response));
              },
              "error": function(errorThrown) {}
            });
        });
        
        $("#redirect_btn").on("click", function() {
            $.ajax({
              "type": "GET",
              "url": "http://localhost:8080/user/info",
              "headers": {
                //"Accept": "application/json",
                "Authorization": "Bearer  " + $('#input_access_token').val()
              },
              "success": function(response) {
                alert(response);
              },
              "error": function(errorThrown) {}
            });
        });
        
        $("#refresh_btn").on("click", function() {
            $.ajax({
              "type": "POST",
              "url": "http://localhost:8080/login/refreshToken",
              "data": {
                "refresh_token": $('#input_refresh_token').val()
              },
              "success": function(response) {
                console.log(response);
                $("#access_token_info").val(JSON.stringify(response));
              },
              "error": function(errorThrown) {}
            });
        });
    });
  </script>
</body>
</html>