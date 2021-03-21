/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var comment = $("#quesion_comment").val();
    comment2target(questionId,0,comment);
}
function comment2target(parentId,type,content) {
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
            "parentId":parentId,
            "content":content,
            "type":type
        })
    });
}

/**
 * 回复二级评论
 */
function comment(e) {
    var id=e.getAttribute("data-id");
    var content=$("#input-"+id).val();
    comment2target(id,1,content);
}

/**
 * 设置标签
 */
function showSelectTag() {
    $("#select-tag").show();
}
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
/**
 * 打开二级回复
     */
function ercomment(e){
    var id = e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    var attribute = e.getAttribute("data-collapse");
    if(attribute){
        //取消展开状态
        comments.removeClass("in");
        //移除储存的标记
        e.removeAttribute("data-collapse");
        //取消蓝色高亮
        e.classList.remove("active");

    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, commentDTO) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": commentDTO.user.touxiang
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": commentDTO.user.name
                    })).append($("<div/>", {
                        "html": commentDTO.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(commentDTO.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
    }

