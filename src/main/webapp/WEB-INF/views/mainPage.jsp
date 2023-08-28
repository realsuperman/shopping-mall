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
    <title>롯데 ON</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="../../static/main-page/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/style.css" type="text/css">
    <link rel="stylesheet" href="../../static/main-page/css/main-custom-style.css" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"
          integrity="sha512-1sCRPdkRXhBV2PBLUdRb4tMg1w2YPf37qatUFeS7zlBy7jJI8Lf4VHwWfZZfpXtYSLy85pkm9GaYVYMfw5BC1A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>
<body>
    <!-- Page Preloder -->
    <div id="preloder">
        <div class="loader"></div>
    </div>

    <!-- Header Section Begin -->
    <header class="header">
        <div class="header__top" style="padding-top: 16px; height: 48px;">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-7">
                        <div class="header__top__left">
                            <p>멤버십 별, 할인 혜택이 적용됩니다!</p>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-5">
                        <div class="header__top__right">
                            <div class="header__top__links">
                                <c:choose>
                                    <c:when test="${login_user == null}">
                                        <a href="/user">Login/SignIn</a>
<%--                                        <a href="#">FAQs</a>--%>
                                    </c:when>
                                    <c:otherwise>
                                        <p style="color: #FFFFFF"> ${login_user.userName}님, 어서오세요 < ${login_user.userName} >&nbsp;&nbsp;&nbsp;&nbsp;
                                            <span><a href="/logout">LOGOUT</a></span>
                                        </p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 1. 로고 -->
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-3">
                    <div class="header__logo">
                        <a href="./index.html"><img src="../../static/main-page/img/롯데온 LOGO.png" alt=""></a>
                    </div>
                </div>
                <div class="col-lg-6 col-md-6"> <!-- 그리드 공간 때문에 넘겨두기 -->
                    <nav class="header__menu mobile-menu">
                    </nav>
                </div>
                <div class="col-lg-3 col-md-3">
                    <div class="header__nav__option">
                        <!-- (로그인 상태) 마이롯데, 장바구니                      -->
                        <a href="#"><img src="../../static/main-page/img/icon/마이롯데.png" alt=""></a>
                        <a href="#"><img src="../../static/main-page/img/icon/장바구니.png" alt=""></a>
                    </div>
                </div>
            </div>
            <!-- 2. 카테고리 DROP-DOWN     -->
            <div class="hoverClass"  id = "menuBody"  style="position: absolute;   z-index: 2;"><span style="text-decoration: underline #D34640 3.5px; font-size: 27px">Menu</span></div>

        </div>
    </header>
    <!-- Header Section End -->


    <div style="position: relative; z-index: 1;     margin-top: 44px;">
        <!-- 인기 상품 시작 -->
        <div class="hero__items set-bg" data-setbg="../../static/main-page/img/hero/hero-1.jpg">
            <div class="col-lg-12">
                <div class="section-title">
                    <br>
                    <span>a week's popularity</span>
                    <h2>Best Seller</h2>
                </div>
            </div>
            <section class="shop spad">
                <div class="container">
                    <div class="row" id = "bestSeller"></div>
                </div>
            </section>
        </div>
        <!-- 인기 상품 끝 -->

        <%-- 최신 상품  시작   --%>
        <section class="hero">
            <div class="hero__slider owl-carousel">

                    <c:set var = "downPrefix" value = "https://firebasestorage.googleapis.com/v0/b/shoppingmall-c6950.appspot.com/o/"/>
                    <c:set var = "downSuffix" value = "?alt=media"/>

                    <section class="shop spad">
                        <div class="container">
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
                </div>
        </section>
        <!-- 최신 상품 끝 -->


        <!-- Footer Section Begin -->
        <footer class="footer">
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="footer__about">
                            <div class="footer__logo">
                                <a href="#"><img src="../../static/main-page/img/footer-logo.png" alt=""></a>
                            </div>
                            <p>The customer is at the heart of our unique business model, which includes design.</p>
                            <a href="#"><img src="../../static/main-page/img/payment.png" alt=""></a>
                        </div>
                    </div>
                    <div class="col-lg-2 offset-lg-1 col-md-3 col-sm-6">
                        <div class="footer__widget">
                            <h6>Shopping</h6>
                            <ul>
                                <li><a href="#">Clothing Store</a></li>
                                <li><a href="#">Trending Shoes</a></li>
                                <li><a href="#">Accessories</a></li>
                                <li><a href="#">Sale</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-3 col-sm-6">
                        <div class="footer__widget">
                            <h6>Shopping</h6>
                            <ul>
                                <li><a href="#">Contact Us</a></li>
                                <li><a href="#">Payment Methods</a></li>
                                <li><a href="#">Delivary</a></li>
                                <li><a href="#">Return & Exchanges</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-3 offset-lg-1 col-md-6 col-sm-6">
                        <div class="footer__widget">
                            <h6>NewLetter</h6>
                            <div class="footer__newslatter">
                                <p>Be the first to know about new arrivals, look books, sales & promos!</p>
                                <form action="#">
                                    <input type="text" placeholder="Your email">
                                    <button type="submit"><span class="icon_mail_alt"></span></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <div class="footer__copyright__text">
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                            <p>Copyright ©
                                <script>
                                    document.write(new Date().getFullYear());
                                </script>2020
                                All rights reserved | This template is made with <i class="fa fa-heart-o"
                                                                                    aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                            </p>
                            <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Footer Section End -->

        <!-- Search Begin -->
        <div class="search-model">
            <div class="h-100 d-flex align-items-center justify-content-center">
                <div class="search-close-switch">+</div>
                <form class="search-model-form">
                    <input type="text" id="search-input" placeholder="Search here.....">
                </form>
            </div>
        </div>
        </div>
        <!-- Search End -->

    <%-- header script   --%>
    <script src="../../static/js/header-script.js"></script>
    <%-- best script   --%>
    <script src="../../static/js/best-seller-script.js"></script>

    <script src="../../static/main-page/js/jquery-3.3.1.min.js"></script>
    <script src="../../static/main-page/js/bootstrap.min.js"></script>
    <script src="../../static/main-page/js/jquery.nice-select.min.js"></script>
    <script src="../../static/main-page/js/jquery.nicescroll.min.js"></script>
    <script src="../../static/main-page/js/jquery.magnific-popup.min.js"></script>
    <script src="../../static/main-page/js/jquery.countdown.min.js"></script>
    <script src="../../static/main-page/js/jquery.slicknav.js"></script>
    <script src="../../static/main-page/js/mixitup.min.js"></script>
    <script src="../../static/main-page/js/owl.carousel.min.js"></script>
    <script src="../../static/main-page/js/main.js"></script>
    <script src="../../../static/js/jquery-3.3.1.min.js"></script>


</body>
</html>
