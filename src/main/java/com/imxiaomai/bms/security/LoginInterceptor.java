package com.imxiaomai.bms.security;

import com.alibaba.fastjson.JSON;
import com.imxiaomai.bms.common.Constants;
import com.imxiaomai.bms.util.BaseAction;
import com.imxiaomai.bms.util.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger Log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null || redisTemplate.opsForValue().get(Constants.KEY_REDIS_PREFIX+token) == null){
            PrintWriter out = response.getWriter();
            try {
                out.write(JSON.toJSONString(new ResMsg<>(BaseAction.NO_AUTHENTICATION,"NO_Authentication/Not Login",null)));
            }finally {
                out.close();
            }
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
