<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${cartItems}" var="cartItem" varStatus="status">
        <tr>
            <td class="product__cart__item">
                <div class="product__cart__item__pic">
                    <img src="${cartItem.itemImagePath}" width="90px" height="90px" alt="">
                </div>
                <div class="product__cart__item__text">
                    <h6>${cartItem.itemName}</h6>
                    <h5 class="cartItem-price-${status.index}"><i class="fa-solid fa-won-sign"></i> <fmt:formatNumber value="${cartItem.itemPrice}" /></h5>
                </div>
            </td>
            <td class="quantity__item">
                <div class="quantity d-flex flex-row">
                    <i class="fa-solid fa-chevron-left left-arrow-${status.index} left-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                    <input type="text" value="${cartItem.itemQuantity}" class="count-${status.index} mx-3 input-val" data-idx="${status.index}" />
                    <i class="fa-solid fa-chevron-right right-arrow-${status.index} right-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                </div>
            </td>
            <td class="cart__price subTotal-price-${status.index}" data-idx="${status.index}"><fmt:formatNumber value="${cartItem.totalPrice}" />원</td>
            <td class="cart__close"><i class="fa fa-close btn-close-${status.index} btn-close" data-item="${cartItem.itemId}"></i></td>
        </tr>
</c:forEach>

<!-- Js Plugins -->
<script src="https://cdn.jsdelivr.net/npm/gasparesganga-jquery-loading-overlay@2.1.7/dist/loadingoverlay.min.js"></script>
<script>
    $(function() {
        var count = $(".input-val").val();

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
               $.ajax({
                   url: "cart",
                   type: "POST",
                   data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                   contentType: "application/json",
                   success: function(result) {
                       console.log("result: ", result);

                       $('.std-parents').html(result);
                       $.LoadingOverlay("hide");
                   },
                   error: function(xhr, err, status) {
                       console.log(xhr.responseText);
                       alert(err + "이(가) 발생했습니다: " + status);
                   }
               });
            }
        });

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
                type: "POST",
                data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);

                    $('.std-parents').html(result);
                    $.LoadingOverlay("hide");
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });


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
                type: "POST",
                data: JSON.stringify({"itemId": itemId, "cnt": curCnt}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);

                    $('.std-parents').html(result);
                    $.LoadingOverlay("hide");
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });

        $(".btn-close").on("click", function() {
            let itemId = $(this).data("item");
            console.log("itemId: ", itemId);
            $.LoadingOverlay("show");
            $.ajax({
                url: "cart-delete",
                type: "POST",
                data: JSON.stringify({"itemId": itemId}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);
                    $('.std-parents').html(result);
                    $.LoadingOverlay("hide");
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });
    });
</script>