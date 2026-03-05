package nuc.edu.lumecho.interceptor;

import nuc.edu.lumecho.common.RedisKeyConstants;
import nuc.edu.lumecho.common.WdfUserContext;
import nuc.edu.lumecho.common.util.WdfStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class AuthUserInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");

        if (WdfStringUtil.isBlank(authHeader) || !authHeader.startsWith("Bearer ")) {
            return true;
        }

        String token = authHeader.substring(7);
        String key = RedisKeyConstants.USER_TOKEN_KEY + token;
        String userId = stringRedisTemplate.opsForValue().get(key);

        if(WdfStringUtil.isEmpty(userId)){
            return true;
        }

        WdfUserContext.setCurrentUserId(Long.valueOf(userId));

        return true;
    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WdfUserContext.clear();
    }

}
