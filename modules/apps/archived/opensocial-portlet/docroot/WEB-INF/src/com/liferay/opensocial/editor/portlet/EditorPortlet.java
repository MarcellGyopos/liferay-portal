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

package com.liferay.opensocial.editor.portlet;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.comparator.FolderNameComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.opensocial.admin.portlet.AdminPortlet;
import com.liferay.opensocial.model.Gadget;
import com.liferay.opensocial.service.GadgetLocalServiceUtil;
import com.liferay.opensocial.service.permission.GadgetPermission;
import com.liferay.opensocial.shindig.util.ShindigUtil;
import com.liferay.opensocial.util.ActionKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.shindig.gadgets.spec.Feature;
import org.apache.shindig.gadgets.spec.GadgetSpec;
import org.apache.shindig.gadgets.spec.ModulePrefs;

/**
 * @author Dennis Ju
 */
public class EditorPortlet extends AdminPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		try {
			Class<?> clazz = getClass();

			AuthTokenUtil.checkCSRFToken(
				PortalUtil.getHttpServletRequest(resourceRequest),
				clazz.getName());

			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("addFileEntry")) {
				serveAddFileEntry(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("addFolder")) {
				serveAddFolder(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("deleteFileEntry")) {
				serveDeleteFileEntry(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("deleteFolder")) {
				serveDeleteFolder(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("getFileEntryContent")) {
				serveGetFileEntryContent(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("getFolderChildren")) {
				serveGetFolderChildren(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("getRenderParameters")) {
				serveGetRenderParameters(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("updateFileEntryContent")) {
				serveUpdateFileEntryContent(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("updateFileEntryTitle")) {
				serveUpdateFileEntryTitle(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("updateFolderName")) {
				serveUpdateFolderName(resourceRequest, resourceResponse);
			}
		}
		catch (IOException ioException) {
			serveException(ioException, resourceRequest, resourceResponse);

			throw ioException;
		}
		catch (PortletException portletException) {
			serveException(portletException, resourceRequest, resourceResponse);

			throw portletException;
		}
		catch (Exception exception) {
			serveException(exception, resourceRequest, resourceResponse);

			throw new PortletException(exception);
		}
	}

	@Override
	public void updateGadget(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long groupId = themeDisplay.getScopeGroupId();

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			GadgetPermission.check(
				permissionChecker, groupId, ActionKeys.PUBLISH_GADGET);

			Gadget gadget = doAddGadget(actionRequest, actionResponse);

			String publishGadgetRedirect = ParamUtil.getString(
				actionRequest, "publishGadgetRedirect");

			boolean unpublishPermission = GadgetPermission.contains(
				permissionChecker, groupId, gadget.getGadgetId(),
				ActionKeys.DELETE);

			publishGadgetRedirect = HttpUtil.addParameter(
				publishGadgetRedirect, "unpublishPermission",
				unpublishPermission);

			publishGadgetRedirect = HttpUtil.addParameter(
				publishGadgetRedirect, "gadgetId", gadget.getGadgetId());

			actionResponse.sendRedirect(publishGadgetRedirect);
		}
		else {
			long gadgetId = ParamUtil.getLong(actionRequest, "gadgetId");

			GadgetPermission.check(
				permissionChecker, groupId, gadgetId, ActionKeys.UPDATE);

			doUpdateGadget(actionRequest, actionResponse);
		}
	}

	protected void serveAddFileEntry(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		Folder folder = DLAppServiceUtil.getFolder(folderId);

		String fileEntryTitle = ParamUtil.getString(
			resourceRequest, "fileEntryTitle");

		String content = ParamUtil.getString(resourceRequest, "content");

		byte[] bytes = content.getBytes(StringPool.UTF8);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		serviceContext.setAttribute("sourceFileName", fileEntryTitle);

		serviceContext.setScopeGroupId(folder.getGroupId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			folder.getRepositoryId(), folderId, fileEntryTitle,
			resourceRequest.getContentType(), fileEntryTitle, StringPool.BLANK,
			StringPool.BLANK, bytes, serviceContext);

		JSONObject jsonObject = JSONUtil.put(
			"fileEntryId", fileEntry.getFileEntryId());

		String fileEntryURL = ShindigUtil.getFileEntryURL(
			PortalUtil.getPortalURL(themeDisplay), fileEntry.getFileEntryId());

		jsonObject.put("fileEntryURL", fileEntryURL);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveAddFolder(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long parentFolderId = ParamUtil.getLong(
			resourceRequest, "parentFolderId");

		Folder parentFolder = DLAppServiceUtil.getFolder(parentFolderId);

		String folderName = ParamUtil.getString(resourceRequest, "folderName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(parentFolder.getGroupId());

		Folder folder = DLAppServiceUtil.addFolder(
			parentFolder.getRepositoryId(), parentFolderId, folderName,
			StringPool.BLANK, serviceContext);

		JSONObject jsonObject = JSONUtil.put("folderId", folder.getFolderId());

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveDeleteFileEntry(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		DLAppServiceUtil.deleteFileEntry(fileEntryId);
	}

	protected void serveDeleteFolder(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		DLAppServiceUtil.deleteFolder(folderId);
	}

	protected void serveException(
			Exception exception, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException {

		JSONObject errorJSONObject = JSONUtil.put(
			"message", exception.getLocalizedMessage());

		Class<?> clazz = exception.getClass();

		errorJSONObject.put("name", clazz.getSimpleName());

		JSONObject jsonObject = JSONUtil.put("error", errorJSONObject);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveGetFileEntryContent(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

		String content = StringUtil.read(fileEntry.getContentStream());

		JSONObject jsonObject = JSONUtil.put("content", content);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveGetFolderChildren(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long repositoryId = ParamUtil.getLong(resourceRequest, "repositoryId");
		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		List<Folder> folders = DLAppServiceUtil.getFolders(
			repositoryId, folderId);

		folders = ListUtil.sort(folders, new FolderNameComparator(true));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Folder folder : folders) {
			jsonArray.put(
				JSONUtil.put(
					"entryId", folder.getFolderId()
				).put(
					"label", folder.getName()
				).put(
					"leaf", false
				).put(
					"type", "editor"
				));
		}

		boolean getFileEntries = ParamUtil.getBoolean(
			resourceRequest, "getFileEntries");

		if (getFileEntries) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
				repositoryId, folderId);

			fileEntries = ListUtil.sort(
				fileEntries,
				new RepositoryModelTitleComparator<FileEntry>(true));

			for (FileEntry fileEntry : fileEntries) {
				JSONObject jsonObject = JSONUtil.put(
					"entryId", fileEntry.getFileEntryId());

				String fileEntryURL = ShindigUtil.getFileEntryURL(
					PortalUtil.getPortalURL(themeDisplay),
					fileEntry.getFileEntryId());

				jsonObject.put("fileEntryURL", fileEntryURL);

				long gadgetId = 0;

				try {
					Gadget gadget = GadgetLocalServiceUtil.getGadget(
						themeDisplay.getCompanyId(), fileEntryURL);

					gadgetId = gadget.getGadgetId();
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}

				jsonObject.put(
					"gadgetId", gadgetId
				).put(
					"label", fileEntry.getTitle()
				).put(
					"leaf", true
				);

				JSONObject permissionsJSONObject =
					JSONFactoryUtil.createJSONObject();

				if (gadgetId > 0) {
					boolean unpublishPermission = GadgetPermission.contains(
						permissionChecker, themeDisplay.getScopeGroupId(),
						gadgetId, ActionKeys.DELETE);

					permissionsJSONObject.put(
						"unpublishPermission", unpublishPermission);
				}

				jsonObject.put(
					"permissions", permissionsJSONObject
				).put(
					"type", "editor"
				);

				jsonArray.put(jsonObject);
			}
		}

		writeJSON(resourceRequest, resourceResponse, jsonArray);
	}

	protected void serveGetRenderParameters(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String fileEntryURL = ParamUtil.getString(
			resourceRequest, "fileEntryURL");

		GadgetSpec gadgetSpec = ShindigUtil.getGadgetSpec(
			fileEntryURL, true, true);

		ModulePrefs modulePrefs = gadgetSpec.getModulePrefs();

		JSONObject jsonObject = JSONUtil.put("height", modulePrefs.getHeight());

		long moduleId = ShindigUtil.getModuleId(
			resourceResponse.getNamespace());

		jsonObject.put("moduleId", moduleId);

		Map<String, Feature> features = modulePrefs.getFeatures();

		boolean requiresPubsub = features.containsKey("pubsub-2");

		jsonObject.put(
			"requiresPubsub", requiresPubsub
		).put(
			"scrolling", modulePrefs.getScrolling()
		);

		String secureToken = ShindigUtil.createSecurityToken(
			ShindigUtil.getOwnerId(themeDisplay.getLayout()),
			themeDisplay.getUserId(), fileEntryURL,
			PortalUtil.getPortalURL(themeDisplay), fileEntryURL, moduleId,
			PortalUtil.getCurrentURL(resourceRequest));

		jsonObject.put(
			"secureToken", secureToken
		).put(
			"specUrl", fileEntryURL
		);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveUpdateFileEntryContent(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

		String content = ParamUtil.getString(resourceRequest, "content");

		byte[] bytes = content.getBytes(StringPool.UTF8);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		DLAppServiceUtil.updateFileEntry(
			fileEntryId, fileEntry.getTitle(), resourceRequest.getContentType(),
			fileEntry.getTitle(), fileEntry.getDescription(), StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false), bytes,
			serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveUpdateFileEntryTitle(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fileEntryId = ParamUtil.getLong(resourceRequest, "fileEntryId");

		FileEntry fileEntry = DLAppServiceUtil.getFileEntry(fileEntryId);

		String fileEntryTitle = ParamUtil.getString(
			resourceRequest, "fileEntryTitle");

		byte[] bytes = null;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		DLAppServiceUtil.updateFileEntry(
			fileEntryId, fileEntryTitle, resourceRequest.getContentType(),
			fileEntryTitle, fileEntry.getDescription(), StringPool.BLANK,
			DLVersionNumberIncrease.fromMajorVersion(false), bytes,
			serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveUpdateFolderName(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long folderId = ParamUtil.getLong(resourceRequest, "folderId");

		Folder folder = DLAppServiceUtil.getFolder(folderId);

		String folderName = ParamUtil.getString(resourceRequest, "folderName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		DLAppServiceUtil.updateFolder(
			folderId, folderName, folder.getDescription(), serviceContext);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(EditorPortlet.class);

}