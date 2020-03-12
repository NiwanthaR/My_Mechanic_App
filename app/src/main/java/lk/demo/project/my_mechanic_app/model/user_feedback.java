package lk.demo.project.my_mechanic_app.model;

public class user_feedback {

    private String Seller_ID;
    private String User_Name;
    private String User_Description;
    private String User_Satisfaction;
    private String Image_Uri;

    public user_feedback() {
    }

    public user_feedback(String seller_ID, String user_Name, String user_Description, String user_Satisfaction, String image_Uri) {
        Seller_ID = seller_ID;
        User_Name = user_Name;
        User_Description = user_Description;
        User_Satisfaction = user_Satisfaction;
        Image_Uri = image_Uri;
    }

    public String getSeller_ID() {
        return Seller_ID;
    }

    public void setSeller_ID(String seller_ID) {
        Seller_ID = seller_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Description() {
        return User_Description;
    }

    public void setUser_Description(String user_Description) {
        User_Description = user_Description;
    }

    public String getUser_Satisfaction() {
        return User_Satisfaction;
    }

    public void setUser_Satisfaction(String user_Satisfaction) {
        User_Satisfaction = user_Satisfaction;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }
}
