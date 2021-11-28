

package com.kennie.example.http.model;

/**
 * <p>描述：选择</p>
 */
public class SectionItem {
    String description;//
    int  id;//
    String name; //"深夜惊奇",
    String thumbnail;// "http://pic3.zhimg.com/91125c9aebcab1c84f58ce4f8779551e.jpg"

    public String getDescription() {
        return description;
    }

    public SectionItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getId() {
        return id;
    }

    public SectionItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SectionItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public SectionItem setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    @Override
    public String toString() {
        return "SectionItem{" +
                "description='" + description + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
