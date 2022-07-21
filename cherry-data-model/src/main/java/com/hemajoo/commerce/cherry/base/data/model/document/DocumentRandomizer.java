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
package com.hemajoo.commerce.cherry.base.data.model.document;

import com.hemajoo.commerce.cherry.base.commons.test.TestMediaType;
import com.hemajoo.commerce.cherry.base.data.model.base.random.AbstractDataModelEntityRandomizer;
import lombok.experimental.UtilityClass;
import org.ressec.avocado.core.random.EnumRandomGenerator;

import java.util.UUID;

/**
 * Utility class providing services to randomly generate <b>document</b> data model entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@UtilityClass
public class DocumentRandomizer extends AbstractDataModelEntityRandomizer
{
    /**
     * Document type enumeration generator.
     */
    private static final EnumRandomGenerator DOCUMENT_TYPE_GENERATOR = new EnumRandomGenerator(DocumentType.class);

    /**
     * Test media type enumeration generator.
     */
    private static final EnumRandomGenerator TEST_MEDIA_TYPE_GENERATOR = new EnumRandomGenerator(TestMediaType.class);

    /**
     * Generate a random document data model entity.
     * <br>
     * A content (file) will be automatically attached to the document.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * @return Random document.
     * @throws DocumentException Thrown to indicate an error occurred while generating a document.
     */
    public static IDocument generate(final boolean withRandomId) throws DocumentException
    {
        return generate(withRandomId, true);
    }

    /**
     * Generates a new random document data model entity.
     * @param withRandomId Does a random entity identifier has to be generated? <b>False</b> by default.
     * @param withContent Does a content (file) has to be attached to the document?
     * @return Random document.
     * @throws DocumentException Thrown to indicate an error occurred while generating a document.
     */
    public static IDocument generate(final boolean withRandomId, final boolean withContent) throws DocumentException
    {
        IDocument document = new Document();
        AbstractDataModelEntityRandomizer.populateBaseFields(document);

        if (withRandomId)
        {
            document.setId(UUID.randomUUID());
        }

        document.setName(FAKER.name().title());

        if (withContent)
        {
            document.setContent(((TestMediaType) TEST_MEDIA_TYPE_GENERATOR.gen()).getPath());
        }

        document.setTags(FAKER.elderScrolls().creature());
        document.setDocumentType((DocumentType) DOCUMENT_TYPE_GENERATOR.gen());

        return document;
    }
}
