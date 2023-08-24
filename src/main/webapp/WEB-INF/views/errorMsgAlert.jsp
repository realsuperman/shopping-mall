
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 에러 메시지 alert JSP -->
<%
    String errorMsg = (String) request.getAttribute("errorMsg");
    if (errorMsg != null) {
%>
<input type="hidden" id="errorMsg" value="<%= errorMsg %>">
<%
    }
%>

<!-- 에러 메시지 alert JS -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        var errorMsgInput = document.getElementById("errorMsg");
        if (errorMsgInput) {
            showAlert(errorMsgInput.value);
        }
    });

    function showAlert(message) {
        alert(message);
    }
</script>