<%--
  Created by IntelliJ IDEA.
  User: seong
  Date: 2023-08-24
  Time: 오전 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="./commonScript.jsp" %>
<script>
    $(document).ready(function() {
        let queryString = window.location.search;
        let params = new URLSearchParams(queryString);
        if(params.get('mode')==='fail'){ // 결제 취소 or 실패
            alert("결제가 정상적으로 처리되지 않았습니다.");
            window.close();
            return;
        }

        $.ajax({
            url: "/payment",
            type: "POST",
            data : {pg_token : params.get('pg_token')},
            async: false,
            success: function(result) {
                alert("결제 성공");
            },
            error: function(error) { // 재고가 부족합니다 or 결제가 실패했습니다
                alert(error);
            }
        });
        window.close();
    });
</script>