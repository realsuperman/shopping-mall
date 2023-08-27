<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="col-lg-8">
    <div class="shopping__cart__table">
        <table>
            <thead>
                <tr>
                    <th></th>
                    <th>Product</th>
                    <th>Quantity</th>
                    <th style="text-align:right;padding-right:40px;">Total</th>
                    <th></th>
                </tr>
            </thead>
            <tbody class="std-parents">
                <c:choose>
                    <c:when test="${empty cartItemsAll}">
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
                        <c:forEach items="${checkedList}" var="cartItem" begin="${pageable.getBlockStartNum()-1}" end="${pageable.getBlockLastNum()-1}" varStatus="status">
                            <tr class="row-id" data-id="${cartItem.cartId}">
                                <td>
                                    <input type="checkbox" name="check-${cartItem.itemId}" data-item="${cartItem.itemId}" class="check-item mx-3 check-${cartItem.itemId} row-item" checked/>
                                </td>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <img src="${cartItem.itemImagePath}" width="90px" height="90px" alt="">
                                    </div>
                                    <div class="product__cart__item__text">
                                        <h6 class="sec-name">${cartItem.itemName}</h6>
                                        <h5 class="cartItem-price-${status.index}"><i class="fa-solid fa-won-sign"></i>  <fmt:formatNumber value="${cartItem.itemPrice}" /></h5>
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
                                        <div style="color:#0F4C81;font-size:14px;"><B>(- <span><fmt:formatNumber value="${cartItem.totalPrice * discount_rate}" /></span>)</B></div>
                                    </div>
                                </td>
                                <td class="cart__close"><i class="fa fa-close btn-close-${status.index} btn-close" data-item="${cartItem.itemId}"></i></td>
                            </tr>
                        </c:forEach>
                        <c:forEach items="${uncheckedList}" var="cartItem" begin="${pageable.getBlockStartNum()-1}" end="${pageable.getBlockLastNum()-1}" varStatus="status">
                            <tr class="row-id" data-id="${cartItem.cartId}">
                                <td>
                                    <input type="checkbox" name="check-${cartItem.itemId}" data-item="${cartItem.itemId}" class="check-item mx-3 check-${cartItem.itemId} row-item" />
                                </td>
                                <td class="product__cart__item">
                                    <div class="product__cart__item__pic">
                                        <img src="${cartItem.itemImagePath}" width="90px" height="90px" alt="">
                                    </div>
                                    <div class="product__cart__item__text">
                                        <h6 class="sec-name">${cartItem.itemName}</h6>
                                        <h5 class="cartItem-price-${status.index}"><i class="fa-solid fa-won-sign"></i>  <fmt:formatNumber value="${cartItem.itemPrice}" /></h5>
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
                                        <div style="color:#0F4C81;font-size:14px;"><B>(- <span><fmt:formatNumber value="${cartItem.totalPrice * discount_rate}" /></span>)</B></div>
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
            <c:set var="totalPrice" value="0" />
            <c:set var="sumDiscount" value="0" />
            <c:forEach items="${cartItemsAll}" var="cartItem" varStatus="status">
                <c:forEach items="${checkedIdSet}" var="checkedId">
                    <c:choose>
                        <c:when test="${cartItem.itemId == checkedId}">
                            <c:set var="sumDiscount" value="${sumDiscount + (cartItem.totalPrice * discount_rate)}" />
                            <c:set var="discountedPrice" value="${cartItem.totalPrice - (cartItem.totalPrice * discount_rate)}" />
                            <li>${cartItem.itemName} <span><i class="fa-solid fa-won-sign"></i>&nbsp;<span class="summary-subTotal-${status.index} summary-subTotal" data-idx="${status.index}"><fmt:formatNumber value="${discountedPrice}" /></span></span></li>
                            <c:set var="totalPrice" value="${totalPrice + discountedPrice}" />
                        </c:when>
                    </c:choose>
                </c:forEach>
            </c:forEach>
            <li>Discount <span>${grade}(&nbsp;${discount_rate}%<i class="fa-solid fa-caret-down" style="color:#0F4C81;"></i>&nbsp;)</span></li>
            <li>Discount Total <span style="color:#0F4C81;">- <i class="fa-solid fa-won-sign"></i>&nbsp;${sumDiscount}</span></li>
            <li><B>Total</B> <span><i class="fa-solid fa-won-sign"></i>&nbsp;<span id="sum-price"><fmt:formatNumber value="${totalPrice}" /></span></span></li>
        </ul>
        <form id="form-order" action="/order" method="post">
            <input type="text" name="orderItemDtoList" class="input-hidden"/>
            <button type="submit" class="primary-btn btn-order">주문하기</a>
        </form>
    </div>
