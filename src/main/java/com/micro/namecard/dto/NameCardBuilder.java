package com.micro.namecard.dto;

public class NameCardBuilder {
    private String uuid;

    private String name;

    private String age;

    public NameCardBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public NameCardBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public NameCardBuilder withAge(String age) {
        this.age = age;
        return this;
    }

    public NameCard build() {
        NameCard nameCard = new NameCard();
        nameCard.setUuid(uuid);
        nameCard.setName(name);
        nameCard.setAge(age);
        return nameCard;
    }

}
