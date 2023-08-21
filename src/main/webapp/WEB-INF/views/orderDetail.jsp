<%@ page import="com.bit.shoppingmall.dto.OrderDetailDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bit.shoppingmall.dto.OrderInfoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>주문 내역 상세</title>
</head>
<body>
    <!-- 주문 시각, 배송지 정보 표시 -->
    <%
        OrderInfoDto orderInfoDto = (OrderInfoDto) request.getAttribute("orderInfo");
        out.print(orderInfoDto.toString()+"<br>");
    %>

    <!-- [상품명, 수량, 구매 당시 가격, 상태]의 목록으로 조회 -->
    <%
        List<OrderDetailDto> orderDetailDtoList = (List<OrderDetailDto>) request.getAttribute("orderDetailList");
        for(OrderDetailDto orderDetailDto: orderDetailDtoList) {
            out.print(orderDetailDto.toString()+"<br>");
        }
    %>

    <!-- 결제 취소, 반품 상태가 아닌 order_detail의 buy_price의 총합 -->
    <%
        long orderSetTotalBuyPrice = (long) request.getAttribute("orderSetTotalBuyPrice");
        out.print(orderSetTotalBuyPrice+"<br>");
    %>
</body>
</html>
