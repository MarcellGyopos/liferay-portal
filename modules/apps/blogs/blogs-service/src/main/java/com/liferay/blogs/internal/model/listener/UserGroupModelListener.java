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

package com.liferay.blogs.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * @author Marcell Gyöpös
 */
@Component(immediate = true, service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onBeforeRemoveAssociation(
			Object userGroupId, String associationClassName,
			Object associationClassPK)
		throws ModelListenerException {

		try {
			if (associationClassName.equals(User.class.getName())) {
				unsubscribeDeletedGroupMemberFromSite(
					(long)associationClassPK, (long)userGroupId);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unsubscribe user from group failed because:\n" + exception);
		}

		try {
			if (associationClassName.equals(Group.class.getName())) {
				unsubscribeGroupFromSite(
					(long)associationClassPK, (long)userGroupId);
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unsubscribe group from site failed because:\n" + exception);
		}
	}

	public void unsubscribeDeletedGroupMemberFromSite(
		long userId, long userGroupId) {

		long[] groupIds = _userGroupLocalService.getGroupPrimaryKeys(
			userGroupId);
		long[] userIds = {userId};

		unsubscribeUserFromSite(userGroupId, userIds, groupIds);
	}

	public void unsubscribeGroupFromSite(long groupId, long userGroupId) {
		long[] userIds = _userGroupLocalService.getUserPrimaryKeys(userGroupId);
		long[] groupIds = {groupId};

		unsubscribeUserFromSite(userGroupId, userIds, groupIds);
	}

	public void unsubscribeUserFromSite(
		long userGroupId, long[] userIds, long[] groupIds) {

		for (long userId : userIds) {
			Map<Long, long[]> userGroupGroupIds = new HashMap<>();
			List<UserGroup> userGroups =
				_userGroupLocalService.getUserUserGroups(userId);
			long[] userIdsTemp = {userId};

			for (UserGroup ug : userGroups) {
				if (ug.getUserGroupId() != userGroupId) {
					userGroupGroupIds.put(
						ug.getPrimaryKey(),
						_userGroupLocalService.getGroupPrimaryKeys(
							ug.getUserGroupId()));
				}
			}

			for (long s : groupIds) {
				boolean siteContainsGroup = false;

				for (Map.Entry<Long, long[]> entry :
						userGroupGroupIds.entrySet()) {

					if (LongStream.of(
							entry.getValue()
						).anyMatch(
							x -> x == s
						)) {

						siteContainsGroup = true;
					}
				}

				long[] groupIdUserIds = _userLocalService.getGroupUserIds(s);

				if (!siteContainsGroup &&
					!LongStream.of(
						groupIdUserIds
					).anyMatch(
						x -> x == userId
					)) {

					TransactionCommitCallbackUtil.registerCallback(
						() -> {
							Message message = new Message();

							message.put("groupId", s);
							message.put("userIds", userIdsTemp);

							MessageBusUtil.sendMessage(
								DestinationNames.SUBSCRIPTION_CLEAN_UP,
								message);

							return null;
						});
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserGroupModelListener.class);

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}