<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="common/code.jsp" %>

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

<script>
    var bestSeller = function(categoryId){
        $.ajax({
            type:"GET",
            url:"itemJson?categoryId="+categoryId,
            dataType:"json",
            success: function(response){
                console.log(response)
                var str = "";

                $('#bestSeller').empty();

                str +=
                    '<div class="col-lg-3 product__item__text">' +
                    '<h5 style="display:inline">' + categoryId + '</h5> 의 인기상품' +
                    '</div>' +
                    '<div class = "col-lg-12">' +
                    '<div class="row">'
                for (let i = 0; i < response.length; i++) {
                    str +=

                        '<div class="col-lg-3 col-md-6 col-sm-6">' +
                        '<div class="product__item">' +
                        '<div class="product__item__pic set-bg" data-setbg='+response[i].itemImagePath+'>' +
                        '</div>' +
                        '<div class="product__item__text">' +
                        '<h6>'+response[i].itemName + '</h6>' +
                        '<a href="#" class="add-cart">+ Add To Cart</a>' +
                        '<h5>'+response[i].itemPrice+'원</h5>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                }
                str +=
                    '</div>' +
                '</div>'
                console.log(str);
                $('#bestSeller').append(str);
            }
        })
    }
    $(document).ready(bestSeller(1));

</script>

<div onclick="bestSeller(2)">다음 종류</div>

<!-- Shop Section Begin -->
<section class="shop spad">
    <div class="container">
        <div class="row" id = "bestSeller">
        </div>
        <c:forEach items="${itemList}" var = "items" varStatus="status">
            <div class="row">
                <div class="col-lg-3 product__item__text">
                    <h5 style="display:inline">${categoryNames[status.index]}</h5> 의 최신상품
                </div>

                <div class="col-lg-9 col-md-6 col-sm-6">
                    <div class="shop__product__option__right">
                        <a href="item?categoryId=${categoryIds[status.index]}&page=1">더 보기</a>
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="row">
                    <c:forEach items="${items}" var = "item">
                        <div class="col-lg-3 col-md-6 col-sm-6">
                            <div class="product__item">
                                <div class="product__item__pic set-bg" data-setbg="img/product/product-2.jpg">
                                </div>
                                <div class="product__item__text">
                                    <h6>${item.itemName}</h6>
                                    <a href="#" class="add-cart">+ Add To Cart</a>
                                    <h5>${item.itemPrice}원</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
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