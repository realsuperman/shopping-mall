<%--
  Created by IntelliJ IDEA.
  User: 최성훈
  Date: 2023-08-25
  Time: 오후 5:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/commonScript.jsp" %>
<%
    String requestUrl = (String) request.getAttribute("redirectUrl");
    String pageUrl = request.getParameter("page");
%>
<script>
    var csh = "100"; // 전역에 값 유지 변수
</script>

<script>
    $(function() {
        $.ajax({
            url: "<%= requestUrl %>",
            type: "GET",
            data: {isSecond:"Y",pageUrl:'<%= pageUrl %>'},
            success: function (result) {
                $("#drawLayor").html(result);
            }
        });
    });
</script>

<html id="dynamicHtml">
    <body>
<%--        <jsp:include page="common/header.jsp"></jsp:include>--%>
        <%String globalVar1 = "test";%>
        <div id="drawLayor"></div>
    </body>
</html>