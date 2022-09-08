/*
 * (C) Copyright Hemajoo Systems Inc.  2022 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.hemajoo.commerce.cherry.base.data.model.base.status;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hemajoo.commerce.cherry.base.data.model.base.audit.IAuditEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;

import java.util.Date;

/**
 * Provide services to manipulate the status data composing an <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IStatusEntity extends IAuditEntity
{
    /**
     * Data model entity attribute: <b>status type</b>.
     */
    @JsonIgnore
    String BASE_STATUS_TYPE = "statusType";

    /**
     * Data model entity attribute: <b>since</b> (inactive date).
     */
    @JsonIgnore
    String BASE_SINCE = "since";

    /**
     * Returns the status type.
     * @return Status type.
     */
    EntityStatusType getStatusType();

    /**
     * Set the entity status type.
     * @param status Status type.
     */
    void setStatusType(final EntityStatusType status);

    /**
     * Return if this entity is active?
     * @return <b>True</b> if the entity is active, <b>false</b> otherwise.
     */
    boolean isActive();

    /**
     * Returns the inactive date.
     * <br>
     * When status is set to {@link EntityStatusType#INACTIVE}, invoking this service will return the date this entity has been set to inactive.
     * @return Inactive date.
     */
    Date getInactiveSince(); //TODO Should be changed to a ZonedDateTime

    /**
     * Sets the inactive date.
     * <br>
     * When status is set to {@link EntityStatusType#INACTIVE}, invoking this service will set the date this entity has been set to inactive.
     * @param date Inactive date.
     */
    void setInactiveSince(final Date date);
}
