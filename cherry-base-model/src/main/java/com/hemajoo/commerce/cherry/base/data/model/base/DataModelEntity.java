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
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityValidationException;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.IIdentity;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.Identity;
import com.hemajoo.commerce.cherry.base.data.model.base.status.AbstractStatusEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import com.hemajoo.commerce.cherry.base.data.model.base.validation.DataModelEntityValidator;
import com.hemajoo.commerce.cherry.base.data.model.document.Document;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentException;
import com.hemajoo.commerce.cherry.base.data.model.document.IDocument;
import com.hemajoo.commerce.cherry.base.utilities.helper.string.StringHelper;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.javers.core.metamodel.annotation.DiffIgnore;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Represent a <b>data model entity</b>.
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
     * Tag separator character.
     */
    private static final String TAG_SEPARATOR = ",";

    /**
     * Entity identifier.
     */
    @DiffIgnore
    @Getter
    @Setter
    @Id
    @Type(type = "uuid-char") // Allow displaying in the DB the UUID as a string instead of a binary field!
    @GenericGenerator(name = "cherry-uuid-gen", strategy = "com.hemajoo.commerce.cherry.base.utilities.generator.UuidGenerator")
    @GeneratedValue(generator = "cherry-uuid-gen")
    private UUID id;

    /**
     * Entity type.
     */
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ENTITY_TYPE", length = 50)
    private EntityType entityType;

    /**
     * Entity name.
     */
    @Getter
    @Setter
    @NotEmpty
    @NotBlank
    @Column(name = "NAME")
    private String name;

    /**
     * Entity description.
     */
    @Getter
    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Entity internal reference.
     */
    @Getter
    @Setter
    @Column(name = "REFERENCE", length = 100)
    private String reference;

    /**
     * Tags.
     */
    @Setter
    @Column(name = "TAGS")
    private String tags;

    /**
     * Documents.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    private Set<Document> documents = null;

    /**
     * Parent entity.
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties
    @ManyToOne(targetEntity = DataModelEntity.class, fetch = FetchType.EAGER) // TODO We should store the parent's UUID here!
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
     * Create a new entity.
     * @param type Entity type.
     */
    protected DataModelEntity(final EntityType type)
    {
        this.entityType = type;
        setStatusType(EntityStatusType.ACTIVE);
    }

    /**
     * Create a ne data model entity.
     * @param type Entity type.
     * @param name Entity name.
     * @param description Entity description.
     * @param reference Entity reference.
     * @param status Entity status.
     * @param parent Entity parent.
     * @param document Associated document.
     * @param tags Entity tags.
     * @throws DataModelEntityException Thrown to indicate an error occurred while trying to set the parent entity.
     */
    @SuppressWarnings("java:S107")
    public DataModelEntity(final EntityType type, final String name, final String description, final String reference, final EntityStatusType status, final IDataModelEntity parent, final IDocument document, final Set<String> tags) throws DataModelEntityException
    {
        this(type);

        this.name = name;
        this.description = description;
        this.reference = reference;

        setStatusType(status == null ? EntityStatusType.ACTIVE : status);
        setParent(parent);

        addTags(tags);

        if (document != null)
        {
            addDocument(document);
        }
    }

    @Override
    public IIdentity getIdentity()
    {
        return Identity.from(entityType, id);
    }

    @Override
    public <T extends IDataModelEntity> T getParent()
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

        this.parent = parent;
        this.parentType = parent != null ? parent.getEntityType() : null;

        if (parent != null)
        {
            LOGGER.debug(String.format("%s has parent set to: %s", getIdentity(), parent.getIdentity()));
        }
    }

    @Override
    public int getDocumentCount()
    {
        return documents == null ? 0 : documents.size();
    }

    @JsonIgnore
    @Override
    public <T extends IDataModelEntity> Set<T> getDocuments()
    {
        if (entityType == EntityType.MEDIA)
        {
            return new HashSet<>();
        }

        return documents != null ? (Set<T>) Collections.unmodifiableSet(documents) : new HashSet<>();
    }

    @Override
    public <T extends IDataModelEntity> T getDocument(final @NonNull IDocument document)
    {
        Optional<Document> element = documents.stream().filter(e -> e.equals(document)).findFirst();
        return element.isEmpty() ? null : (T) element.get();
    }

    @Override
    public <T extends IDataModelEntity> T getDocumentByName(final @NonNull String name)
    {
        Optional<Document> document = documents.stream().filter(e -> e.getName().equals(name)).findFirst();
        return document.isEmpty() ? null : (T) document.get();
    }

    @Override
    public <T extends IDataModelEntity> T getDocumentById(final @NonNull String uuid)
    {
        Optional<Document> document = documents.stream().filter(e -> e.getId().toString().equals(uuid)).findFirst();
        return document.isEmpty() ? null : (T) document.get();
    }

    @Override
    public <T extends IDataModelEntity> T getDocumentById(final @NonNull UUID uuid)
    {
        Optional<Document> document = documents.stream().filter(e -> e.getId().toString().equals(uuid.toString())).findFirst();
        return document.isEmpty() ? null : (T) document.get();
    }

    @Override
    public <T extends IDataModelEntity> boolean existDocument(final @NonNull T document)
    {
        if (document.getId() != null)
        {
            return existDocumentById(document.getId());
        }
        else
        {
            return existDocumentByName(document.getName());
        }
    }

    @Override
    public boolean existDocumentById(final @NonNull UUID id)
    {
        return documents.stream().anyMatch(doc -> doc.getId().equals(id));
    }

    @Override
    public boolean existDocumentById(final @NonNull String id)
    {
        return documents.stream().anyMatch(doc -> doc.getId().toString().equals(id));
    }

    @Override
    public boolean existDocumentByName(final @NonNull String name)
    {
        return documents.stream().anyMatch(doc -> doc.getName().equals(name));
    }

    @Override
    public <T extends IDataModelEntity> void addDocument(final @NonNull T document) throws DataModelEntityException
    {
        if (entityType == EntityType.DOCUMENT && document.getEntityType() == EntityType.DOCUMENT)
        {
            throw new DataModelEntityException("Cannot add a document to another document!");
        }

        if (documents == null)
        {
            documents = new HashSet<>();
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
    public <T extends IDataModelEntity> boolean deleteDocument(final @NonNull T document)
    {
        if (document.getId() != null)
        {
            return deleteDocumentById(document.getId());
        }
        else
        {
            return documents.removeIf(doc -> doc.getName().equals(document.getName()));
        }
    }

    @Override
    public boolean deleteDocumentById(final @NonNull String id)
    {
        return documents.removeIf(doc -> doc.getId().toString().equals(id));
    }

    @Override
    public boolean deleteDocumentById(final @NonNull UUID id)
    {
        return documents.removeIf(doc -> doc.getId().equals(id));
    }

    @Override
    public boolean deleteDocumentByName(final @NonNull String name)
    {
        return documents.removeIf(doc -> doc.getName().equals(name));
    }

    @Override
    public void deleteAllDocuments() throws DataModelEntityException
    {
        for (IDocument document : documents)
        {
            document.setParent(null); //TODO Later, we must ensure the document is deleted from the content store and the db!
        }

        documents.clear();
    }

    @Override
    public Set<String> getTags()
    {
        return StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR);
    }

    @Override
    public String getTagsAsString()
    {
        return tags;
    }

    @Override
    public void addTag(String tag)
    {
        if (StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR).stream().noneMatch(element -> element.equals(tag)))
        {
            tags = tags == null || tags.isEmpty() ? tag : tags + ", " + tag;
        }
    }

    @Override
    public void addTags(final String... tags)
    {
        if (tags != null)
        {
            for (String tag : tags)
            {
                addTag(tag);
            }
        }
    }

    @Override
    public void addTags(final Set<String> tags)
    {
        if (tags != null)
        {
            for (String tag : tags)
            {
                addTag(tag);
            }
        }
    }

    @Override
    public void deleteTag(String tag)
    {
        Set<String> sourceTags = StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR);
        Set<String> targetTags = new HashSet<>();

        for (String element : sourceTags)
        {
            if (!element.equals(tag))
            {
                targetTags.add(element);
            }
        }

        setTags(StringHelper.convertSetValuesAsString(targetTags, TAG_SEPARATOR));
    }

    @Override
    public void deleteAllTags()
    {
        tags = null;
    }

    @Override
    public String getRandomTag() throws DataModelEntityException
    {
        Set<String> tagList = StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR);

        if (tagList.isEmpty())
        {
            return null;
        }

        try
        {
            int index = SecureRandom.getInstanceStrong().nextInt(tagList.size());
            return List.copyOf(tagList).get(index).trim();
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new DataModelEntityException(e);
        }
    }

    @Override
    public boolean existTag(String tag)
    {
        return StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR).stream().anyMatch(element -> element.equals(tag));
    }

    @Override
    public int getTagCount()
    {
        return StringHelper.convertStringValuesAsSet(tags, TAG_SEPARATOR).size();
    }

    /**
     * Validate the data of the underlying data model entity.
     * @throws DataModelEntityValidationException Thrown in case errors occurred while validating a data model entity.
     */
    protected final void validate() throws DataModelEntityValidationException
    {
        Set<ConstraintViolation<DataModelEntity>> violations = DataModelEntityValidator.VALIDATOR_FACTORY.getValidator().validate(this);
        checkAndThrowException(violations);

        postValidate();
    }

    /**
     * Post validate the data model entity.
     * @throws DataModelEntityValidationException Thrown in case errors occurred while validating a data model entity.
     */
    protected void postValidate() throws DataModelEntityValidationException
    {
        // Should be overridden by subclasses.
    }

    /**
     * Check if some constraints have been violated. If so, then it creates an exception with all the violations and throw it.
     * @param violations Set of constraint violations.
     * @throws DataModelEntityValidationException Thrown if some constraints have been violated.
     */
    private void checkAndThrowException(final Set<ConstraintViolation<DataModelEntity>> violations) throws DataModelEntityValidationException
    {
        int counter = 1;
        StringBuilder messageBuilder = new StringBuilder();

        if (!violations.isEmpty())
        {
            messageBuilder
                    .append(violations.size())
                    .append(" constraint(s) violated found!");
        }

        for (ConstraintViolation<DataModelEntity> violation : violations)
        {
            messageBuilder.append("\n\t")
                    .append("(").append(counter).append(") - ")
                    .append("Attribute: '")
                    .append(violation.getPropertyPath()).append("' ")
                    .append(violation.getMessage());
            counter++;
        }

        if (!messageBuilder.isEmpty())
        {
            throw new DataModelEntityValidationException(messageBuilder.toString());
        }
    }
}
