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

import com.hemajoo.commerce.cherry.base.data.model.document.*;
import com.hemajoo.commerce.cherry.base.data.model.test.base.AbstractDataModelEntityUnitTest;

/**
 * Abstract implementation of a <b>Cherry</b> unit test.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractDocumentUnitTest extends AbstractDataModelEntityUnitTest
{
    /**
     * Test document name.
     */
    protected final String DOCUMENT_NAME = FAKER.funnyName().name().trim();

    /**
     * Test tag.
     */
    protected final String DOCUMENT_TAG1 = FAKER.animal().name().trim();

    /**
     * Test tag.
     */
    protected final String DOCUMENT_TAG2 = FAKER.artist().name().trim();

    /**
     * Test tag.
     */
    protected final String DOCUMENT_TAG3 = FAKER.book().genre().trim();

    /**
     * Test description.
     */
    protected final String DOCUMENT_DESCRIPTION = FAKER.book().title().trim();

    /**
     * Test reference.
     */
    protected final String DOCUMENT_REFERENCE = FAKER.aviation().METAR().trim();

    /**
     * Test filename.
     */
    protected final String DOCUMENT_CONTENT_PDF = "./media/java-8-streams-cheat-sheet.pdf";

    /**
     * Create a test document.
     * @return Document.
     * @throws DocumentException Thrown in case an error occurred while creating a new document.
     */
    protected IDocument createTestDocument() throws DocumentException
    {
        return Document.builder()
                .withName(DOCUMENT_NAME)
                .withDescription(DOCUMENT_DESCRIPTION)
                .withReference(DOCUMENT_REFERENCE)
                .withTags(new String[] { DOCUMENT_TAG1, DOCUMENT_TAG2, DOCUMENT_TAG3 })
                .withFilename(DOCUMENT_CONTENT_PDF)
                .withDocumentType(DocumentType.DOCUMENT_INVOICE)
                .build();
    }

    /**
     * Return a randomly generated document with a content (file) attached.
     * @return Document.
     * @throws DocumentException Thrown in case an error occurred while creating a document.
     */
    protected IDocument getRandomDocumentWithContent() throws DocumentException
    {
        return DocumentRandomizer.generate(true, true);
    }

    /**
     * Return a randomly generated document without a content (file) attached.
     * @return Document.
     * @throws DocumentException Thrown in case an error occurred while creating a document.
     */
    protected IDocument getRandomDocument() throws DocumentException
    {
        return DocumentRandomizer.generate(true, false);
    }
}
