<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="code.jsp" %><html>
<head>
    <title>Title</title>
</head>
<body>
<div class="hoverClass" id = "menuBody">
    <span>menu</span>
</div>
</body>

<script src="../../../static/js/jquery-3.3.1.min.js"></script>
<script>
    $(document).ready(function(){
        let str = "";
        str +=
            '<ul class="dep1 hide">'

        let largeC = getCategories(null);
        for (let i = 0; i < largeC.length; i++) {
            str +=
                '<li>' +
                '<div class = "hoverClass">' +
                '<span>' + largeC[i].key.split(";")[1] +'</span>' +
                '<ul class = "dep2 hide">'

            middleC = largeC[i].value;
            for (let j = 0; j < middleC.length; j++) {
                str +=  '<li>' +
                        '<div class = "hoverClass">' +
                        '<span>' + middleC[j].split(";")[1] + '</span>' +
                        '<ul class = "dep3 hide">'
                let smallC = getCategories(middleC[j]);
                for(let k = 0; k < smallC.length; k++){
                    str +=
                        '<li>' +
                        '<a href = item?categoryId=' + smallC[k].value.split(";")[0] + '&page=1>' +
                        smallC[k].value.split(";")[1] +
                        '</a>' +
                        '</li>'
                }
                str +=
                    '</ul>' +
                    '</div>' +
                    '</li>'
            }

            str +=
                '</ul>' +
                '</div>' +
                '</li>'
        }
        $('#menuBody').append(str);

        $(".hoverClass").bind("mouseover",function(){
            $(this).children('ul').removeClass("hide");
            $(this).children('ul').addClass("show");
        })

        $(".hoverClass").bind("mouseout",function(){
            $(this).children('ul').removeClass("show");
            $(this).children('ul').addClass("hide");
        })
    })
</script>

<style>
    .hide {
        display:none;
    }

    .show {
    }

    li {
        list-style: none;
        padding:5px;
    }

    .dep1 {
        width:150px;
        background-color: red;
        position:relative;
        border-style: solid;
        padding:0%;
    }

    .dep2 {
        background-color: blue;
        position: absolute;
        top: -3px;
        left: 140px;
        border-style: solid;
        padding:0%;
        width: 150px;
    }

    .dep3 {
        background-color: green;
        position: absolute;
        top: -3px;
        left: 140px;
        border-style: solid;
        padding:0%;
        width: 150px;
    }
</style>



</html>
