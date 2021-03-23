package com.zhao.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageDTO<T> {
    private Integer page;
    private List<T> data;
    private boolean hasfirst;
    private boolean haslast;
    private boolean hasnext;
    private boolean hasprivious;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;

    public void fenYe(Integer pagea, Integer size, Integer count) {
        page=pagea;
            if(count%size==0){
            totalPage=count/size;
        }else{
            totalPage=count/size+1;
        }
        if(count<5){
            totalPage=1;
        }
        if(page<1){
            page=1;
        }

        if(page>=totalPage){
            page=totalPage;
        }

        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);//往前加入0的位置（头部插入）。
            }
            if(page+i<=totalPage){
                pages.add(page+i);//往后就顺延着尾部插入即可。
            }
        }
        if(page==1){
            hasprivious=false;
        }else{
            hasprivious=true;
        }
        if(page==totalPage){
            hasnext=false;
        }else{
            hasnext=true;
        }
        if(pages.contains(1)){
            hasfirst=false;
        }else{
            hasfirst=true;
        }
        if(pages.contains(totalPage)){
            haslast=false;
        }else{
            haslast=true;
        }
    }

}
