package com.db_connector.template.interaction;

public enum AlterTypes {
    ADD("add"),
    RENAME_COLUMN("rename column"),
    ADD_CONSTRAINT("add constraint"),
    ALTER_COLUMN("alter column"),
    RENAME_TO("rename to"),
    DROP_COLUMN("drop column");

    private final String type;

    private AlterTypes(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
