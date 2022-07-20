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
import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.IIdentity;
import com.hemajoo.commerce.cherry.base.data.model.base.identity.Referable;
import com.hemajoo.commerce.cherry.base.data.model.base.status.IStatusEntity;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityType;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Provide services to manipulate the base data composing a <b>data model entity</b>.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @since Cherry 0.1.0
 * @version 1.0.0
 */
public interface IDataModelEntity extends IStatusEntity, IIdentity, Referable
{
    /**
     * Data model entity attribute: <b>id</b>.
     */
    @JsonIgnore
    String BASE_ENTITY_ID = "id";

    /**
     * Data model entity attribute: <b>entity type</b>.
     */
    @JsonIgnore
    String BASE_ENTITY_TYPE = "entityType";

    /**
     * Data model entity attribute: <b>name</b>.
     */
    @JsonIgnore
    String BASE_NAME = "name";

    /**
     * Data model entity attribute: <b>description</b>.
     */
    @JsonIgnore
    String BASE_DESCRIPTION = "description";

    /**
     * Data model entity attribute: <b>reference</b>.
     */
    @JsonIgnore
    String BASE_REFERENCE = "reference";

    /**
     * Data model entity attribute: <b>parent id</b>.
     */
    @JsonIgnore
    String BASE_PARENT_ID = "parentId";

//    /**
//     * Data model entity attribute: <b>id</b>.
//     */
//    @JsonIgnore
//    public static final String BASE_PARENT = "parent";

    /**
     * Data model entity attribute: <b>parent type</b>.
     */
    @JsonIgnore
    String BASE_PARENT_TYPE = "parentType";

    /**
     * Data model entity attribute: <b>tags</b>.
     */
    @JsonIgnore
    String BASE_TAGS = "tags";

    @Override
    UUID getId();

    @Override
    void setId(final @NonNull UUID id);

    /**
     * Returns the parent entity of this entity (can be null).
     * @param <T> Entity type.
     * @return Parent entity if set, <b>null</b>> otherwise.
     */
    <T extends IDataModelEntity> T getParent();

    /**
     * Sets the parent entity for this entity (can be null).
     * @param parent Parent entity.
     * @param <T> Entity type.
     * @throws DataModelEntityException Thrown to indicate an error occurred when trying to set the parent entity.
     */
    <T extends IDataModelEntity> void setParent(T parent) throws DataModelEntityException;

    /**
     * Returns the parent type.
     * @return DataModelEntity type representing the parent type.
     */
    EntityType getParentType();

    /**
     * Return the number of documents this entity holds.
     * @return Number of documents.
     */
    int getDocumentCount();

    /**
     * Return the complete list of documents this entity holds.
     * @param <T> Entity type.
     * @return List of documents.
     */
    <T extends IDataModelEntity> List<T> getDocuments();

    /**
     * Return the document matching the given name.
     * @param name Document name.
     * @param <T> Entity type.
     * @return Document if found, <b>null</b> otherwise.
     */
    <T extends IDataModelEntity> T getDocumentByName(final @NonNull String name);

    /**
     * Return the document matching the given identifier.
     * @param id Document identifier.
     * @param <T> Entity type.
     * @return Document if found, <b>null</b> otherwise.
     */
    <T extends IDataModelEntity> T getDocumentById(final @NonNull String id);

    /**
     * Return the document matching the given UUID.
     * @param uuid Document UUID.
     * @param <T> Entity type.
     * @return Document if found, <b>null</b> otherwise.
     */
    <T extends IDataModelEntity> T getDocumentById(final @NonNull UUID uuid);

    /**
     * Add a document to this entity.
     * @param document Document to add.
     * @param <T> Entity type.
     * @throws DataModelEntityException Thrown to indicate an error when trying to add a document.
     */
    <T extends IDataModelEntity> void addDocument(T document) throws DataModelEntityException;

    /**
     * Check if the given document exist in the list of documents for this entity?
     * @param document Document to check.
     * @param <T> Entity type.
     * @return <b>True</b>> if the document exist, <b>false</b>> otherwise.
     */
    <T extends IDataModelEntity> boolean existDocument(T document);

    /**
     * Check if a document exist given its identifier.
     * @param id Document identifier.
     * @return <b>True</b>> if the document exist, <b>false</b> otherwise.
     */
    boolean existDocument(final @NonNull UUID id);

    /**
     * Check if a document exist given its identifier.
     * @param id Document identifier.
     * @return <b>True</b>> if the document exist, <b>false</b> otherwise.
     */
    boolean existDocument(final @NonNull String id);

    /**
     * Remove a document from the list of documents for this entity.
     * @param document Document to remove.
     * @param <T> Entity type.
     * @return <b>True</b> if the document has been removed, <b>false</b> otherwise.
     */
    <T extends IDataModelEntity> boolean removeDocument(T document);

    /**
     * Remove a document given its identifier.
     * @param id Document identifier.
     * @return <b>True</b> if the document has been removed, <b>false</b> otherwise.
     */
    boolean removeDocumentById(final @NonNull String id);

    /**
     * Remove a document given its identifier.
     * @param id Document identifier.
     * @return <b>True</b> if the document has been removed, <b>false</b> otherwise.
     */
    boolean removeDocumentById(final @NonNull UUID id);

    /**
     * Returns the entity name.
     * @return DataModelEntity name.
     */
    String getName();

    /**
     * Sets the entity name.
     * @param name DataModelEntity name.
     */
    void setName(String name);

    /**
     * Returns the entity description.
     * @return DataModelEntity description.
     */
    String getDescription();

    /**
     * Sets the entity description.
     * @param description DataModelEntity description.
     */
    void setDescription(String description);

    /**
     * Returns the entity reference.
     * @return DataModelEntity reference.
     */
    String getReference();

    /**
     * Sets the entity reference.
     * @param reference DataModelEntity reference.
     */
    void setReference(String reference);

    /**
     * Returns the document tags.
     * @return Document tags.
     */
    List<String> getTags();

    /**
     * Returns the document tags as a string with values separated by commas.
     * @return Document tags.
     */
    String getTagsAsString();

    /**
     * Sets the document tags.
     * @param tags Document tags.
     */
    void setTags(final String tags);

    /**
     * Adds a tag.
     * @param tag Tag.
     */
    void addTag(String tag);

    /**
     * Removes a tag.
     * @param tag Tag to remove.
     */
    void removeTag(String tag);

    /**
     * Returns a random tag.
     * @return Random tag or <b>null</b> if no tag exist.
     * @throws DataModelEntityException Thrown in case an error occurred while trying to get a random tag.
     */
    String getRandomTag() throws DataModelEntityException;

    /**
     * Checks if the given tag exist.
     * @param tag Tag.
     * @return {@code True} if the tag exist, {@code false} otherwise.
     */
    boolean existTag(String tag);

    /**
     * Returns the number of tags.
     * @return Number of tags.
     */
    int getTagCount();
}
