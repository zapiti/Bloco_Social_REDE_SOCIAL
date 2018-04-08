package com.dev.nathan.socialblock;


import java.util.Date;

public class BlogPost extends BlogPostId {

    public String user_id, image_url, desc, image_thumb;
    public Date dhUpload;

    public BlogPost() {}

    public BlogPost(String user_id, String image_url, String desc, String image_thumb, Date dhUpload) {
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.image_thumb = image_thumb;
        this.dhUpload= dhUpload;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public Date getDhUpload() {
        return dhUpload;
    }

    public void setDhUpload(Date dhUpload) {
        this.dhUpload = dhUpload;
    }


}
