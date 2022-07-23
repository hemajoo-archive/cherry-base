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
package com.hemajoo.commerce.cherry.base.data.model.test.document;

import com.hemajoo.commerce.cherry.base.data.model.base.exception.DataModelEntityException;
import com.hemajoo.commerce.cherry.base.data.model.base.type.EntityStatusType;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentRandomizer;
import com.hemajoo.commerce.cherry.base.data.model.document.DocumentType;
import com.hemajoo.commerce.cherry.base.data.model.test.base.AbstractDataModelEntityUnitTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

/**
 * Abstract implementation of a unit test for a <b>document</b> data model entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractDocumentUnitTest extends AbstractDataModelEntityUnitTest
{
    /**
     * Name.
     */
    protected String documentName;

    /**
     * Description.
     */
    protected String documentDescription;

    /**
     * Reference.
     */
    protected String documentReference;

    /**
     * Tags.
     */
    protected List<String> documentTags;

    /**
     * Filename.
     */
    protected String documentFilename;

    /**
     * Document type.
     */
    protected DocumentType documentType;

    /**
     * Document status type.
     */
    protected EntityStatusType documentStatusType;

    @BeforeEach
    protected void beforeEach() throws DataModelEntityException
    {
        super.beforeEach();

        documentName = DocumentRandomizer.getRandomName();
        documentDescription = DocumentRandomizer.getRandomName();
        documentReference = DocumentRandomizer.getRandomName();
        documentTags = DocumentRandomizer.getRandomTagList();
        documentFilename = DocumentRandomizer.getRandomFilename();
        documentType = DocumentRandomizer.getRandomDocumentType();
        documentStatusType = DocumentRandomizer.getRandomStatusType();
    }
}
