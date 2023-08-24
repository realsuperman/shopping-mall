
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" >
<head>
  <meta charset="UTF-8">
  <title>CodePen - Responsive Signup/Login form</title>
  <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700|Raleway:300,600" rel="stylesheet">
  <meta name="viewport" content="width=device-width, initial-scale=1"><link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'><link rel="stylesheet" href="./style.css">

  <!-- Css Styles -->
  <link rel="stylesheet" href="../../static/css/userstyle.css" type="text/css">

</head>
<body>
<!-- partial:index.partial.html -->
<div class="container">
  <section id="formHolder">

    <div class="row">

      <!-- Brand Box -->
      <div class="col-sm-6 brand">
        <a href="#" class="logo">MR <span>.</span></a>

        <div class="heading">
          <h2>Marina</h2>
          <p>Your Right Choice</p>
        </div>
      </div>


      <!-- Form Box -->
      <div class="col-sm-6 form">

        <!-- Login Form -->
        <div class="login form-peice">
          <form class="login-form" action="/user" method="post">
            <div class="form-group">
              <label for="loginemail">Email Adderss</label>
              <input type="email" name="email" id="loginemail" required>
            </div>

            <div class="form-group">
              <label for="loginPassword">Password</label>
              <input type="password" name="password" id="loginPassword" required>
            </div>

            <div class="CTA">
              <input type="submit" value="Login">
              <a href="#" class="switch">I'm New</a>
            </div>
          </form>
        </div><!-- End Login Form -->


        <!-- Signup Form -->
        <div class="signup form-peice switched">
          <form class="signup-form" action="/user/sign-up" method="post">

            <div class="form-group">
              <label for="email">Email</label>
              <input type="email" name="email" id="email" class="email">
              <span class="error"></span>
            </div>

            <div class="form-group">
              <label for="password">Password</label>
              <input type="password" name="password" id="password" class="pass">
              <span class="error"></span>
            </div>

            <div class="form-group">
              <label for="passwordCon">Confirm Password</label>
              <input type="password" name="passwordCon" id="passwordCon" class="passConfirm">
              <span class="error"></span>
            </div>

            <div class="form-group">
              <label for="name">Name</label>
              <input type="text" name="username" id="name" class="name">
              <span class="error"></span>
            </div>

            <div class="form-group">
              <label for="phone">Phone Number</label>
              <input type="text" name="phone_number" id="phone">
            </div>

            <div class="form-group">
              <label for="address">Address</label>
              <input type="text" name="address" id="address" class="address">
              <input type="text" name="address_detail" id="address_detail">
            </div>

            <div class="CTA">
              <input type="submit" value="Signup Now">
              <a href="#" class="switch">계정 생성</a>
            </div>
          </form>
        </div><!-- End Signup Form -->
      </div>
    </div>

  </section>

  <!-- 에러 메시지 alert -->
  <div id="errorSection">
    <%@ include file="errorMsgAlert.jsp" %>
  </div>

  <footer>
    <p>
      Footer
    </p>
  </footer>

</div>
<!-- partial -->
<script src='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
<script  src="../../static/js/userscript.js"></script>

<%-- 다음 주소 api --%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="../../static/js/address-api.js"></script>

<%-- 데이터 유효성 검사 back --%>
<script>
  function validation(){
    let formData = {
      email : $('#email').val(),
      password: $('#password').val(),
      username: $('#username').val(),
      address : $('#address').val(),
      address_detail : $('#address_detail').val(),
      phone_number : $('#phone_number').val(),

    };
    let returnValue;

    $.ajax({
      url: "/user-validation/sign-up",
      type: "POST",
      data: formData,
      async: false,
      success: function(response) {
        returnValue = true;
      },error: function(jqXHR, textStatus, errorThrown) {
        alert(jqXHR.responseText);
        returnValue = false;
      }
    });
    return returnValue;
  }

  $("#signup-form").submit(function(event) {

    if(!validation()){ // 백엔드 체크
      event.preventDefault();
    }
  })

</script>


</body>
</html>

