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

package com.liferay.layout.reports.web.internal.portlet;

import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedCompanyConfiguration;
import com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsPortletKeys;
import com.liferay.layout.reports.web.internal.constants.LayoutReportsWebKeys;
import com.liferay.layout.reports.web.internal.data.provider.LayoutReportsDataProvider;
import com.liferay.layout.reports.web.internal.display.context.LayoutReportsDisplayContext;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.layout.reports.web.internal.configuration.LayoutReportsGooglePageSpeedConfiguration",
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Page Audit",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutReportsPortletKeys.LAYOUT_REPORTS,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = {LayoutReportsPortlet.class, Portlet.class}
)
public class LayoutReportsPortlet extends MVCPortlet {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutReportsGooglePageSpeedConfiguration =
			ConfigurableUtil.createConfigurable(
				LayoutReportsGooglePageSpeedConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletRequest originalHttpServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		String layoutMode = ParamUtil.getString(
			originalHttpServletRequest, "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		try {
			Group group = _groupLocalService.getGroup(
				_portal.getScopeGroupId(httpServletRequest));

			if (!_isEnabled(group)) {
				return;
			}

			httpServletRequest.setAttribute(
				LayoutReportsWebKeys.LAYOUT_REPORTS_DISPLAY_CONTEXT,
				new LayoutReportsDisplayContext(
					_groupLocalService, _infoItemServiceTracker,
					_layoutLocalService,
					new LayoutReportsDataProvider(_getApiKey(group)),
					_layoutSEOLinkManager, _language, _portal, renderRequest));

			super.doDispatch(renderRequest, renderResponse);
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	private String _getApiKey(Group group) throws ConfigurationException {
		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String googlePageSpeedApikey = unicodeProperties.getProperty(
			"googlePageSpeedApiKey");

		if (Validator.isNotNull(googlePageSpeedApikey)) {
			return googlePageSpeedApikey;
		}

		return _getApiKey(group.getCompanyId());
	}

	private String _getApiKey(long companyId) throws ConfigurationException {
		LayoutReportsGooglePageSpeedCompanyConfiguration
			layoutReportsGooglePageSpeedCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutReportsGooglePageSpeedCompanyConfiguration.class,
					companyId);

		String apiKey =
			layoutReportsGooglePageSpeedCompanyConfiguration.apiKey();

		if (Validator.isNotNull(apiKey)) {
			return apiKey;
		}

		return _layoutReportsGooglePageSpeedConfiguration.apiKey();
	}

	private boolean _isEnabled(Group group) throws ConfigurationException {
		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		return GetterUtil.getBoolean(
			unicodeProperties.getProperty("googlePageSpeedEnabled"),
			_isEnabled(group.getCompanyId()));
	}

	private boolean _isEnabled(long companyId) throws ConfigurationException {
		if (!_layoutReportsGooglePageSpeedConfiguration.enabled()) {
			return false;
		}

		LayoutReportsGooglePageSpeedCompanyConfiguration
			layoutReportsGooglePageSpeedCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					LayoutReportsGooglePageSpeedCompanyConfiguration.class,
					companyId);

		if (!layoutReportsGooglePageSpeedCompanyConfiguration.enabled()) {
			return false;
		}

		return true;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private volatile LayoutReportsGooglePageSpeedConfiguration
		_layoutReportsGooglePageSpeedConfiguration;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}