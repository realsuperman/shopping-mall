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
    <title>롯데 온 - 아이템</title>

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
<c:set var = "downPrefix" value = "https://firebasestorage.googleapis.com/v0/b/shoppingmall-c6950.appspot.com/o/"/>
<c:set var = "downSuffix" value = "?alt=media"/>

<jsp:include page="common/header.jsp"></jsp:include>
<div style="position: relative; z-index: 1;     margin-top: 44px;">
    <jsp:include page="common/titleHeader.jsp"></jsp:include>


<!-- Shop Section Begin -->
<section class="shop spad">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">

                <div class ="shop__product__option">
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="shop__product__option__left">
                                <span>
                                    <c:forEach items = "${upperCategoryNames}" var = "upperCategory" varStatus="status">
                                        ${upperCategory}
                                        <c:if test ="${!status.last}"> > </c:if>
                                    </c:forEach>
                                </span>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6"></div>
                    </div>
                </div>

                <h3 class = "related-title">${categoryName}</h3>

                <div class="row">
                    <c:forEach items="${items}" var="item">
                        <div class="col-lg-3 col-md-6 col-sm-6">
                            <div class="product__item">
                                <img class="product__item__pic set-bg" src =${downPrefix}${item.itemImagePath}${downSuffix}>
                                <div class="product__item__text">
                                    <h6>${item.itemName}</h6>
                                    <a href="itemDetail?itemId=${item.itemId}" class="add-cart">상품 상세보기</a>
                                    <h5>${item.itemPrice}원</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="product__pagination">

                            <c:forEach var = "i" begin ="1" end = "${lastPage}">
                                <a class = "<c:if test = '${i eq nowPage}'>active</c:if>" href="item?categoryId=${categoryId}&page=${i}">
                                    ${i}
                                </a>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Shop Section End -->

<jsp:include page="common/footer.jsp"></jsp:include>

</div>



</body>
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

</html>