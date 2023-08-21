<%@ page import="com.bit.shoppingmall.dto.OrderSetDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>

</script>

<html>
<head>
    <title>주문 내역 목록</title>
</head>
<body>
<!-- [상품명 외 X종, 배송지, 배송지 연락처]의 목록 조회 -->
<div class="order_set_list">
    <table>
        <thead>
        <tr>
            <th>상품명</th>
            <th>주문 코드</th>
            <th>배송지</th>
            <th>배송지 연락처</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.consumerOrderSetList}" var="orderSetDto" varStatus="state">
            <tr>
                <c:choose>
                    <c:when test="${orderSetDto.distinctItemCount > 1}">
                        <td>${orderSetDto.representative} 외 ${orderSetDto.distinctItemCount - 1}종</td>
                    </c:when>
                    <c:otherwise>
                        <td>${orderSetDto.representative}</td>
                    </c:otherwise>
                </c:choose>
                <td>${orderSetDto.orderCode}</td>
                <td>${orderSetDto.orderAddress}</td>
                <td>${orderSetDto.orderPhoneNumber}</td>
            </tr>
        </c:forEach>
        <span>
            <button>주문 상세</button>
        </span>
        </tbody>
    </table>
</div>

</body>
</html>
