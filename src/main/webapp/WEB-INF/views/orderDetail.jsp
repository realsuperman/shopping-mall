<%@ page import="com.bit.shoppingmall.dto.OrderDetailDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bit.shoppingmall.dto.OrderInfoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="zxx">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="Male_Fashion Template">
    <meta name="keywords" content="Male_Fashion, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>주문 상세 조회</title>

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

<!-- Offcanvas Menu Begin -->
<!--
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
    <div id="mobile-menu-wrap">
        <div class="slicknav_menu"><a href="#" aria-haspopup="true" role="button" tabindex="0"
                                      class="slicknav_btn slicknav_collapsed" style=""><span class="slicknav_menutxt">MENU</span><span
                class="slicknav_icon"><span class="slicknav_icon-bar"></span><span
                class="slicknav_icon-bar"></span><span class="slicknav_icon-bar"></span></span></a>
            <nav class="slicknav_nav slicknav_hidden" style="display: none;" aria-hidden="true" role="menu">
                <ul>
                    <li><a href="./index.html" role="menuitem">Home</a></li>
                    <li class="active"><a href="./shop.html" role="menuitem">Shop</a></li>
                    <li class="slicknav_collapsed slicknav_parent"><a href="#" role="menuitem" aria-haspopup="true"
                                                                      tabindex="-1" class="slicknav_item slicknav_row"
                                                                      style=""><a href="#">Pages</a>
                        <span class="slicknav_arrow">►</span></a>
                        <ul class="dropdown slicknav_hidden" role="menu" style="display: none;" aria-hidden="true">
                            <li><a href="./about.html" role="menuitem" tabindex="-1">About Us</a></li>
                            <li><a href="./shop-details.html" role="menuitem" tabindex="-1">Shop Details</a></li>
                            <li><a href="./shopping-cart.html" role="menuitem" tabindex="-1">Shopping Cart</a></li>
                            <li><a href="./checkout.html" role="menuitem" tabindex="-1">Check Out</a></li>
                            <li><a href="./blog-details.html" role="menuitem" tabindex="-1">Blog Details</a></li>
                        </ul>
                    </li>
                    <li><a href="./blog.html" role="menuitem">Blog</a></li>
                    <li><a href="./contact.html" role="menuitem">Contacts</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <div class="offcanvas__text">
        <p>Free shipping, 30-day return or refund guarantee.</p>
    </div>
</div>
-->
<!-- Offcanvas Menu End -->

<!-- Header Section Begin -->
<!--
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
-->
<!-- Header Section End -->

<!-- Breadcrumb Section Begin -->
<!--
<section class="breadcrumb-option">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="breadcrumb__text">
                    <h4>Shopping Cart</h4>
                    <div class="breadcrumb__links">
                        <a href="./index.html">Home</a>
                        <a href="./shop.html">Shop</a>
                        <span>Shopping Cart</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
-->
<!-- Breadcrumb Section End -->

<!-- Shopping Cart Section Begin -->
<section class="shopping-cart spad">
    <div id="order_info">
        <div class="container">
            <h4><b>Order Info</b></h4>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <table>
                        <thead>
                        <tr>
                            <th>Order Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${requestScope.orderInfo.orderTime}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <table>
                        <thead>
                        <tr>
                            <th>Order Address</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${requestScope.orderInfo.orderAddress}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row">
                <div class="p-3">
                    <table>
                        <thead>
                        <tr>
                            <th>Phone Number</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${requestScope.orderInfo.orderPhoneNumber}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <%--    <div class="container">--%>
    <div id="order_table">
        <div class="container p-3">
            <h4><b>Order Detail</b></h4>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-lg-8">
                    <div class="shopping__cart__table">
                        <table>
                            <thead>
                            <tr>
                                <th>Item Name</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.orderDetailList}" var="orderDetailDto" varStatus="state">
                                <tr>
                                    <td class="product__cart__item">
                                        <div class="product__cart__item__text">
                                                ${orderDetailDto.itemName}
                                        </div>
                                    </td>
                                    <td class="quantity__item">
                                        <div class="product__cart__item__text">
                                                ${orderDetailDto.itemQuantity}
                                        </div>
                                    </td>
                                    <td class="product__cart__item__text">
                                            ${orderDetailDto.buyPrice}
                                    </td>
                                    <td class="product__cart__item__text">
                                            ${orderDetailDto.statusName}
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="container">
                        <div class="row">
                            <div class="p-2">
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Total Buy Price</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>${requestScope.orderSetTotalBuyPrice}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="continue__btn">
                                <a href="#">Back To Order List</a>
                            </div>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="continue__btn update__btn">
                                <a href="#">Cancel Order</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <!--
            <div class="col-lg-4">
                <div class="cart__discount">
                    <h6>Discount codes</h6>
                    <form action="#">
                        <input type="text" placeholder="Coupon code">
                        <button type="submit">Apply</button>
                    </form>
                </div>
                <div class="cart__total">
                    <h6>Cart total</h6>
                    <ul>
                        <li>Subtotal <span>$ 169.50</span></li>
                        <li>Total <span>$ 169.50</span></li>
                    </ul>
                    <a href="#" class="primary-btn">Proceed to checkout</a>
                </div>
            </div>
            -->
    </div>
</section>
<!-- Shopping Cart Section End -->

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


