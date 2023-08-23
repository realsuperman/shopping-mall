<%--
  Created by IntelliJ IDEA.
  User: 최성훈
  Date: 2023-08-17
  Time: 오후 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/commonScript.jsp" %>
<%
    String mode = (String) request.getAttribute("page");
%>

<script>
    var totalRow = 0;
    var pageSize = 16;

    $(document).ready(function() {
        initDesign('<%= mode %>');

        $('#add').html("상품 추가");
        $('#control').html("재고 관리");
        $('#stat').html("상품 상태 관리");

        $('#searchInput').on('keyup', function(event) {
            $('#current-page').val(1);
            if (event.keyCode === 13) {
                $("#dynamicDiv").empty();
                searchStock();
            }
        });

        $('#searchStockButton').on('click', function() {
            $('#current-page').val(1);
            $("#dynamicDiv").empty();
            searchStock();
        });
    });

    function initDesign(page){
        $('#current-page').val(1);
        let htmlCode="";
        htmlCode = initHtmlCode(page,htmlCode);
        if(page==="stock"){
            htmlCode+= callJsonList();
        }else{

        }

        htmlCode+="</table>";
        $('#dynamicDiv').append(htmlCode);
    }

    function searchStock(){
        let htmlCode="";
        htmlCode = initHtmlCode('<%= mode %>',htmlCode);
        htmlCode+=callJsonList();
        htmlCode+="</table>";
        $('#dynamicDiv').append(htmlCode);
    }

    function initHtmlCode(page,htmlCode){
        htmlCode = "<table><tr>";
        if(page === "stock"){ // 재고 관리
            htmlCode+="<th>제품 id</th><th>제품명</th><th>갯수</th>";
            htmlCode+="</tr>"
        }else{ // 상품 상태 관리
            htmlCode+="<th>Cargo id</th><th>제품명</th><th>상태</th>";
            htmlCode+="</tr>"
        }
        return htmlCode;
    }

    function callJsonList(){
        totalRow = 0;
        $('#pagination').empty();
        let data = '';
        let formData = {
            itemName : $('#searchInput').val(),
            page : $('#current-page').val()-1
        };

        $.ajax({
            url:"/stock/list",
            type:"GET",
            async: false,
            data : formData,
            success: function(result) {
                data = parsingJson(result);
            }
        });

        let page = "";
        let paging = Math.ceil(totalRow/pageSize);
        for (let i = 1; i <= paging; i++) {
            page += '<span style="margin-left: 50px;" onclick="moveAnotherPage(' + i + ')">' + i + '</span>';
            if (i % 10 === 0) {
                page += "<br>";
            }
        }

        $('#pagination').append(page);
        return data;
    }

    function parsingJson(inputString){
        let inputText = JSON.stringify(inputString);
        let size = parseInt(inputText.match(/"count":(\d+)/)[1]);
        if (size === 0) {
            alert("검색 결과가 존재하지 않아요");
            return '';
        }
        totalRow = size;
        console.log(size);

        let htmlCode = "";
        let keyData = inputText.match(/"key":"\[.*\]"/)[0];
        let objectArrayString = keyData.match(/\[.*\]/)[0];
        let objectStrings = objectArrayString.match(/StockDto\(.*?\)/g);
        let extractedData = objectStrings.map(function(objectString) {
            let itemId = objectString.match(/itemId=(\d+)/)[1];
            let itemName = objectString.match(/itemName=([^,]+)/)[1];
            let cnt = objectString.match(/cnt=(\d+)/)[1];
            htmlCode+="<tr>";
            htmlCode+="<td>"; htmlCode+=itemId; htmlCode+="</td>";
            htmlCode+="<td>"; htmlCode+=itemName; htmlCode+="</td>";
            htmlCode+="<td>"; htmlCode+=cnt; htmlCode+="</td>";
            htmlCode+="</tr>";
        });
        return htmlCode;
    }

    function moveAnotherPage(page){
        $("#dynamicDiv").empty();
        $('#current-page').val(page);
        searchStock();
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="../static/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="../static/assets/demo/chart-area-demo.js"></script>
<script src="../static/assets/demo/chart-bar-demo.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
<script src="../static/js/datatables-simple-demo.js"></script>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Dashboard - SB Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="../static/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <style>
        table {
            border-collapse: collapse; /* 테두리 겹침 해제 */
            width: 100%;
            border: 1px solid black; /* 전체 테이블 테두리 */
        }

        th, td {
            border: 1px solid black; /* 각각의 셀 테두리 */
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2; /* 헤더 배경색 */
        }
    </style>
</head>
<body class="sb-nav-fixed">
<%@include file="./common/header.html" %>
<div id="layoutSidenav">
    <%@include file="common/adminNav.html" %>
    <div id="layoutSidenav_content">
        <div class="center">
            <div style="text-align: center; margin-left: 200px; margin-right: 200px" id="search">
                <div style="text-align: left;" id="searchField"><input type="text" id="searchInput"/> <button id="searchStockButton">검색</button></div>
                <div style="margin-top: 20px;" id="dynamicDiv"></div>
            </div>
            <div id="pagination" style="margin-top:20px; text-align: center;"></div>
        </div>
    </div>
    <input id="current-page" style="display: none" value="1"/>
</div>
</body>
</html>