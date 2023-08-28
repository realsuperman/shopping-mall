<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

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
    <script src="https://cdn.jsdelivr.net/npm/gasparesganga-jquery-loading-overlay@2.1.7/dist/loadingoverlay.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="../static/js_test/dynamic.js"></script>

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
        .btn-close:hover {
            cursor: pointer;
            box-shadow: 2px 1px 2px gray;
        }
        .page-active {
            color: red;
        }
    </style>
</head>

<body>
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
    <!-- Breadcrumb Section End -->

    <!-- Shopping Cart Section Begin -->
    <section class="shopping-cart spad">
        <div class="container">
            <div class="row replace-parents">
                <div class="col-lg-8">
                    <div class="shopping__cart__table">
                        <table>
                            <thead>
                                <tr>
                                    <th>Product</th>
                                    <th>Quantity</th>
                                    <th style="text-align:right;padding-right:40px;">Total</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody class="std-parents">
                                <c:choose>
                                    <c:when test="${empty cartItems}">
                                        <tr>
                                            <td class="product__cart__item">
                                                <i class="fa-solid fa-minus"></i>
                                            </td>
                                            <td class="quantity__item">
                                                <i class="fa-solid fa-minus"></i>
                                            </td>
                                            <td class="cart__price subTotal-price-${status.index}" data-idx="${status.index}">
                                                <i class="fa-solid fa-minus"></i>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${cartItems}" var="cartItem" begin="${pageable.getBlockStartNum()-1}" end="${pageable.getBlockLastNum()-1}" varStatus="status">
                                                <tr class="row-id" data-id="${cartItem.cartId}">
                                                    <td>
                                                        <input type="checkbox" data-item="${cartItem.itemId}" class="check-item mx-3 check-${cartItem.itemId} row-item" />
                                                    </td>
                                                    <td class="product__cart__item">
                                                        <div class="product__cart__item__pic">
                                                            <img src="${cartItem.itemImagePath}" width="90px" height="90px" alt="">
                                                        </div>
                                                        <div class="product__cart__item__text">
                                                            <h6 class="sec-name">${cartItem.itemName}</h6>
                                                            <h5 class="cartItem-price-${status.index}"><i class="fa-solid fa-won-sign"></i>  <fmt:formatNumber value="${cartItem.itemPrice}" /></h5>
                                                            <input type="hidden" value="${cartItem.itemPrice - (cartItem.itemPrice * discount_rate)}" class="each-discounted" />
                                                        </div>
                                                    </td>
                                                    <td class="quantity__item">
                                                        <div class="quantity d-flex flex-row">
                                                            <i class="fa-solid fa-chevron-left left-arrow-${status.index} left-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                                                            <input type="text" value="${cartItem.itemQuantity}" class="count-${status.index} mx-3 input-val" data-idx="${status.index}" />
                                                            <i class="fa-solid fa-chevron-right right-arrow-${status.index} right-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                                                        </div>
                                                    </td>
                                                    <td class="cart__price subTotal-price-${status.index}" data-idx="${status.index}">
                                                        <div class="w-75" style="text-align:right;">
                                                            <div><fmt:formatNumber value="${cartItem.totalPrice}" />원</div>
                                                        </div>
                                                    </td>
                                                    <td class="cart__close"><i class="fa fa-close btn-close-${status.index} btn-close" data-item="${cartItem.itemId}"></i></td>
                                                </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" value="${pageable.getCurPage()}" id="pager" />
                    <input type="hidden" value="${pageable.getPageLastCartItem()}" id="pager-last-item" />
                    <input type="hidden" value="${pageable.getPageStartCartItem()}" id="pager-start-item" />
                    <input type="hidden" value="${errMsg}" id="err-msg" />
                    <div class="container d-flex justify-content-center">
                        <div>
                            <ul class="d-flex flex-row">
                                <li style="list-style: none;"><a href="#" class="btn btn-light page-prev">Prev</a></li>
                                <c:choose>
                                    <c:when test="${pageable.getLastPageNum() <= pageable.getBlockLastNum()}">
                                        <c:set var="endNum" value="${pageable.getLastPageNum()}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="endNum" value="${pageable.getBlockLastNum()}" />
                                    </c:otherwise>
                                </c:choose>
                                <c:forEach var="i" begin="${pageable.getBlockStartNum()}" end="${endNum}">
                                    <c:choose>
                                        <c:when test="${pageable.getCurPage() == i}">
                                            <li style="list-style: none;" class="mx-1"><a href="#" class="btn btn-light page-cur page-active">${i}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li style="list-style: none;" class="mx-1"><a href="#" class="btn btn-light page-cur">${i}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <li style="list-style: none;"><a href="#" class="btn btn-light page-next">Next</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <div class="continue__btn">
                                <a href="/home">Continue Shopping</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="cart__total">
                        <h6>Cart total</h6>
                        <ul>
                            <li>Discount <span>${grade}(&nbsp;${discount_rate}%<i class="fa-solid fa-caret-down" style="color:#0F4C81;"></i>&nbsp;)</span></li>
                            <li>Discount Total <span style="color:#0F4C81;">- <i class="fa-solid fa-won-sign"></i>&nbsp;0</span></li>
                            <li><B>Total</B> <span><i class="fa-solid fa-won-sign"></i>&nbsp;<span id="sum-price">&nbsp;0</span></span></li>
                        </ul>
                        <form id="form-order" action="/order" method="post">
                            <input type="text" name="orderItemDtoList" class="input-hidden"/>
                            <button type="submit" class="primary-btn btn-order">주문하기</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- Shopping Cart Section End -->

    <!-- Footer Section Begin -->
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-6 col-sm-6">
                    <div class="footer__about">
                        <div class="footer__logo">
                            <a href="#"><img src="img/footer-logo.png" alt=""></a>
                        </div>
                        <p>The customer is at the heart of our unique business model, which includes design.</p>
                        <a href="#"><img src="img/payment.png" alt=""></a>
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
    <!-- Search End -->
    <script th:inline="javascript">
        toastr.options = {
            closeButton: false,
            debug: false,
            newestOnTop: false,
            progressBar: false,
            positionClass: "toast-top-right",
            preventDuplicates: false,
            onclick: null,
            showDuration: "300",
            hideDuration: "1000",
            timeOut: "5000",
            extendedTimeOut: "1000",
            showEasing: "swing",
            hideEasing: "linear",
            showMethod: "fadeIn",
            hideMethod: "fadeOut"
        };

        function successModal(msg) {
            toastr["success"](msg);
        }

        function errorModal(msg) {
            toastr["error"](msg);
        }

        let errMsg = $("#err-msg").val();
        console.log("errMsg: ", errMsg);

        //if (params.msg) {
           // successModal(params.msg);
        //}

        if (errMsg) {
            console.log("errMsg call");
            errorModal(errMsg);
            console.log("errMsg call__end");
        }
     </script>
</body>

</html>