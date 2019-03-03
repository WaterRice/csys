/**  

* 创建时间：2018年12月8日 下午8:46:58  

* 项目名称：csys  

* @author 罗强  

* @version 1.0   

* @since JDK 1.8  

* 文件名称：VueHistoryInterceptor.java  

* 类说明：  修复vue-router history模式下的404错误

*/

package edu.swu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class VueHistoryInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		return true;
	}
	
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
			if(response.getStatus() == 404 && modelAndView !=null){
				modelAndView.setViewName("redirect:/index.html");
			}
	}
}