</div>

<!-- Js Plugins -->
<script src="https://cdn.jsdelivr.net/npm/gasparesganga-jquery-loading-overlay@2.1.7/dist/loadingoverlay.min.js"></script>
<script>
    $(function() {
        let count = $(".input-val").val();
        let curPageNumber = $("#pager").val();
        console.log("curPageNumber", curPageNumber);
        let discountedArray = [];

        $(".summary-subTotal").each(function() {
            let discounted = $(this).text();
            discounted = discounted.replace(/,/g, '');
            discountedArray.push(discounted);
        });

        //checkbox 토글
        $(".row-item").change(function() {
            let rowItemId = $(this).data("item");
            let rowSelector = ".check-" + rowItemId;
            let url = "/checked";
            console.log("isChecked");
            if($(rowSelector).is(":checked")) {
                alert(rowItemId + "가 체크되었습니다.");
                $.ajax({
                    url:"/cart-ajax/checked",
                    type: "POST",
                    data: JSON.stringify({"checkedId": rowItemId, "url": url}),
                    contentType: "application/json",
                    success: function(result) {
                        $.ajax({
                            url: "cart-ajax",
                            type: "GET",
                            success: function(result) {
                                $('.replace-parents').html(result);
                            },
                            error: function(xhr, err, status) {
                                console.log(xhr.responseText);
                                alert(err + "이(가) 발생했습니다: " + status);
                            }
                        });
                    },
                    error: function(xhr, err, status) {
                        console.log(xhr.responseText);
                        alert(err + "이(가) 발생했습니다: " + status);
                    }
                });
            } else {
                alert(rowItemId + "가 체크해제 되었습니다.");

            }
        });

        //수량 입력 버튼
        $(".input-val").keypress(function(event) {
            if (event.which === 13) { // Enter 키의 key code는 13입니다.
               let idxVal = $(this).data("idx");
               let eachPrice = ".cartItem-price-" + idxVal;
               let countSelector = ".count-" + idxVal;
               let priceSelector = ".subTotal-price-" + idxVal;
               let summarySelector = ".summary-subTotal-" + idxVal;
               let closeSelector = ".btn-close-" + idxVal;

               let itemId = $(closeSelector).data("item");
               let preSubTotal = $(priceSelector).text();
               let preSum = $("#sum-price").text();
               let countVal = $(this).val();
               let curCnt = $(countSelector).val();

               let subTotalPrice = parseInt($(eachPrice).text()) * countVal;
               $(priceSelector).text(subTotalPrice.toLocaleString() + "원");
               $(summarySelector).text(subTotalPrice.toLocaleString());

               let withoutComma = $("#sum-price").text().replace(/,/g, '');
               preSum = preSum.replace(/,/g, '');
               preSubTotal = preSubTotal.replace("원", '');
               preSubTotal = preSubTotal.replace(/,/g, '');

               let cur = parseInt(preSum) - parseInt(preSubTotal) + parseInt(subTotalPrice);

               $("#sum-price").text(cur.toLocaleString());
               $.LoadingOverlay("show");
               $.ajax({
                   url: "cart",
                   type: "PUT",
                   data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                   contentType: "application/json",
                   success: function(result) {
                       console.log("result: ", result);
                       $.ajax({
                            url: "cart-ajax",
                            type: "GET",
                            success: function(result) {
                                $('.replace-parents').html(result);
                            },
                            error: function(xhr, err, status) {
                                console.log(xhr.responseText);
                                alert(err + "이(가) 발생했습니다: " + status);
                            }
                       });
                   },
                   error: function(xhr, err, status) {
                       console.log(xhr.responseText);
                       alert(err + "이(가) 발생했습니다: " + status);
                   }
               });
               $.LoadingOverlay("hide");
            }
        });

        //수량 감소 버튼
        $(".left-arrow").click(function() {
            let idxVal = $(this).data("idx");
            let countSelector = ".count-" + idxVal
            let summarySelector = ".summary-subTotal-" + idxVal;
            let eachPrice = ".cartItem-price-" + idxVal;
            let priceSelector = ".subTotal-price-" + idxVal;
            let closeSelector = ".btn-close-" + idxVal;
            let itemId = $(closeSelector).data("item");

            count = $(countSelector).val();
            if(count == 1) {
                $(countSelector).val(1);
            } else {
                count--;
                $(countSelector).val(count);
            }

            let subTotalPrice = parseInt($(eachPrice).text()) * count;
            let curCnt = $(countSelector).val();

            $(priceSelector).text(subTotalPrice.toLocaleString() + "원");
            $(summarySelector).text(subTotalPrice.toLocaleString());
            let withoutComma = $("#sum-price").text().replace(/,/g, '');
            let cur = parseInt(withoutComma) - parseInt($(eachPrice).text());

            $("#sum-price").text(cur.toLocaleString());
            $.LoadingOverlay("show");
            $.ajax({
                url: "cart",
                type: "PUT",
                data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);
                    $.ajax({
                         url: "cart-ajax",
                         type: "GET",
                         success: function(result) {
                             $('.replace-parents').html(result);
                         },
                         error: function(xhr, err, status) {
                             console.log(xhr.responseText);
                            alert(err + "이(가) 발생했습니다: " + status);
                         }
                    });
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
            $.LoadingOverlay("hide");
        });

        //수량 증가 버튼
        $(".right-arrow").click(function() {
            let idxVal = $(this).data("idx");
            let countSelector = ".count-" + idxVal;
            let summarySelector = ".summary-subTotal-" + idxVal;
            let eachPrice = ".cartItem-price-" + idxVal;
            let priceSelector = ".subTotal-price-" + idxVal;
            let closeSelector = ".btn-close-" + idxVal;
            let itemId = $(closeSelector).data("item");

            count = $(countSelector).val();
            count++;
            $(countSelector).val(count);
            let curCnt = $(countSelector).val();
            console.log("curCnt: ", curCnt);
            let subTotalPrice = parseInt($(eachPrice).text()) * count;

            $(priceSelector).text(subTotalPrice.toLocaleString() + "원");
            $(summarySelector).text(subTotalPrice.toLocaleString());
            let withoutComma = $("#sum-price").text().replace(/,/g, '');
            let cur = parseInt(withoutComma) + parseInt($(eachPrice).text());

            $("#sum-price").text(cur.toLocaleString());
            $.LoadingOverlay("show");
            $.ajax({
                url: "cart",
                type: "PUT",
                data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);
                    $.ajax({
                         url: "cart-ajax",
                         type: "GET",
                         success: function(result) {
                             $('.replace-parents').html(result);
                         },
                         error: function(xhr, err, status) {
                             console.log(xhr.responseText);
                            alert(err + "이(가) 발생했습니다: " + status);
                         }
                    });
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
            $.LoadingOverlay("hide");
        });

        //cart item 삭제
        $(".btn-close").on("click", function() {
            let itemId = $(this).data("item");
            console.log("itemId: ", itemId);
            $.LoadingOverlay("show");
            $.ajax({
                url: "cart",
                type: "DELETE",
                data: JSON.stringify({"itemId": itemId}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);
                    $.ajax({
                        url: "cart-ajax",
                        type: "GET",
                        success: function(result) {
                            $('.replace-parents').html(result);
                        },
                        error: function(xhr, err, status) {
                           console.log(xhr.responseText);
                           alert(err + "이(가) 발생했습니다: " + status);
                        }
                    });
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
            $.LoadingOverlay("hide");
        });

        //페이지네이션
        $(".page-prev").on("click", function(event) {
            console.log("prev click");
            event.preventDefault();

            let curPageNum = parseInt($("#pager").val());
            console.log("curPageNum: ", curPageNum);

            let prevPageNum = curPageNum - 1;
            console.log("prevPageNum: ", prevPageNum);

            let nextPageNum = curPageNum + 1;
            console.log("nextPageNum: ", nextPageNum);

            let pageStartCartItem = parseInt($("#pager-start-item").val())-5;
            console.log("pageStartCartItem: ", pageStartCartItem);

            let pageLastCartItem = parseInt($("#pager-last-item").val())-4;
            $.ajax({
                url: "cart-ajax",
                type: "POST",
                data: JSON.stringify({"curPageNum": curPageNum, "prevPageNum": prevPageNum, "nextPageNum": nextPageNum, "pageStartCartItem": pageStartCartItem, "pageLastCartItem": pageLastCartItem, "flag": 0}),
                contentType: "application/json",
                success: function(result) {
                    $.ajax({
                        url: "cart-ajax",
                        type: "GET",
                        success: function(result) {
                            $('.replace-parents').html(result);
                        },
                        error: function(xhr, err, status) {
                            console.log(xhr.responseText);
                            alert(err + "이(가) 발생했습니다: " + status);
                        }
                    });
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });
        $(".page-next").on("click", function(event) {
            console.log("next click");
            event.preventDefault();

            let curPageNum = parseInt($("#pager").val());
            console.log("curPageNum: ", curPageNum);

            let prevPageNum = curPageNum - 1;
            console.log("prevPageNum: ", prevPageNum);

            let nextPageNum = curPageNum + 1;
            console.log("nextPageNum: ", nextPageNum);

            let pageStartCartItem = parseInt($("#pager-start-item").val())+5;
            let pageLastCartItem = parseInt($("#pager-last-item").val())+4;
            $.ajax({
                url: "cart-ajax",
                type: "POST",
                data: JSON.stringify({"curPageNum": curPageNum, "prevPageNum": prevPageNum, "nextPageNum": nextPageNum, "pageStartCartItem": pageStartCartItem, "pageLastCartItem": pageLastCartItem, "flag": 1}),
                contentType: "application/json",
                success: function(result) {
                    $.ajax({
                        url: "cart-ajax",
                        type: "GET",
                        success: function(result) {
                            $('.replace-parents').html(result);
                        },
                        error: function(xhr, err, status) {
                            console.log(xhr.responseText);
                            alert(err + "이(가) 발생했습니다: " + status);
                        }
                    });
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });

        //주문하기 버튼 클릭
        $("#form-order").submit( function(event) {
            let cartIdArray = [];
            let itemIdArray = [];
            let itemNameArray = [];
            let itemQuantityArray = [];
            $(".row-id").each(function() {
                let eachCartId = $(this).data("id");
                cartIdArray.push(eachCartId);
            });
            console.log("cartIdArray: ", cartIdArray);
            $(".btn-close").each(function() {
                let eachitemId = $(this).data("item");
                itemIdArray.push(eachitemId);
            });
            console.log("itemIdArray: ", itemIdArray);
            $(".sec-name").each(function() {
                let eachItemName = $(this).text();
                itemNameArray.push(eachItemName);
            });
            console.log("itemNameArray: ", itemNameArray);
            $(".input-val").each(function() {
                let eachItemQuantity = $(this).val();
                itemQuantityArray.push(eachItemQuantity);
            });

            datas = []
            for(let i = 0; i < cartIdArray.length; i++) {
                let jsonFormat = {}
                jsonFormat["itemId"] = itemIdArray[i];
                jsonFormat["cartId"] = cartIdArray[i];
                jsonFormat["itemName"] = itemNameArray[i];
                jsonFormat["itemQuantity"] = itemQuantityArray[i];
                jsonFormat["itemPrice"] = discountedArray[i];
                datas.push(jsonFormat);
            }
            console.log("datas: ", datas);

            let jsonData = JSON.stringify(datas);
            $(".input-hidden").val(jsonData);
        });
    });
</script>