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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import lombok.*;

import java.util.UUID;

/**
 * Represents the <b>identity</b> of a data model entity.
 * <br>
 * It is used to store a lightweight reference to an entity with enough information for the persistence layer to be able to retrieve the entity from the backend.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Identity implements IIdentity
{
    /**
     * DataModelEntity type.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    private EntityType entityType;

    /**
     * DataModelEntity unique identifier.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    private UUID id;

    /**
     * Create a new identity given an identity.
     * @param identity Identity.
     */
    public Identity(final IIdentity identity)
    {
        if (identity != null)
        {
            this.id = identity.getId();
            this.entityType = identity.getEntityType();
        }
    }

    /**
     * Creates a new entity identity given an entity type and identifier.
     * @param entityType Entity type.
     * @param id Entity identifier.
     */
    public Identity(final EntityType entityType, final UUID id)
    {
        this.entityType = entityType;
        this.id = id;
    }

    /**
     * Creates an entity identifier statically given an entity type and identifier.
     * @param type Entity type.
     * @param id Entity identifier.
     * @return Entity identity.
     */
    public static IIdentity from(final EntityType type, final UUID id)
    {
        return new Identity(type, id);
    }
}
