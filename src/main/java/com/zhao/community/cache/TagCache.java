package com.zhao.community.cache;

import com.zhao.community.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
 @Autowired
    TagDTO tagDTO;
 public static List<TagDTO> getTags(){
     List<TagDTO> tagDTOS=new ArrayList<>();

     TagDTO yuyan = new TagDTO();
     yuyan.setKuNames("开发语言");
     yuyan.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
     tagDTOS.add(yuyan);

     TagDTO framework = new TagDTO();
     framework.setKuNames("平台框架");
     framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
     tagDTOS.add(framework);


     TagDTO server = new TagDTO();
     server.setKuNames("服务器");
     server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
     tagDTOS.add(server);

     TagDTO db = new TagDTO();
     db.setKuNames("数据库");
     db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
     tagDTOS.add(db);

     TagDTO tool = new TagDTO();
     tool.setKuNames("开发工具");
     tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
     tagDTOS.add(tool);

     return tagDTOS;
 }
}
