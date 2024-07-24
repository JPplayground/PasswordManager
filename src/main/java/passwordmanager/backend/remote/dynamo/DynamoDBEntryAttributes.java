package passwordmanager.backend.remote.dynamo;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import passwordmanager.backend.EntryFields;

/**
 * These might be useful, not sure yet though.
 */
public class DynamoDBEntryAttributes {

    public static final AttributeDefinition titleAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.TITLE.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition emailAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.EMAIL.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition secondaryEmailAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.SECONDARY_EMAIL.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition passwordAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.PASSWORD.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition usernameAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.USERNAME.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition phoneNumberAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.PHONE_NUMBER.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition linkAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.LINK.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition categoryAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.CATEGORY.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition dateCreatedAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.DATE_CREATED.toString())
            .withAttributeType(ScalarAttributeType.S);

    public static final AttributeDefinition dateModifiedAttr = new AttributeDefinition()
            .withAttributeName(EntryFields.DATE_MODIFIED.toString())
            .withAttributeType(ScalarAttributeType.S);
}
