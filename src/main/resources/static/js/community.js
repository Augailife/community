/**
 * 提交回复
 */
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
               window.location.reload();
            }else{
                if(data.code==2003) {
                    var b = confirm(data.message);
                    //自带的一个带有确认和取消的提示框，点击确认返回true，点击取消返回false
                    if (b) {
                        localStorage.setItem("errorAfter","true");
                        window.open("https://github.com/login/oauth/authorize?client_id=589755417b2298a49fcb&redirect_uri=http://localhost:8888/callback&state=1");
                    } else {
                        alert(data.message);
                    }
                }
                alert(data.message);
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
    /**
     * 打开二级回复
     */
function ercomment(e){
    var id = e.getAttribute("data-id");
    var comment=$("#comment-"+id);
    var attribute = e.getAttribute("data-collapse");
    if(attribute){
        comment.removeClass("in");
        e.removeAttribute("data-collapse")
    }else{
        comment.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse","in");
    }
    }

