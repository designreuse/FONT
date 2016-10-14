<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
<head>
<title></title>
</head>
<body>
	<div class="sidebar-collapse">
		<ul class="nav metismenu" id="side-menu">
			<li class="nav-header">
				<div class="dropdown profile-element">
					<span> <img alt="image" class="img-circle"
						src="content/img/acms/headimg.jpg" />
					</span> <a data-toggle="dropdown" class="dropdown-toggle" href="#"> <span
						class="clear"> <span class="block m-t-xs"> <strong
								class="font-bold">${nikename }</strong>
						</span> <span class="text-muted text-xs block">${rolename } <b
								class="caret"></b></span>
					</span>
					</a>
					<ul class="dropdown-menu animated fadeInRight m-t-xs">
						<li><a href="#">Profile</a></li>
						<li><a href="#">Contacts</a></li>
						<li><a href="#">Mailbox</a></li>
						<li class="divider"></li>
						<li><a href="javascript:void(0);" onclick="logout()">Logout</a></li>
					</ul>
				</div>
				<div class="logo-element">FMS</div>
			</li>

			<c:forEach items="${menuList}" var="firstmenu">
				<c:choose>
					<c:when test="${firstmenu.childMenus==null || fn:length(firstmenu.childMenus)==0}">
						<li><a class="J_menuItem" href="${path }${firstmenu.uri}"><i
								class="fa ${firstmenu.icon }"></i> <span class="nav-label">${firstmenu.menuname}</span></a></li>
					</c:when>
					<c:otherwise>
						<li><a href="${path }${firstmenu.uri}"><i
								class="fa ${firstmenu.icon }"></i> <span class="nav-label">${firstmenu.menuname}</span>
								<span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
							
								<c:forEach items="${firstmenu.childMenus }" var="secondmenu">
									<c:choose>
										<c:when test="${secondmenu.childMenus==null || fn:length(secondmenu.childMenus)==0}">
											<li><a class="J_menuItem"
												href="${path }${secondmenu.uri}"><i
													class="fa ${secondmenu.icon }"></i> <span class="nav-label">${secondmenu.menuname}</span></a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${path }${secondmenu.uri}"><i
													class="fa ${secondmenu.icon }"></i> <span class="nav-label">${secondmenu.menuname}</span>
													<span class="fa arrow"></span></a>
												<ul class="nav nav-third-level collapse">
												
													<c:forEach items="${secondmenu.childMenus }"
														var="thirdmenu">
														<c:choose>
															<c:when test="${thirdmenu.childMenus==null || fn:length(thirdmenu.childMenus)==0 }">
																<li><a class="J_menuItem"
																	href="${path }${thirdmenu.uri}"><i
																		class="fa ${thirdmenu.icon }"></i> <span
																		class="nav-label">${thirdmenu.menuname}</span></a></li>
															</c:when>
															<c:otherwise>
																<li><a href="${path }${thirdmenu.uri}"><i
																		class="fa ${thirdmenu.icon }"></i> <span
																		class="nav-label">${thirdmenu.menuname}</span> <span
																		class="fa arrow"></span></a>
																	<ul class="nav nav-fourth-level collapse">
																	
																		<c:forEach items="${thirdmenu.childMenus }"
																			var="fourthmenu">
																			<c:choose>
																				<c:when test="${fourthmenu.childMenus==null || fn:length(fourthmenu.childMenus)==0 }">
																					<li><a class="J_menuItem"
																						href="${path }${fourthmenu.uri}"><i
																							class="fa ${fourthmenu.icon }"></i> <span
																							class="nav-label">${fourthmenu.menuname}</span></a></li>
																				</c:when>
																				<c:otherwise>
																				</c:otherwise>
																			</c:choose>
																		</c:forEach>
																		
																	</ul></li>
															</c:otherwise>

														</c:choose>
													</c:forEach>
													
												</ul></li>
										</c:otherwise>

									</c:choose>
								</c:forEach>
								
							</ul></li>
					</c:otherwise>
				</c:choose>

			</c:forEach>
			<%--                     <li>
                        <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">系统设置</span> <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                        	<li><a class="J_menuItem" href="dashboard_2.html">用户管理</a></li>
                            <li><a class="J_menuItem" href="dashboard_2.html">菜单管理</a></li>
                            <li><a class="J_menuItem" href="dashboard_2.html">日志管理</a></li>
                        </ul>
                    </li>
                    <li>
                        <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">产品管理</span> <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                        	<li><a class="J_menuItem" href="dashboard_2.html">产品列表</a></li>
                            <li><a class="J_menuItem" href="dashboard_2.html">产品更新</a></li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">应用中心管理</span> <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                        	<li><a class="J_menuItem" href="${path}/app/index">应用管理</a></li>
                            <li><a class="J_menuItem" href="${path}/sort/index">分类管理</a></li>
                            <li><a class="J_menuItem" href="${path}/specialtopic/index">专题管理</a></li>
                            <li><a class="J_menuItem" href="${path}/indiv/index">前端定制</a></li>
                            <li><a class="J_menuItem" href="${path}/indivConf/index">配置更新</a></li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="index.html"><i class="fa fa-th-large"></i> <span class="nav-label">统计</span> <span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level collapse">
                        	<li><a class="J_menuItem" href="dashboard_2.html">应用平台产品统计</a></li>
                            <li><a class="J_menuItem" href="${path}/appstatistics/index">应用中心分析统计</a></li>
                        </ul>
                    </li> --%>
		</ul>

	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		//alert("${menuList}");
	});
	
	function logout() {
		window.location.href = "${spath}/user/logout";
	}
</script>
</html>
