package lk.demo.project.my_mechanic_app.model;

public class wall_post {

    private String Owner_UID;
    private String Post_Title;
    private String Post_Description;
    private String Post_Cost;
    private String ImageUri;
    private String Post_Type;
    private String Store_Name;
    private String Store_Contact;

    public wall_post() {
    }

    public wall_post(String owner_UID, String post_Title, String post_Description, String post_Cost, String imageUri, String post_Type, String store_Name, String store_Contact) {
        Owner_UID = owner_UID;
        Post_Title = post_Title;
        Post_Description = post_Description;
        Post_Cost = post_Cost;
        ImageUri = imageUri;
        Post_Type = post_Type;
        Store_Name = store_Name;
        Store_Contact = store_Contact;
    }

    public String getOwner_UID() {
        return Owner_UID;
    }

    public void setOwner_UID(String owner_UID) {
        Owner_UID = owner_UID;
    }

    public String getPost_Title() {
        return Post_Title;
    }

    public void setPost_Title(String post_Title) {
        Post_Title = post_Title;
    }

    public String getPost_Description() {
        return Post_Description;
    }

    public void setPost_Description(String post_Description) {
        Post_Description = post_Description;
    }

    public String getPost_Cost() {
        return Post_Cost;
    }

    public void setPost_Cost(String post_Cost) {
        Post_Cost = post_Cost;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getPost_Type() {
        return Post_Type;
    }

    public void setPost_Type(String post_Type) {
        Post_Type = post_Type;
    }

    public String getStore_Name() {
        return Store_Name;
    }

    public void setStore_Name(String store_Name) {
        Store_Name = store_Name;
    }

    public String getStore_Contact() {
        return Store_Contact;
    }

    public void setStore_Contact(String store_Contact) {
        Store_Contact = store_Contact;
    }
}
