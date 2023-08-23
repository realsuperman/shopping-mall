<%--
  Created by IntelliJ IDEA.
  User: seong
  Date: 2023-08-23
  Time: 오후 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script>
    $(document).ready(function() { // 폼으로 보내는거 맞는 것 같다
        $('#test').on('click', function () {
            $.ajax({
                url: "/kakao/pay",
                type: "POST",
                async: false,
                success: function(result) {
                    let parsedObject = JSON.parse(JSON.stringify(result));
                    window.open(parsedObject.next_redirect_pc_url, "myPopup", "width=400,height=300");
                },
                error: function(xhr, status, error) { // 에러메시지 처리
                }
            });
        });
    });
</script>

<!DOCTYPE html>
<html>
<head></head>
<body>
    <button id="test">결재하기</button>
</body>
</html>
