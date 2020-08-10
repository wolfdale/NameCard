package com.micro.namecard.dto;

public class OrganizationBuilder {
    private String uuid;
    private String name;

    public OrganizationBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public OrganizationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public Organization build() {
        Organization org = new Organization();
        org.setUuid(uuid);
        org.setName(name);
        return org;
    }
}
