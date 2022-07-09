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
 * Entities implementing this interface gain the ability to expose their status.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IStatusEntity extends IAuditEntity
{
    /**
     * Data model entity attribute: <b>status type</b>.
     */
    @JsonIgnore
    public static final String BASE_STATUS_TYPE = "statusType";

    /**
     * Data model entity attribute: <b>since</b> (inactive date).
     */
    @JsonIgnore
    public static final String BASE_SINCE = "since";

    /**
     * Returns the status type.
     * @return Status type.
     */
    EntityStatusType getStatusType();

    /**
     * Sets the status type date.
     * @param status Status type.
     */
    void setStatusType(final EntityStatusType status);

    /**
     * Returns the inactivation date.
     * <br>
     * When status is <b>INACTIVE</b> status, invoking this service will return the date this entity has been de-activated.
     * @return Inactivation since date.
     */
    Date getSince(); //TODO Should be changed to a ZonedDateTime

    /**
     * Sets the inactivation date.
     * <br>
     * When status is set to <b>INACTIVE</b> status, invoking this service will set the date this entity has been de-activated.
     * @param date Inactivation date.
     */
    void setSince(final Date date);
}
