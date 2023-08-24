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
<c:set var = "downPrefix" value = "https://firebasestorage.googleapis.com/v0/b/shoppingmall-c6950.appspot.com/o/"/>
<c:set var = "downSuffix" value = "?alt=media"/>


<!-- Shop Section Begin -->
<section class="shop spad">
    <c:forEach items = "${upperCategoryNames}" var = "upperCategory" varStatus="status">
        ${upperCategory}
        <c:if test ="${!status.last}"> > </c:if>
    </c:forEach>


    <h1>${categoryName}</h1>

    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="row">

                    <c:forEach items="${items}" var="item">
                        <div class="col-lg-3 col-md-6 col-sm-6">
                            <div class="product__item">
                                <img class="product__item__pic set-bg" src =${downPrefix}${item.itemImagePath}${downSuffix}>
                                <div class="product__item__text">
                                    <h6>${item.itemName}</h6>
                                    <a href="#" class="add-cart">+ Add To Cart</a>
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