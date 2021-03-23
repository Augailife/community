package com.zhao.community.service;

import com.zhao.community.dto.NotificationDTO;
import com.zhao.community.dto.PageDTO;
import com.zhao.community.enums.NotifictionEnum;
import com.zhao.community.mapper.NotifictionMapper;
import com.zhao.community.model.Notifiction;
import com.zhao.community.model.NotifictionExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotifictionService {
@Autowired
    NotifictionMapper notifictionMapper;
    public PageDTO List(Integer page, Integer size, Integer id) {
        PageDTO pageDTO=new PageDTO();
        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        NotifictionExample notifictionExample = new NotifictionExample();
        Integer count =(int) notifictionMapper.countByExample(notifictionExample);
        pageDTO.fenYe(page,size, count);
        Integer startpage=(page-1)*size;
        NotifictionExample  notifictionExample1 = new NotifictionExample();
        notifictionExample1.setOrderByClause("gmt_create desc");
        List<Notifiction> notifictions=notifictionMapper.selectByExampleWithRowbounds(notifictionExample1,new RowBounds(startpage,size));
        for (Notifiction notifiction : notifictions) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notifiction, notificationDTO);
            notificationDTO.setType(NotifictionEnum.typeToGetName(notifiction.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOS);
        return pageDTO;


    }
}
