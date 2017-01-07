package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
		

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		//�ų���3������Ҫ����·��,����������3��·��,ֱ�����������ִ��.
		String[] paths=new String[]{"/toLogin.do","/login.do","/creatImg.do","/toIndex.do"};
		String p=request.getServletPath();
		for(String path:paths){
			if(p.equals(path)){
				chain.doFilter(request, response);
				return;
			}
		}
		//��session�л�ȡ�˺�
		HttpSession session=request.getSession();
		String code=(String)session.getAttribute("admin");
		if(code==null){
			response.sendRedirect("/netctoss_cxs/toLogin.do");
		}else{
			chain.doFilter(request, response);
		}
		//�����˺��ж��û��Ƿ��½. 
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
