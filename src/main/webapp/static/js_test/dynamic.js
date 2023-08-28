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
        if($(rowSelector).is(":checked")) {
            alert(rowItemId + "가 체크되었습니다.");
            $.LoadingOverlay("show");
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
            $.LoadingOverlay("hide");
        } else {
            alert(rowItemId + "가 체크해제 되었습니다.");
            url = "/unchecked";
            $.LoadingOverlay("show");
                $.ajax({
                    url:"/cart-ajax/unchecked",
                    type: "POST",
                    data: JSON.stringify({"uncheckedId": rowItemId, "url": url}),
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
                $.LoadingOverlay("hide");
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

        let pageLastCartItem = parseInt($("#pager-last-item").val())-5;
        $.ajax({
            url: "cart-ajax",
            type: "POST",
            data: JSON.stringify({"curPageNum": curPageNum, "prevPageNum": prevPageNum, "nextPageNum": nextPageNum, "pageStartCartItem": pageStartCartItem, "pageLastCartItem": pageLastCartItem, "flag": 0, "url": ""}),
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
        let pageLastCartItem = parseInt($("#pager-last-item").val())+5;
        $.ajax({
            url: "cart-ajax",
            type: "POST",
            data: JSON.stringify({"curPageNum": curPageNum, "prevPageNum": prevPageNum, "nextPageNum": nextPageNum, "pageStartCartItem": pageStartCartItem, "pageLastCartItem": pageLastCartItem, "flag": 1, "url": ""}),
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
        let eachDiscountedArray = [];
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
        $(".each-discounted").each(function() {
            let eachDiscounted = $(this).val();
            eachDiscountedArray.push(eachDiscounted);
        });
        console.log("eachDiscountedArray: ", eachDiscountedArray);

        datas = []
        for(let i = 0; i < cartIdArray.length; i++) {
            let jsonFormat = {}
            jsonFormat["itemId"] = itemIdArray[i];
            jsonFormat["cartId"] = cartIdArray[i];
            jsonFormat["itemName"] = itemNameArray[i];
            jsonFormat["itemQuantity"] = itemQuantityArray[i];
            jsonFormat["itemPrice"] = eachDiscountedArray[i];
            datas.push(jsonFormat);
        }
        console.log("datas: ", datas);

        let jsonData = JSON.stringify(datas);
        $(".input-hidden").val(jsonData);
    });
});
