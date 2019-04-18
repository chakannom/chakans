package com.chakans.drive.web.rest.user.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FileCreateRequestModel {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Size(min = 1, max = 255)
    private String mimeType;

    private Long size = (long)0;

    @NotNull
    private Parent parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public Parent getParent() {
        return parent;
    }

    public class Parent {

        @NotNull
        @Size(min = 1, max = 50)
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Parent{id='" + id + '\'' + "}";
        }
    }

    @Override
    public String toString() {
        return "FileCreateRequestModel{" +
            "name='" + name + '\'' +
            ", mimeType='" + mimeType + '\'' +
            ", size=" + size +
            ", parent=" + parent +
            '}';
    }
}
