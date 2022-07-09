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
package com.hemajoo.commerce.cherry.base.data.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.IIdentity;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.Identity;
import com.hemajoo.commerce.cherry.base.data.model.base.status.AbstractStatusEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.document.Document;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Represents a data model <b>entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
//@Table(name = "ENTITY")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DataModelEntity extends AbstractStatusEntity implements IDataModelEntity
{
    /**
     * DataModelEntity identifier.
     */
    @DiffIgnore
    @Getter
    @Setter
    @Id
    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
    @GenericGenerator(name = "cherry-uuid-gen", strategy = "com.hemajoo.commerce.cherry.server.commons.utility.UuidGenerator")
    @GeneratedValue(generator = "cherry-uuid-gen")
    private UUID id;

    /**
     * DataModelEntity type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ENTITY_TYPE", length = 50)
    private EntityType entityType;

    /**
     * DataModelEntity name.
     */
    @Getter
    @Setter
    @Column(name = "NAME")
    private String name;

    /**
     * DataModelEntity description.
     */
    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * DataModelEntity internal reference.
     */
    @Getter
    @Setter
    @Column(name = "REFERENCE", length = 100)
    private String reference;

    /**
     * Tags.
     */
    @Getter
    @Setter
    @Column(name = "TAGS")
    private String tags;

    /**
     * Documents.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private List<Document> documents = null;

    /**
     * The parent entity.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties
    @ManyToOne(targetEntity = DataModelEntity.class, fetch = FetchType.EAGER)
    private IDataModelEntity parent;

    /**
     * Parent type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "PARENT_TYPE", length = 50)
    private EntityType parentType;

    /**
     * Creates a new base entity.
     * @param type DataModelEntity type.
     */
    protected DataModelEntity(final EntityType type)
    {
        this.entityType = type;
    }

    @Override
    public final IIdentity getIdentity()
    {
        return Identity.from(entityType, id);
    }

    @Override
    public final <T extends IDataModelEntity> T getParent()
    {
        return (T) parent;
    }

    @Override
    public <T extends IDataModelEntity> void setParent(final T parent) throws DataModelEntityException
    {
        if (parent == this)
        {
            throw new DataModelEntityException("Cannot set itself as parent!");
        }

        this.parent = (DataModelEntity) parent;
        this.parentType = parent != null ? parent.getEntityType() : null;

        if (parent != null)
        {
            LOGGER.debug(String.format("%s has parent set to: %s", getIdentity(), parent.getIdentity()));
        }
    }

    @Override
    public final int getDocumentCount()
    {
        return documents.size();
    }

    @JsonIgnore
    @Override
    public <T extends IDataModelEntity> List<T> getDocuments()
    {
        if (entityType == EntityType.MEDIA)
        {
            return new ArrayList<>();
        }

        return documents != null ? (List<T>) Collections.unmodifiableList(documents) : null;
    }

    @Override
    public final <T extends IDataModelEntity> boolean existDocument(final @NonNull T document)
    {
        return existDocument(document.getId());
    }

    @Override
    public final boolean existDocument(final UUID documentId)
    {
        if (documentId != null)
        {
            return documents.stream().anyMatch(doc -> doc.getId().equals(documentId));
        }

        return false; // Random documents do not have a UUID assigned yet by the database!
    }

    @Override
    public final <T extends IDataModelEntity> void addDocument(final @NonNull T document) throws DataModelEntityException
    {
        if (entityType == EntityType.DOCUMENT && document.getEntityType() == EntityType.DOCUMENT)
        {
            throw new DataModelEntityException("Cannot add a document to another document!");
        }

        if (documents == null)
        {
            documents = new ArrayList<>();
        }

        if (!existDocument(document))
        {
            documents.add((Document) document);
            try
            {
                document.setParent(this);
            }
            catch (DataModelEntityException e)
            {
                throw new DocumentException(e);
            }
        }
    }

    @Override
    public final <T extends IDataModelEntity> void removeDocument(final @NonNull T document)
    {
        removeDocument(document.getId());
    }

    @Override
    public final void removeDocument(final @NonNull UUID documentId)
    {
        documents.removeIf(doc -> doc.getId().equals(documentId));
    }

    @Override
    public final void addTag(String tag)
    {
        if (convertTagAsList().stream().noneMatch(element -> element.equals(tag)))
        {
            tags = tags.isEmpty() ? tag : tags + ", " + tag;
        }
    }

    @Override
    public final void removeTag(String tag)
    {
        List<String> sourceTags = convertTagAsList();
        List<String> targetTags = new ArrayList<>();

        for (String element : sourceTags)
        {
            if (!element.equals(tag))
            {
                targetTags.add(element);
            }
        }

        setTags(convertTagAsString(targetTags));
    }

    @Override
    public final String getRandomTag() throws DataModelEntityException
    {
        List<String> tagList = convertTagAsList();

        if (tagList.isEmpty())
        {
            return null;
        }

        try
        {
            int index = SecureRandom.getInstanceStrong().nextInt(tagList.size());
            return tagList.get(index).trim();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new DataModelEntityException(e);
        }
    }

    @Override
    public final boolean existTag(String tag)
    {
        return convertTagAsList().stream().anyMatch(element -> element.equals(tag));
    }

    @Override
    public final int getTagCount()
    {
        return convertTagAsList().size();
    }

    /**
     * Converts a string of tags (separated by comma) to a list of tags.
     * @return List of tags.
     */
    private List<String> convertTagAsList()
    {
        List<String> values;

        if (tags.isEmpty())
        {
            return new ArrayList<>();
        }

        values = Arrays.asList(tags.split(",", -1));
        for (String tag : values)
        {
            tag = tag.trim();
        }

        return values;
    }

    /**
     * Converts a list of tags to a string of tags (separated by comma).
     * @return String of tags.
     */
    private String convertTagAsString(final List<String> tagList)
    {
        StringBuilder builder = new StringBuilder();

        for (String tag : tagList)
        {
            if (builder.length() == 0)
            {
                builder.append(tag);
            }
            else
            {
                builder.append(", ").append(tag);
            }
        }

        return builder.toString();
    }
}
