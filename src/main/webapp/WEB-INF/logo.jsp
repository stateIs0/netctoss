<%@page pageEncoding="utf-8"%>
<img src="images/logo.png" alt="logo" class="left"/>
<!--
	EL"默认"从4个对象中取值:
	pageContext,request,session,application
	也可以从cookie中取值,方式;
	cookie.key.velue(key是固定的)
  -->
<%-- <span>${cookie.admin.value }</span> 使用jsp注释.--%>
<span>${admin }</span>
<a href="/netctoss_cxs/logout.do">[退出]</a>    