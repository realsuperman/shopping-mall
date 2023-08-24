<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/core" %>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!--
<script>
    $("#paymentLink").on("click", function(event) {
        event.preventDefault();

        let orderAddress     = $('section.shopping-cart.spad div#destinationInfo div#address div.container div.row div.p-3 table tbody tr td').text();
        let orderPhoneNumber = $('section.shopping-cart.spad div#destinationInfo div#phoneNumber div.container div.row div.p-3 table tbody tr td').text();
        let totalBuyPrice    = $("#paymentInfo").data("data-total-price");

        $.ajax({
            type: "POST",
            url: "/payment",
            data: {
                orderInfo: {
                    orderAddress: orderAddress,
                    orderPhoneNumber: orderPhoneNumber
                },
                orderItemDtoList: ${requestScope.orderItemDtoList},
                totalBuyPrice: totalBuyPrice
            },
            success: function(response) {

            },
            error: function(response) {

            },
            complete: function(response) {

            }
        });
    });
</script>
-->
<html lang="zxx">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>주문 확인</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&amp;display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="../static/css_test/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/style.css" type="text/css">

</head>
<body>
<!-- Page Preloder -->
<div id="preloder" style="display: none;">
    <div class="loader" style="display: none;"></div>
</div>
<section class="shopping-cart spad">
    <div id="orderInfo">
        <div class="container">
            <h5><b>구매자 정보</b></h5>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <!-- 사용자 이름 -->
                    <table>
                        <thead>
                        <tr>
                            <th>이름</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${sessionScope.get("login_user").userName}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <!-- 사용자 이메일 -->
                    <table>
                        <thead>
                        <tr>
                            <th>이메일</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${sessionScope.get("login_user").userEmail}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <!-- 사용자 전화 번호 -->
                    <table>
                        <thead>
                        <tr>
                            <th>전화 번호</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${sessionScope.get("login_user").phoneNumber}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div id="destinationInfo">
        <div class="container">
            <h5><b>배송지 정보</b></h5>
        </div>

        <div id="address">
            <div class="container row p-3">
                <div class="row">
                    <div class="p-3">
                        <!-- 배송지 주소 -->
                        <table>
                            <thead>
                            <tr>
                                <th>배송지</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${sessionScope.get("login_user").address}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="phoneNumber">
            <div class="container">
                <div class="row">
                    <div class="p-3">
                        <!-- 배송지 전화 번호 -->
                        <table>
                            <thead>
                            <tr>
                                <th>전화 번호</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${sessionScope.get("login_user").phoneNumber}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%--    <div class="container">--%>
    <div id="orderTable">
        <div class="container p-3">
            <h4><b>구매 상품 목록</b></h4>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="shopping__cart__table">
                        <!-- 상품명과 갯수 -->
                        <table>
                            <thead>
                            <tr>
                                <th>상품명</th>
                                <th>수량</th>
                                <th>가격</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.orderItemDtoList}" var="orderItemDto" varStatus="state">
                                <tr>
                                    <td class="product__cart__item">
                                        <div class="product__cart__item__text">
                                                ${orderItemDto.itemName}
                                        </div>
                                    </td>
                                    <td class="quantity__item">
                                        <div class="product__cart__item__text">
                                                ${orderItemDto.itemQuantity}
                                        </div>
                                    </td>
                                    <td class="product__cart__item">
                                        <div class="product__cart__item__text">
                                                ${orderItemDto.itemPrice}
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="paymentInfo" data-total-price="${totalBuyPrice}">
            <div class="container p-3">
                <h4><b>결제 정보</b></h4>
            </div>

            <div class="container">
                <div class="row">
                    <div class="p-2">
                        <!-- 할인률 적용 -->
                        <c:set var="totalBuyPrice" value="0"/>
                        <fn:forEach items="${requestScope.orderItemDtoList}" var="orderItemDto">
                            <c:set var="productSum" value="${orderItemDto.itemQuantity * orderItemDto.itemPrice}"/>
                            <c:set var="totalBuyPrice" value="${totalBuyPrice + productSum}"/>
                        </fn:forEach>
                        <table>
                            <thead>
                            <tr>
                                <th>총 결제 금액</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    ${totalBuyPrice}원
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div id="payment">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6">
                    <div class="continue__btn update__btn">
                        <a href="#" id="paymentLink">Payment</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- Js Plugins -->
<script src="../static/js_test/jquery-3.3.1.min.js"></script>
<script src="../static/js_test/bootstrap.min.js"></script>
<script src="../static/js_test/jquery.nice-select.min.js"></script>
<script src="../static/js_test/jquery.nicescroll.min.js"></script>
<script src="../static/js_test/jquery.magnific-popup.min.js"></script>
<script src="../static/js_test/jquery.countdown.min.js"></script>
<script src="../static/js_test/jquery.slicknav.js"></script>
<script src="../static/js_test/mixitup.min.js"></script>
<script src="../static/js_test/owl.carousel.min.js"></script>
<script src="../static/js_test/main.js"></script>
</body>
</html>
