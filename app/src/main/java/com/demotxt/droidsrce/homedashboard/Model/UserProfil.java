package com.demotxt.droidsrce.homedashboard.Model;

import java.io.File;

/**
 * Created on 10/16/17.
 */

public class UserProfil {

    String imageName;

    File image;

    String message;


    public String getImageName() {
        return imageName;
    }

    public UserProfil setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public File getImage() {
        return image;
    }

    public UserProfil setImage(File image) {
        this.image = image;
        return this;
    }
}
