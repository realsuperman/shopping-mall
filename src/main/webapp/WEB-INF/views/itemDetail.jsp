<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Male-Fashion | Template</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="../../static/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/css/style.css" type="text/css">
</head>

<body>
<c:set var = "images" value = "${fn:split(item.itemImagePath,';')}"/>
<c:set var = "downPrefix" value = "https://firebasestorage.googleapis.com/v0/b/shoppingmall-c6950.appspot.com/o/"/>
<c:set var = "downSuffix" value = "?alt=media"/>

<section class="shopping-cart spad">
    <div class = "container">
        <div class = "row">
            <div class="col-lg-6">
                <img src = "${downPrefix}${images[0]}${downSuffix}" />
            </div>
            <div class="col-lg-6">
                <div class="cart__discount">
                    <h6 style="display:inline">상품명</h6> ${item.itemName}
                </div>

                <div class="cart__discount">
                    <h6 style="display:inline">가격</h6> ${item.itemPrice} 원
                </div>

<%--                <c:if test = "${cargoCnt le 10}">--%>
                <c:if test = "${cargoCnt gt 10}">
                    <div class="cart__discount">
                    상품이 <h6 style="display:inline">${cargoCnt}개</h6> 남았습니다.
                    </div>
                </c:if>



                <button>장바구니 담기</button>
                <button>바로 구매하기</button>

            </div>
        </div>
        <div class = "row">
            <div class="col-lg-12">
                <img src = "${downPrefix}${images[1]}${downSuffix}" />
            </div>
        </div>
        <div class = "row">
            <div class="col-lg-12">
                <img src = "${downPrefix}${images[2]}${downSuffix}" />
            </div>
        </div>
        <div class = "row">
            <div class="col-lg-12">
                <img src = "${downPrefix}${images[3]}${downSuffix}" />
            </div>
        </div>
        <div class = "row">
            <div class="col-lg-12">
                <img src = "${downPrefix}${images[4]}${downSuffix}" />
            </div>
        </div>
        <div class = "row">
            <div class="col-lg-12">
                <img src = "${downPrefix}${images[5]}${downSuffix}" />
            </div>
        </div>
    </div>
</section>
<!-- Js Plugins -->
<script src="../../static/js/jquery-3.3.1.min.js"></script>
<script src="../../static/js/bootstrap.min.js"></script>
<script src="../../static/js/jquery.nice-select.min.js"></script>
<script src="../../static/js/jquery.nicescroll.min.js"></script>
<script src="../../static/js/jquery.magnific-popup.min.js"></script>
<script src="../../static/js/jquery.countdown.min.js"></script>
<script src="../../static/js/jquery.slicknav.js"></script>
<script src="../../static/js/mixitup.min.js"></script>
<script src="../../static/js/owl.carousel.min.js"></script>
<script src="../../static/js/main.js"></script>
</body>
</html>