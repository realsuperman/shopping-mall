<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
    function executeAjax(url, type, async, callback){
        $.ajax({
            url:url, // url
            type:type, // type
            async: async, // async
            success: function(result) {
                if (callback) {
                    callback(result); // callback이 존재할 경우에만 실행
                }
            }
        });
    }
</script>