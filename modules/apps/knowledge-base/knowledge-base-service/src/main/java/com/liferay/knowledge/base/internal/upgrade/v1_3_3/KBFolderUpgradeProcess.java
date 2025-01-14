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

package com.liferay.knowledge.base.internal.upgrade.v1_3_3;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class KBFolderUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		Map<Long, String> urlTitles = _getInitialUrlTitles(connection);

		for (Map.Entry<Long, String> entry : urlTitles.entrySet()) {
			String uniqueUrlTitle = _findUniqueUrlTitle(
				connection, entry.getValue());

			for (int i = 1; uniqueUrlTitle == null; i++) {
				uniqueUrlTitle = _findUniqueUrlTitle(
					connection, entry.getValue() + StringPool.DASH + i);
			}

			_updateKBFolder(connection, entry.getKey(), uniqueUrlTitle);
		}
	}

	private String _findUniqueUrlTitle(Connection con, String urlTitle)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				"select count(*) from KBFolder where KBFolder.urlTitle like " +
					"?")) {

			ps.setString(1, urlTitle + "%");

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return urlTitle;
				}

				int kbFolderCount = rs.getInt(1);

				if (kbFolderCount == 0) {
					return urlTitle;
				}

				return null;
			}
		}
	}

	private Map<Long, String> _getInitialUrlTitles(Connection con)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				"select kbFolderId, name from KBFolder where " +
					"(KBFolder.urlTitle is null) or (KBFolder.urlTitle = '')");
			ResultSet rs = ps.executeQuery()) {

			Map<Long, String> urlTitles = new HashMap<>();

			while (rs.next()) {
				long kbFolderId = rs.getLong(1);
				String name = rs.getString(2);

				String urlTitle = _getUrlTitle(kbFolderId, name);

				urlTitles.put(kbFolderId, urlTitle);
			}

			return urlTitles;
		}
	}

	/**
	 * See {@link
	 * com.liferay.knowledge.base.util.KnowledgeBaseUtil#getUrlTitle(long,
	 * String)}
	 */
	private String _getUrlTitle(long id, String title) {
		if (title == null) {
			return String.valueOf(id);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(id);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				title);
		}

		return title.substring(0, 75);
	}

	private void _updateKBFolder(
			Connection con, long kbFolderId, String urlTitle)
		throws Exception {

		try (PreparedStatement ps = con.prepareStatement(
				"update KBFolder set KBFolder.urlTitle = ? where " +
					"KBFolder.kbFolderId = ?")) {

			ps.setString(1, urlTitle);
			ps.setLong(2, kbFolderId);

			ps.execute();
		}
	}

}