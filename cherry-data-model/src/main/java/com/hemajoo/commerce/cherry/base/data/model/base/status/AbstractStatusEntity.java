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

import com.hemajoo.commerce.cherry.base.data.model.base.IDataModelEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.audit.AbstractAuditEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents an abstract <b>status aware</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AbstractStatusEntity extends AbstractAuditEntity implements IStatusEntity, IDataModelEntity
{
    /**
     * DataModelEntity status.
     */
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_TYPE", length = 50)
    private EntityStatusType statusType;

    /**
     * Inactivity time stamp information (server time) that must be filled when the entity becomes inactive.
     */
    @Getter
    @Setter
    @Schema(hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SINCE", length = 26)
    private Date since;

    /**
     * Returns if this entity is active?
     * @return <b>True</b> if the entity is active, <b>false</b> otherwise.
     */
    public final boolean isActive()
    {
        return statusType == EntityStatusType.ACTIVE;
    }

    /**
     * Sets the underlying entity to {@link EntityStatusType#ACTIVE}.
     */
    public final void setActive()
    {
        statusType = EntityStatusType.ACTIVE;
        since = null;
    }

    /**
     * Sets the underlying entity to {@link EntityStatusType#INACTIVE}.
     */
    public final void setInactive()
    {
        statusType = EntityStatusType.INACTIVE;
        since = new Date(System.currentTimeMillis());
    }

    /**
     * Sets the status of the entity.
     * @param status Status.
     */
    public void setStatusType(EntityStatusType status)
    {
        if (status != this.statusType)
        {
            if (status == EntityStatusType.INACTIVE)
            {
                setInactive();
            }
            else
            {
                setActive();
            }
        }
    }
}
