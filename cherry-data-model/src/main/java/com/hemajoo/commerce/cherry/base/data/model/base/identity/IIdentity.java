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
package com.hemajoo.commerce.cherry.base.data.model.base.identity;

import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Classes implementing this interface gain the ability to expose their <b>identity</b>.
 * <br><br>
 * An entity identity is composed of:<br>
 *  - the entity identifier (UUID)<br>
 *  - the entity type
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IIdentity extends Serializable
{
    /**
     * Returns the entity identifier.
     * @return DataModelEntity identifier.
     */
    UUID getId();

    /**
     * Returns the entity type.
     * @return DataModelEntity type.
     */
    EntityType getEntityType();
}
