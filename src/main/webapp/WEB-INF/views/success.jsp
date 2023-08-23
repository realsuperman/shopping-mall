<%--
  Created by IntelliJ IDEA.
  User: seong
  Date: 2023-08-24
  Time: 오전 1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/commonScript.jsp" %>
<script>
    $(document).ready(function() {
        let queryString = window.location.search;
        let params = new URLSearchParams(queryString);
        let pgToken = params.get('pg_token');
        let csh = params.get('mode');

        console.log(pgToken);
        console.log(csh);
        //window.close();
    });
</script>