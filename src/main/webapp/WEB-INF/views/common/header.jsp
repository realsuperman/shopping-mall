<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="code.jsp" %><html>
<head>
    <title>Title</title>
</head>
<body>
Header!!!!!!!!!!!!!!!!!!!!!!!!!

</body>
<script>
    $(document).ready(function(){
        let largeC = getCategories(null);
        for (let i = 0; i < largeC.length; i++) {
            middleC = largeC[i].value;
            for (let j = 0; j < middleC.length; j++) {
                let smallC = getCategories(middleC[j]);
                console.log(middleC);
                console.log(smallC);
            }
        }
    })
</script>
</html>
