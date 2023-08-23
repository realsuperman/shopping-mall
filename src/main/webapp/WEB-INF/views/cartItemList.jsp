<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:forEach items="${cartItems}" var="cartItem" varStatus="status">
        <tr>
            <td class="product__cart__item">
                <div class="product__cart__item__pic">
                    <img src="${cartItem.itemImagePath}" width="90px" height="90px" alt="">
                </div>
                <div class="product__cart__item__text">
                    <h6>${cartItem.itemName}</h6>
                    <h5 class="cartItem-price-${status.index}">${cartItem.itemPrice}원</h5>
                </div>
            </td>
            <td class="quantity__item">
                <div class="quantity d-flex flex-row">
                    <i class="fa-solid fa-chevron-left left-arrow-${status.index} left-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                    <input type="text" value="${cartItem.itemQuantity}" class="count-${status.index} mx-3 input-val" data-idx="${status.index}" />
                    <i class="fa-solid fa-chevron-right right-arrow-${status.index} right-arrow" data-idx="${status.index}" style="color:gray;padding-top:5px;"></i>
                </div>
            </td>
            <td class="cart__price subTotal-price-${status.index}" data-idx="${status.index}">${cartItem.totalPrice}원</td>
            <td class="cart__close"><i class="fa fa-close btn-close-${status.index} btn-close" data-item="${cartItem.itemId}"></i></td>
        </tr>
</c:forEach>

<!-- Js Plugins -->
<script>
    $(function() {
        var count = $(".input-val").val();

        $(".input-val").keypress(function(event) {
            if (event.which === 13) { // Enter 키의 key code는 13입니다.
               let countVal = $(this).val();
               let idxVal = $(this).data("idx");
               let eachPrice = ".cartItem-price-" + idxVal;
               let priceSelector = ".subTotal-price-" + idxVal;
               let subTotalPrice = parseInt($(eachPrice).text()) * countVal;

               $(priceSelector).text(subTotalPrice + "원");
            }
        });

        $(".left-arrow").click(function() {
            let idxVal = $(this).data("idx");
            let countSelector = ".count-" + idxVal
            count = $(countSelector).val();
            if(count == 1) {
                $(countSelector).val(1);
            } else {
                count--;
                $(countSelector).val(count);
            }
            let eachPrice = ".cartItem-price-" + idxVal;
            let priceSelector = ".subTotal-price-" + idxVal;
            let subTotalPrice = parseInt($(eachPrice).text()) * count;

            $(priceSelector).text(subTotalPrice + "원");
        });

        $(".right-arrow").click(function() {
            let idxVal = $(this).data("idx");
            let countSelector = ".count-" + idxVal;
            count = $(countSelector).val();
            count++;
            $(countSelector).val(count);

            let eachPrice = ".cartItem-price-" + idxVal;
            let priceSelector = ".subTotal-price-" + idxVal;
            let subTotalPrice = parseInt($(eachPrice).text()) * count;

            $(priceSelector).text(subTotalPrice + "원");
        });

        $(".btn-close").on("click", function() {
            let itemId = $(this).data("item");
            console.log("itemId: ", itemId);
            $.ajax({
                url: "cart-delete",
                type: "POST",
                data: JSON.stringify({"itemId": itemId}),
                contentType: "application/json",
                success: function(result) {
                    console.log("result: ", result);
                    $('.std-parents').html(result);
                    //$.LoadingOverlay("hide");
                },
                error: function(xhr, err, status) {
                    console.log(xhr.responseText);
                    alert(err + "이(가) 발생했습니다: " + status);
                }
            });
        });
    });
</script>