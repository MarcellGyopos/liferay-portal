<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", tabs1Default);
String tabs2 = ParamUtil.getString(request, "tabs2", "open");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setParameter(
	"tabs1", tabs1
).setParameter(
	"tabs2", tabs2
).setWindowState(
	WindowState.NORMAL
).build();
%>

<%@ include file="/tasks/tabs1.jspf" %>

<div class="control-wrapper">
	<c:if test="<%= TasksPermission.contains(permissionChecker, themeDisplay.getScopeGroupId(), ActionKeys.ADD_ENTRY) %>">
		<portlet:renderURL var="addkURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcPath" value="/tasks/edit_task.jsp" />
		</portlet:renderURL>

		<%
		String taglibOnClick = "Liferay.Tasks.displayPopup('" + addkURL.toString() + "', '" + LanguageUtil.get(request, "add-task") + "');";
		%>

		<liferay-ui:icon
			icon="plus"
			label="<%= true %>"
			markupView="lexicon"
			message="add-task"
			onClick="<%= taglibOnClick %>"
			url="javascript:;"
		/>
	</c:if>

	<c:if test="<%= TasksPermission.contains(permissionChecker, themeDisplay.getScopeGroupId(), ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="com.liferay.tasks"
			modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
			resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			icon="lock"
			label="<%= true %>"
			markupView="lexicon"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<liferay-ui:icon
		cssClass="filter-tasks"
		icon="tag"
		label="<%= true %>"
		markupView="lexicon"
		message="filter"
		onClick="Liferay.Tasks.toggleTasksFilter()"
		url="javascript:;"
	/>

	<div style="clear: both;"><!-- --></div>
</div>

<div class="filter-wrapper hide">
	<%@ include file="/tasks/view_tasks_filter.jspf" %>
</div>

<div class="list-wrapper">
	<liferay-util:include page="/tasks/view_tasks.jsp" servletContext="<%= application %>" />
</div>

<div class="tasks-options">
	<table>
		<tr>
			<td>
				<input name="all-tasks" onclick="Liferay.Tasks.updateTaskList(null, this.checked);" type="checkbox" <%= tabs2.equals("all") ? "checked" : StringPool.BLANK %> />
			</td>
			<td>
				<liferay-ui:message key="show-completed-tasks" />
			</td>
		</tr>
	</table>
</div>

<aui:script use="liferay-tasks">
	Liferay.Tasks.init(
		{
			baseActionURL: '<%= PortletURLFactoryUtil.create(request, portletDisplay.getId(), themeDisplay.getPlid(), PortletRequest.ACTION_PHASE) %>',
			currentTab: '<%= HtmlUtil.escape(tabs1) %>',
			namespace: '<portlet:namespace />',
			taskListURL: '<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/tasks/view_tasks.jsp" /></portlet:renderURL>'
		}
	);
</aui:script>