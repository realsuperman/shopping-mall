<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>

<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>롯데온 - 마이페이지</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap"
          rel="stylesheet">

    <!-- 사용자 프로필 Css Styles -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css" integrity="sha256-2XFplPlrFClt0bIdPgpz8H7ojnk10H69xRqd9+uTShA=" crossorigin="anonymous" />
    <link rel="stylesheet" href="../../static/css/profile.css" type="text/css">

    <!-- Css Styles -->
    <link rel="stylesheet" href="../static/css_test/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/elegant-icons.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/nice-select.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="../static/css_test/style.css" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.2/css/all.min.css"
          integrity="sha512-1sCRPdkRXhBV2PBLUdRb4tMg1w2YPf37qatUFeS7zlBy7jJI8Lf4VHwWfZZfpXtYSLy85pkm9GaYVYMfw5BC1A=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <style>
        .left-arrow:hover, .right-arrow:hover {
            cursor: pointer;
        }
        .input-val {
            width: 45px;
            border: 1px #EEEEEE solid;
            border-radius: 5px;
            text-align: right;
            padding: 0 6px;
        }
    </style>
</head>

<body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<!-- Page Preloder -->
<div id="preloder">
    <div class="loader"></div>
</div>

<!-- Offcanvas Menu Begin -->
<div class="offcanvas-menu-overlay"></div>
<div class="offcanvas-menu-wrapper">
    <div class="offcanvas__option">
        <div class="offcanvas__links">
            <a href="#">Sign in</a>
            <a href="#">FAQs</a>
        </div>
        <div class="offcanvas__top__hover">
            <span>Usd <i class="arrow_carrot-down"></i></span>
            <ul>
                <li>USD</li>
                <li>EUR</li>
                <li>USD</li>
            </ul>
        </div>
    </div>
    <div class="offcanvas__nav__option">
        <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
        <a href="#"><img src="img/icon/heart.png" alt=""></a>
        <a href="#"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
        <div class="price">$0.00</div>
    </div>
    <div id="mobile-menu-wrap"></div>
    <div class="offcanvas__text">
        <p>Free shipping, 30-day return or refund guarantee.</p>
    </div>
</div>
<!-- Offcanvas Menu End -->

<!-- Header Section Begin -->
<header class="header">
    <div class="header__top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-7">
                    <div class="header__top__left">
                        <p>Free shipping, 30-day return or refund guarantee.</p>
                    </div>
                </div>
                <div class="col-lg-6 col-md-5">
                    <div class="header__top__right">
                        <div class="header__top__links">
                            <a href="#">Sign in</a>
                            <a href="#">FAQs</a>
                        </div>
                        <div class="header__top__hover">
                            <span>Usd <i class="arrow_carrot-down"></i></span>
                            <ul>
                                <li>USD</li>
                                <li>EUR</li>
                                <li>USD</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-3">
                <div class="header__logo">
                    <a href="./index.html"><img src="img/logo.png" alt=""></a>
                </div>
            </div>
            <div class="col-lg-6 col-md-6">
                <nav class="header__menu mobile-menu">
                    <ul>
                        <li><a href="./index.html">Home</a></li>
                        <li class="active"><a href="./shop.html">Shop</a></li>
                        <li><a href="#">Pages</a>
                            <ul class="dropdown">
                                <li><a href="./about.html">About Us</a></li>
                                <li><a href="./shop-details.html">Shop Details</a></li>
                                <li><a href="./shopping-cart.html">Shopping Cart</a></li>
                                <li><a href="./checkout.html">Check Out</a></li>
                                <li><a href="./blog-details.html">Blog Details</a></li>
                            </ul>
                        </li>
                        <li><a href="./blog.html">Blog</a></li>
                        <li><a href="./contact.html">Contacts</a></li>
                    </ul>
                </nav>
            </div>
            <div class="col-lg-3 col-md-3">
                <div class="header__nav__option">
                    <a href="#" class="search-switch"><img src="img/icon/search.png" alt=""></a>
                    <a href="#"><img src="img/icon/heart.png" alt=""></a>
                    <a href="#"><img src="img/icon/cart.png" alt=""> <span>0</span></a>
                    <div class="price">$0.00</div>
                </div>
            </div>
        </div>
        <div class="canvas__open"><i class="fa fa-bars"></i></div>
    </div>
</header>
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <p style="text-decoration: underline #D34640 3.5px; font-size: 40px">MY PAGE</p>
                    <div class="mypage-tab" style="color: #0b0b0b">
                        <%--  테스트 용 --%>
                        <a href="/home" style="color: #0b0b0b ; font-size: 25px " >Main Page</a> <br>

                        <a href="/myPage" style="color: #0b0b0b ; font-size: 25px " >MY INFO</a> <br>
                        <a href="/cart" style="color: #6b6b6b ; font-size: 25px " ><span>MY CART</span></a> <br>
                        <a href="/orderSetList" style="color: #6b6b6b ; font-size: 25px " ><span>MY ORDER</span></a> <br>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%-- 사용자 정보 조회 --%>

<!-- Page title -->
<div class="my-5">
    <h3>My Profile</h3>
    <hr>
</div>
<!-- Form START -->
<form class="file-upload">
    <div class="row2">
        <!-- Contact detail -->
        <div class="col-xxl-8 mb-5 mb-xxl-0">
            <div class="bg-secondary-soft px-4 py-5 rounded">
                <div class="row1">
                    <h4 class="mb-4 mt-0">사용자 정보 조회</h4>
                    <!-- Name -->
                    <div class="col-md-6">
                        <label class="form-label">Name</label>
                        <input type="text" class="form-control" placeholder="" aria-label="First name" value=" ${login_user.userName}" disabled>
                    </div>
                    <!-- Email -->
                    <div class="col-md-6">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" value="${login_user.userEmail}" disabled>
                    </div>
                    <!-- Phone number -->
                    <div class="col-md-6">
                        <label class="form-label">Phone Number</label>
                        <input type="text" class="form-control"value="${login_user.phoneNumber}" disabled>
                    </div>
                    <!-- Address -->
                    <div class="col-md-6">
                        <label class="form-label">Address *</label>
                        <input type="text" class="form-control"value="${login_user.address}" disabled>
                    </div>

                    <!-- Grade -->
                    <div class="col-md-6">
                        <label class="form-label">Grade</label>
                        <input type="text" class="form-control" value="${grade}" disabled>
                    </div>

                    <!-- Discount Rate -->
                    <div class="col-md-6">
                        <label class="form-label">Discount Rate</label>
                        <input type="text" class="form-control" value="${discount_rate}" disabled>
                    </div>

                    <div  class="col-md-6">
                        <input class="form-control" type="button" value="사용자 정보 수정" onClick="location.href = '/my-page-info'"; style="width: 150px; border-radius: 30px; background-color:#f95959; color: #FFFFFF; border: none;" >
                    </div>

                </div> <!-- Row END -->
            </div>
        </div>
    </div>
</form>

<!-- 에러 메시지 alert -->
<div id="errorSection">
    <%@ include file="errorMsgAlert.jsp" %>
</div>

<!-- Breadcrumb Section End -->
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


