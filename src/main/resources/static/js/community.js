function post() {
    var questionId = $("#question_id").val();
    var comment = $("#quesion_comment").val();
    console.log(comment);
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        success:function (data) {
            if(data.code==200){
                $("#comment_hidden").hide();
            }else{
                alert("data");
            }
        },
        dataType:"json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":comment,
            "type":0
        })
    });


}