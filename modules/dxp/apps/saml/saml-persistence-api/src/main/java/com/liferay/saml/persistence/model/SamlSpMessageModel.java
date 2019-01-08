/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.util.Date;

/**
 * The base model interface for the SamlSpMessage service. Represents a row in the &quot;SamlSpMessage&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.liferay.saml.persistence.model.impl.SamlSpMessageImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpMessage
 * @see com.liferay.saml.persistence.model.impl.SamlSpMessageImpl
 * @see com.liferay.saml.persistence.model.impl.SamlSpMessageModelImpl
 * @generated
 */
@ProviderType
public interface SamlSpMessageModel extends BaseModel<SamlSpMessage>,
	ShardedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a saml sp message model instance should use the {@link SamlSpMessage} interface instead.
	 */

	/**
	 * Returns the primary key of this saml sp message.
	 *
	 * @return the primary key of this saml sp message
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this saml sp message.
	 *
	 * @param primaryKey the primary key of this saml sp message
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the saml sp message ID of this saml sp message.
	 *
	 * @return the saml sp message ID of this saml sp message
	 */
	public long getSamlSpMessageId();

	/**
	 * Sets the saml sp message ID of this saml sp message.
	 *
	 * @param samlSpMessageId the saml sp message ID of this saml sp message
	 */
	public void setSamlSpMessageId(long samlSpMessageId);

	/**
	 * Returns the company ID of this saml sp message.
	 *
	 * @return the company ID of this saml sp message
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this saml sp message.
	 *
	 * @param companyId the company ID of this saml sp message
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the create date of this saml sp message.
	 *
	 * @return the create date of this saml sp message
	 */
	public Date getCreateDate();

	/**
	 * Sets the create date of this saml sp message.
	 *
	 * @param createDate the create date of this saml sp message
	 */
	public void setCreateDate(Date createDate);

	/**
	 * Returns the saml idp entity ID of this saml sp message.
	 *
	 * @return the saml idp entity ID of this saml sp message
	 */
	@AutoEscape
	public String getSamlIdpEntityId();

	/**
	 * Sets the saml idp entity ID of this saml sp message.
	 *
	 * @param samlIdpEntityId the saml idp entity ID of this saml sp message
	 */
	public void setSamlIdpEntityId(String samlIdpEntityId);

	/**
	 * Returns the saml idp response key of this saml sp message.
	 *
	 * @return the saml idp response key of this saml sp message
	 */
	@AutoEscape
	public String getSamlIdpResponseKey();

	/**
	 * Sets the saml idp response key of this saml sp message.
	 *
	 * @param samlIdpResponseKey the saml idp response key of this saml sp message
	 */
	public void setSamlIdpResponseKey(String samlIdpResponseKey);

	/**
	 * Returns the expiration date of this saml sp message.
	 *
	 * @return the expiration date of this saml sp message
	 */
	public Date getExpirationDate();

	/**
	 * Sets the expiration date of this saml sp message.
	 *
	 * @param expirationDate the expiration date of this saml sp message
	 */
	public void setExpirationDate(Date expirationDate);
}