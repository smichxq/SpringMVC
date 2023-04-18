package com.example.springmvc.mapper;

import com.example.springmvc.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    //用户第一次登录成功时记录ticket
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select * from login_ticket where ticket=#{ticket}"
    })
    //对已经登陆后的状态进行查询
    LoginTicket selectLoginTicketByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} ",
            "where ticket=#{ticket} ",
            "<if test=\"ticket!=null\">",
            "and 1=1",
            "</if>",
            "</script>"
    })
    //更新登录状态
    int updateStatus(String ticket, int status);
}
