package com.mdbs.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/*
 * 登录拦截
 *preHandle判断访问的时是否以及登录
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private List<String> excludedUrls;


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        /*String requestUri = request.getRequestURI();
*/

        /*for (String s : excludedUrls) {
            if (requestUri.endsWith(s)) {
                return true;
            }
        }*/

      /*  Integer uid =  (Integer)request.getSession().getAttribute("uid");
        if(uid == null){
            request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
            return false;
        }else{
            return true;
        }*/
    return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    public List<String> getExcludedUrls() {
        return excludedUrls;
    }

    public void setExcludedUrls(List<String> excludedUrls) {
        this.excludedUrls = excludedUrls;
    }

}
