package com.example.bmrcalculator;


public class PersonalInfo {
    private String id, name;
    private String img;


    public PersonalInfo(String id,String name, String img)
    {

        this.setId(id);
        this.setName(name);
        this.setImg(img);

    }

    public String getImg() {
        return img;
    }

    private void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
