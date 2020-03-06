package lk.demo.project.my_mechanic_app.model;

public class wall_post {

    private String Owner_ID;
    private String Owner_UID;
    private String Post_Title;
    private String Post_Description;
    private String ImageUri;

    public wall_post() {
    }

    public wall_post(String owner_ID, String owner_UID, String post_Title, String post_Description, String imageUri) {
        Owner_ID = owner_ID;
        Owner_UID = owner_UID;
        Post_Title = post_Title;
        Post_Description = post_Description;
        ImageUri = imageUri;
    }

    public String getOwner_ID() {
        return Owner_ID;
    }

    public void setOwner_ID(String owner_ID) {
        Owner_ID = owner_ID;
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

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }
}
