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

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.model.CalendarNotificationTemplateModel;
import com.liferay.calendar.model.CalendarNotificationTemplateSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the CalendarNotificationTemplate service. Represents a row in the &quot;CalendarNotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CalendarNotificationTemplateModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CalendarNotificationTemplateImpl}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplateImpl
 * @generated
 */
@JSON(strict = true)
public class CalendarNotificationTemplateModelImpl
	extends BaseModelImpl<CalendarNotificationTemplate>
	implements CalendarNotificationTemplateModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a calendar notification template model instance should use the <code>CalendarNotificationTemplate</code> interface instead.
	 */
	public static final String TABLE_NAME = "CalendarNotificationTemplate";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"calendarNotificationTemplateId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"calendarId", Types.BIGINT}, {"notificationType", Types.VARCHAR},
		{"notificationTypeSettings", Types.VARCHAR},
		{"notificationTemplateType", Types.VARCHAR}, {"subject", Types.VARCHAR},
		{"body", Types.CLOB}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("calendarNotificationTemplateId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("calendarId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("notificationType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("notificationTypeSettings", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("notificationTemplateType", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("subject", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("body", Types.CLOB);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CalendarNotificationTemplate (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,calendarNotificationTemplateId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,calendarId LONG,notificationType VARCHAR(75) null,notificationTypeSettings VARCHAR(150) null,notificationTemplateType VARCHAR(75) null,subject VARCHAR(75) null,body TEXT null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP =
		"drop table CalendarNotificationTemplate";

	public static final String ORDER_BY_JPQL =
		" ORDER BY calendarNotificationTemplate.calendarNotificationTemplateId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CalendarNotificationTemplate.calendarNotificationTemplateId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CALENDARID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long COMPANYID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long GROUPID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NOTIFICATIONTEMPLATETYPE_COLUMN_BITMASK = 8L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long NOTIFICATIONTYPE_COLUMN_BITMASK = 16L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)
	 */
	@Deprecated
	public static final long UUID_COLUMN_BITMASK = 32L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)
	 */
	@Deprecated
	public static final long CALENDARNOTIFICATIONTEMPLATEID_COLUMN_BITMASK =
		64L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CalendarNotificationTemplate toModel(
		CalendarNotificationTemplateSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CalendarNotificationTemplate model =
			new CalendarNotificationTemplateImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setUuid(soapModel.getUuid());
		model.setCalendarNotificationTemplateId(
			soapModel.getCalendarNotificationTemplateId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCalendarId(soapModel.getCalendarId());
		model.setNotificationType(soapModel.getNotificationType());
		model.setNotificationTypeSettings(
			soapModel.getNotificationTypeSettings());
		model.setNotificationTemplateType(
			soapModel.getNotificationTemplateType());
		model.setSubject(soapModel.getSubject());
		model.setBody(soapModel.getBody());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<CalendarNotificationTemplate> toModels(
		CalendarNotificationTemplateSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CalendarNotificationTemplate> models =
			new ArrayList<CalendarNotificationTemplate>(soapModels.length);

		for (CalendarNotificationTemplateSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public CalendarNotificationTemplateModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _calendarNotificationTemplateId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCalendarNotificationTemplateId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _calendarNotificationTemplateId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CalendarNotificationTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return CalendarNotificationTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CalendarNotificationTemplate, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CalendarNotificationTemplate, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CalendarNotificationTemplate, Object>
				attributeGetterFunction = entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply(
					(CalendarNotificationTemplate)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CalendarNotificationTemplate, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CalendarNotificationTemplate, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CalendarNotificationTemplate)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CalendarNotificationTemplate, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CalendarNotificationTemplate, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CalendarNotificationTemplate>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CalendarNotificationTemplate.class.getClassLoader(),
			CalendarNotificationTemplate.class, ModelWrapper.class);

		try {
			Constructor<CalendarNotificationTemplate> constructor =
				(Constructor<CalendarNotificationTemplate>)
					proxyClass.getConstructor(InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map
		<String, Function<CalendarNotificationTemplate, Object>>
			_attributeGetterFunctions;
	private static final Map
		<String, BiConsumer<CalendarNotificationTemplate, Object>>
			_attributeSetterBiConsumers;

	static {
		Map<String, Function<CalendarNotificationTemplate, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CalendarNotificationTemplate, Object>>();
		Map<String, BiConsumer<CalendarNotificationTemplate, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CalendarNotificationTemplate, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", CalendarNotificationTemplate::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::setMvccVersion);
		attributeGetterFunctions.put(
			"uuid", CalendarNotificationTemplate::getUuid);
		attributeSetterBiConsumers.put(
			"uuid",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setUuid);
		attributeGetterFunctions.put(
			"calendarNotificationTemplateId",
			CalendarNotificationTemplate::getCalendarNotificationTemplateId);
		attributeSetterBiConsumers.put(
			"calendarNotificationTemplateId",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::
					setCalendarNotificationTemplateId);
		attributeGetterFunctions.put(
			"groupId", CalendarNotificationTemplate::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::setGroupId);
		attributeGetterFunctions.put(
			"companyId", CalendarNotificationTemplate::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::setCompanyId);
		attributeGetterFunctions.put(
			"userId", CalendarNotificationTemplate::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::setUserId);
		attributeGetterFunctions.put(
			"userName", CalendarNotificationTemplate::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setUserName);
		attributeGetterFunctions.put(
			"createDate", CalendarNotificationTemplate::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CalendarNotificationTemplate, Date>)
				CalendarNotificationTemplate::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CalendarNotificationTemplate::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CalendarNotificationTemplate, Date>)
				CalendarNotificationTemplate::setModifiedDate);
		attributeGetterFunctions.put(
			"calendarId", CalendarNotificationTemplate::getCalendarId);
		attributeSetterBiConsumers.put(
			"calendarId",
			(BiConsumer<CalendarNotificationTemplate, Long>)
				CalendarNotificationTemplate::setCalendarId);
		attributeGetterFunctions.put(
			"notificationType",
			CalendarNotificationTemplate::getNotificationType);
		attributeSetterBiConsumers.put(
			"notificationType",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setNotificationType);
		attributeGetterFunctions.put(
			"notificationTypeSettings",
			CalendarNotificationTemplate::getNotificationTypeSettings);
		attributeSetterBiConsumers.put(
			"notificationTypeSettings",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setNotificationTypeSettings);
		attributeGetterFunctions.put(
			"notificationTemplateType",
			CalendarNotificationTemplate::getNotificationTemplateType);
		attributeSetterBiConsumers.put(
			"notificationTemplateType",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setNotificationTemplateType);
		attributeGetterFunctions.put(
			"subject", CalendarNotificationTemplate::getSubject);
		attributeSetterBiConsumers.put(
			"subject",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setSubject);
		attributeGetterFunctions.put(
			"body", CalendarNotificationTemplate::getBody);
		attributeSetterBiConsumers.put(
			"body",
			(BiConsumer<CalendarNotificationTemplate, String>)
				CalendarNotificationTemplate::setBody);
		attributeGetterFunctions.put(
			"lastPublishDate",
			CalendarNotificationTemplate::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<CalendarNotificationTemplate, Date>)
				CalendarNotificationTemplate::setLastPublishDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_uuid = uuid;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalUuid() {
		return getColumnOriginalValue("uuid_");
	}

	@JSON
	@Override
	public long getCalendarNotificationTemplateId() {
		return _calendarNotificationTemplateId;
	}

	@Override
	public void setCalendarNotificationTemplateId(
		long calendarNotificationTemplateId) {

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_calendarNotificationTemplateId = calendarNotificationTemplateId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalGroupId() {
		return GetterUtil.getLong(this.<Long>getColumnOriginalValue("groupId"));
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCompanyId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("companyId"));
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCalendarId() {
		return _calendarId;
	}

	@Override
	public void setCalendarId(long calendarId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_calendarId = calendarId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCalendarId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("calendarId"));
	}

	@JSON
	@Override
	public String getNotificationType() {
		if (_notificationType == null) {
			return "";
		}
		else {
			return _notificationType;
		}
	}

	@Override
	public void setNotificationType(String notificationType) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_notificationType = notificationType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalNotificationType() {
		return getColumnOriginalValue("notificationType");
	}

	@JSON
	@Override
	public String getNotificationTypeSettings() {
		if (_notificationTypeSettings == null) {
			return "";
		}
		else {
			return _notificationTypeSettings;
		}
	}

	@Override
	public void setNotificationTypeSettings(String notificationTypeSettings) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_notificationTypeSettings = notificationTypeSettings;
	}

	@JSON
	@Override
	public String getNotificationTemplateType() {
		if (_notificationTemplateType == null) {
			return "";
		}
		else {
			return _notificationTemplateType;
		}
	}

	@Override
	public void setNotificationTemplateType(String notificationTemplateType) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_notificationTemplateType = notificationTemplateType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public String getOriginalNotificationTemplateType() {
		return getColumnOriginalValue("notificationTemplateType");
	}

	@JSON
	@Override
	public String getSubject() {
		if (_subject == null) {
			return "";
		}
		else {
			return _subject;
		}
	}

	@Override
	public void setSubject(String subject) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_subject = subject;
	}

	@JSON
	@Override
	public String getBody() {
		if (_body == null) {
			return "";
		}
		else {
			return _body;
		}
	}

	@Override
	public void setBody(String body) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_body = body;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(
				CalendarNotificationTemplate.class.getName()));
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (entry.getValue() != getColumnValue(entry.getKey())) {
				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), CalendarNotificationTemplate.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CalendarNotificationTemplate toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CalendarNotificationTemplate>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CalendarNotificationTemplateImpl calendarNotificationTemplateImpl =
			new CalendarNotificationTemplateImpl();

		calendarNotificationTemplateImpl.setMvccVersion(getMvccVersion());
		calendarNotificationTemplateImpl.setUuid(getUuid());
		calendarNotificationTemplateImpl.setCalendarNotificationTemplateId(
			getCalendarNotificationTemplateId());
		calendarNotificationTemplateImpl.setGroupId(getGroupId());
		calendarNotificationTemplateImpl.setCompanyId(getCompanyId());
		calendarNotificationTemplateImpl.setUserId(getUserId());
		calendarNotificationTemplateImpl.setUserName(getUserName());
		calendarNotificationTemplateImpl.setCreateDate(getCreateDate());
		calendarNotificationTemplateImpl.setModifiedDate(getModifiedDate());
		calendarNotificationTemplateImpl.setCalendarId(getCalendarId());
		calendarNotificationTemplateImpl.setNotificationType(
			getNotificationType());
		calendarNotificationTemplateImpl.setNotificationTypeSettings(
			getNotificationTypeSettings());
		calendarNotificationTemplateImpl.setNotificationTemplateType(
			getNotificationTemplateType());
		calendarNotificationTemplateImpl.setSubject(getSubject());
		calendarNotificationTemplateImpl.setBody(getBody());
		calendarNotificationTemplateImpl.setLastPublishDate(
			getLastPublishDate());

		calendarNotificationTemplateImpl.resetOriginalValues();

		return calendarNotificationTemplateImpl;
	}

	@Override
	public int compareTo(
		CalendarNotificationTemplate calendarNotificationTemplate) {

		long primaryKey = calendarNotificationTemplate.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CalendarNotificationTemplate)) {
			return false;
		}

		CalendarNotificationTemplate calendarNotificationTemplate =
			(CalendarNotificationTemplate)object;

		long primaryKey = calendarNotificationTemplate.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<CalendarNotificationTemplate> toCacheModel() {
		CalendarNotificationTemplateCacheModel
			calendarNotificationTemplateCacheModel =
				new CalendarNotificationTemplateCacheModel();

		calendarNotificationTemplateCacheModel.mvccVersion = getMvccVersion();

		calendarNotificationTemplateCacheModel.uuid = getUuid();

		String uuid = calendarNotificationTemplateCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			calendarNotificationTemplateCacheModel.uuid = null;
		}

		calendarNotificationTemplateCacheModel.calendarNotificationTemplateId =
			getCalendarNotificationTemplateId();

		calendarNotificationTemplateCacheModel.groupId = getGroupId();

		calendarNotificationTemplateCacheModel.companyId = getCompanyId();

		calendarNotificationTemplateCacheModel.userId = getUserId();

		calendarNotificationTemplateCacheModel.userName = getUserName();

		String userName = calendarNotificationTemplateCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			calendarNotificationTemplateCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			calendarNotificationTemplateCacheModel.createDate =
				createDate.getTime();
		}
		else {
			calendarNotificationTemplateCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			calendarNotificationTemplateCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			calendarNotificationTemplateCacheModel.modifiedDate =
				Long.MIN_VALUE;
		}

		calendarNotificationTemplateCacheModel.calendarId = getCalendarId();

		calendarNotificationTemplateCacheModel.notificationType =
			getNotificationType();

		String notificationType =
			calendarNotificationTemplateCacheModel.notificationType;

		if ((notificationType != null) && (notificationType.length() == 0)) {
			calendarNotificationTemplateCacheModel.notificationType = null;
		}

		calendarNotificationTemplateCacheModel.notificationTypeSettings =
			getNotificationTypeSettings();

		String notificationTypeSettings =
			calendarNotificationTemplateCacheModel.notificationTypeSettings;

		if ((notificationTypeSettings != null) &&
			(notificationTypeSettings.length() == 0)) {

			calendarNotificationTemplateCacheModel.notificationTypeSettings =
				null;
		}

		calendarNotificationTemplateCacheModel.notificationTemplateType =
			getNotificationTemplateType();

		String notificationTemplateType =
			calendarNotificationTemplateCacheModel.notificationTemplateType;

		if ((notificationTemplateType != null) &&
			(notificationTemplateType.length() == 0)) {

			calendarNotificationTemplateCacheModel.notificationTemplateType =
				null;
		}

		calendarNotificationTemplateCacheModel.subject = getSubject();

		String subject = calendarNotificationTemplateCacheModel.subject;

		if ((subject != null) && (subject.length() == 0)) {
			calendarNotificationTemplateCacheModel.subject = null;
		}

		calendarNotificationTemplateCacheModel.body = getBody();

		String body = calendarNotificationTemplateCacheModel.body;

		if ((body != null) && (body.length() == 0)) {
			calendarNotificationTemplateCacheModel.body = null;
		}

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			calendarNotificationTemplateCacheModel.lastPublishDate =
				lastPublishDate.getTime();
		}
		else {
			calendarNotificationTemplateCacheModel.lastPublishDate =
				Long.MIN_VALUE;
		}

		return calendarNotificationTemplateCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CalendarNotificationTemplate, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CalendarNotificationTemplate, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CalendarNotificationTemplate, Object>
				attributeGetterFunction = entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply(
					(CalendarNotificationTemplate)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<CalendarNotificationTemplate, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CalendarNotificationTemplate, Object>>
				entry : attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CalendarNotificationTemplate, Object>
				attributeGetterFunction = entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply(
					(CalendarNotificationTemplate)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function
			<InvocationHandler, CalendarNotificationTemplate>
				_escapedModelProxyProviderFunction =
					_getProxyProviderFunction();

	}

	private long _mvccVersion;
	private String _uuid;
	private long _calendarNotificationTemplateId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _calendarId;
	private String _notificationType;
	private String _notificationTypeSettings;
	private String _notificationTemplateType;
	private String _subject;
	private String _body;
	private Date _lastPublishDate;

	public <T> T getColumnValue(String columnName) {
		columnName = _attributeNames.getOrDefault(columnName, columnName);

		Function<CalendarNotificationTemplate, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CalendarNotificationTemplate)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("uuid_", _uuid);
		_columnOriginalValues.put(
			"calendarNotificationTemplateId", _calendarNotificationTemplateId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("calendarId", _calendarId);
		_columnOriginalValues.put("notificationType", _notificationType);
		_columnOriginalValues.put(
			"notificationTypeSettings", _notificationTypeSettings);
		_columnOriginalValues.put(
			"notificationTemplateType", _notificationTemplateType);
		_columnOriginalValues.put("subject", _subject);
		_columnOriginalValues.put("body", _body);
		_columnOriginalValues.put("lastPublishDate", _lastPublishDate);
	}

	private static final Map<String, String> _attributeNames;

	static {
		Map<String, String> attributeNames = new HashMap<>();

		attributeNames.put("uuid_", "uuid");

		_attributeNames = Collections.unmodifiableMap(attributeNames);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("uuid_", 2L);

		columnBitmasks.put("calendarNotificationTemplateId", 4L);

		columnBitmasks.put("groupId", 8L);

		columnBitmasks.put("companyId", 16L);

		columnBitmasks.put("userId", 32L);

		columnBitmasks.put("userName", 64L);

		columnBitmasks.put("createDate", 128L);

		columnBitmasks.put("modifiedDate", 256L);

		columnBitmasks.put("calendarId", 512L);

		columnBitmasks.put("notificationType", 1024L);

		columnBitmasks.put("notificationTypeSettings", 2048L);

		columnBitmasks.put("notificationTemplateType", 4096L);

		columnBitmasks.put("subject", 8192L);

		columnBitmasks.put("body", 16384L);

		columnBitmasks.put("lastPublishDate", 32768L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CalendarNotificationTemplate _escapedModel;

}