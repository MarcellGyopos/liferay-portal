/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.user.groups.admin.modellistener;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.subscription.service.impl.SubscriptionLocalServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcell Gyöpös
 */
public class UserGroupModelListenerTest {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteUsersFromGroupWhenUserDoesntHaveAnotherGroupAndDirectMembership()
		throws Exception {

		long[]  userIds = addUsers();
		UserGroup standardUserGroup = UserGroupTestUtil.addUserGroup();

		_userGroupLocalService.addUserUserGroup(userIds[0], standardUserGroup);

		_groupLocalService.addUserGroupGroup(standardUserGroup.getGroupId(),group);

		_blogLayout = LayoutTestUtil.addLayout(group);

		String portletId = PortletProviderUtil.getPortletId(
			BlogsEntry.class.getName(), PortletProvider.Action.VIEW);

		LayoutTestUtil.addPortletToLayout(_blogLayout, portletId);

		BlogsEntryLocalServiceUtil.subscribe(userIds[0], group.getGroupId());

		_userGroupLocalService.deleteUserUserGroup(userIds[0],standardUserGroup);

		Assert.assertFalse(_subscriptionLocalService.isSubscribed(group.getCompanyId(),userIds[0],BlogsEntry.class.getName(),group.getGroupId()));

	}


	@Test
	public void testDeleteUsersFromGroupWhenUserHasAnotherGroup(){}

	@Test
	public void testDeleteUsersFromGroupWhenUserHasDirectMembership(){}

	@Test
	public void testDeleteUserGroupFromSiteWhenUserDoesntHaveAnotherGroupAndDirectMembership(){}

	@Test
	public void testDeleteUserGroupFromSiteWhenUserHasAnotherGroup(){}

	@Test
	public void testDeleteUserGroupFromSiteWhenUserHasDirectMembership(){}


	protected long[] addUsers() throws Exception {
		User user1 = UserTestUtil.addUser(group.getGroupId());

		_userIds[0] = user1.getUserId();

		User user2 = UserTestUtil.addUser(
			RandomTestUtil.randomString(), group.getGroupId());

		_userIds[1] = user2.getUserId();

		return _userIds;
	}


	public void tearDown() throws Exception {
		_userIds = new long[2];
	}

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SubscriptionLocalServiceImpl _subscriptionLocalService;


	@DeleteAfterTestRun
	protected Group group;

	private static long[] _userIds = new long[2];

	private Layout _blogLayout;


}
