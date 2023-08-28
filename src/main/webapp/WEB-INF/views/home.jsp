<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="common/code.jsp" %>
<%@include file="common/uploadPath.jsp" %>

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"
          integrity="sha512-1sCRPdkRXhBV2PBLUdRb4tMg1w2YPf37qatUFeS7zlBy7jJI8Lf4VHwWfZZfpXtYSLy85pkm9GaYVYMfw5BC1A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>


<c:set var = "downPrefix" value = "https://firebasestorage.googleapis.com/v0/b/shoppingmall-c6950.appspot.com/o/"/>
<c:set var = "downSuffix" value = "?alt=media"/>


<!-- Shop Section Begin -->
<section class="shop spad">
    <div class="container">
        <div>
            <div class="row" id = "bestSeller">
            </div>
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
                </div>
            </div>
        </c:forEach>
    </div>
</section>
<!-- Shop Section End -->
</body>
<style>
    #bestSellerBody{
        position:relative;
    }
    #previousButton{
        position: relative;
        left: -316px;
        top: 177px;
    }
    #nextButton{
        position: relative;
        left: 1150px;
        top: -273px;
    }
</style>

<script>
    let categories;
    let bestCategoryIndex = 0;
    let bestCategoryRange;

    $(document).ready(function(){
        categories = getCategories(null);
        bestCategoryRange = categories.length;

        bestSeller(0);
    })

    function bestSeller(categoryIdx){
        if(categoryIdx >= bestCategoryRange){
            categoryIdx = 0;
        }else if(categoryIdx < 0){
            categoryIdx = bestCategoryRange - 1;
        }

        bestCategoryIndex = categoryIdx;

        let bestCategoryId = categories[bestCategoryIndex].key.split(";")[0];
        let categoryName = categories[bestCategoryIndex].key.split(";")[1];

        $.ajax({
            type:"GET",
            url:"itemJson?categoryId="+bestCategoryId,
            dataType:"json",
            success: function(response){
                var str = "";

                $('#bestSeller').empty();

                str +=
                    '<div class="col-lg-3 product__item__text">' +
                    '<h5 style="display:inline">' + categoryName + '</h5> 의 인기상품' +
                    '</div>' +
                    '<i onClick="bestSeller(bestCategoryIndex+1)" id = "previousButton" class = "fa-solid fa-caret-left fa-5x"/>' +
                    '<div id = "bestSellerBody" class = "col-lg-12">' +
                    '<div class="row">'
                for (let i = 0; i < response.length; i++) {
                    str +=

                        '<div class="col-lg-3 col-md-6 col-sm-6">' +
                        '<div class="product__item">' +
                        '<img class="product__item__pic set-bg" src =' + downPrefix + response[i].itemImagePath + downSuffix + '>' +
                        '<div class="product__item__text">' +
                        '<h6>'+response[i].itemName + '</h6>' +
                        '<a href="itemDetail?itemId=' + response[i].itemId + '" className="add-cart">상품 상세보기</a>' +
                        '<h5>'+response[i].itemPrice+'원</h5>' +
                        '</div>' +
                        '</div>' +
                        '</div>'
                }
                str +=
                    '</div>' +
                    '</div>' +
                    '<i onClick="bestSeller(bestCategoryIndex-1)" id ="nextButton" class = "fa-solid fa-caret-right fa-5x"/>'

                $('#bestSeller').append(str);
            }
        })
    }

</script>
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