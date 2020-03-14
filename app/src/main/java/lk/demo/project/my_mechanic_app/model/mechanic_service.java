package lk.demo.project.my_mechanic_app.model;

public class mechanic_service {

    private String Seller_Uid;
    private String Service_Title;
    private String Service_Description;
    private String Service_Cost;
    private String Seller_Store_Name;
    private String Seller_Store_Location;
    private String Seller_Contact;
    private String Image_Uri;

    public mechanic_service() {
    }

    public mechanic_service(String seller_Uid, String service_Title, String service_Description, String service_Cost, String seller_Store_Name, String seller_Store_Location, String seller_Contact, String image_Uri) {
        Seller_Uid = seller_Uid;
        Service_Title = service_Title;
        Service_Description = service_Description;
        Service_Cost = service_Cost;
        Seller_Store_Name = seller_Store_Name;
        Seller_Store_Location = seller_Store_Location;
        Seller_Contact = seller_Contact;
        Image_Uri = image_Uri;
    }

    public String getSeller_Uid() {
        return Seller_Uid;
    }

    public void setSeller_Uid(String seller_Uid) {
        Seller_Uid = seller_Uid;
    }

    public String getService_Title() {
        return Service_Title;
    }

    public void setService_Title(String service_Title) {
        Service_Title = service_Title;
    }

    public String getService_Description() {
        return Service_Description;
    }

    public void setService_Description(String service_Description) {
        Service_Description = service_Description;
    }

    public String getService_Cost() {
        return Service_Cost;
    }

    public void setService_Cost(String service_Cost) {
        Service_Cost = service_Cost;
    }

    public String getSeller_Store_Name() {
        return Seller_Store_Name;
    }

    public void setSeller_Store_Name(String seller_Store_Name) {
        Seller_Store_Name = seller_Store_Name;
    }

    public String getSeller_Store_Location() {
        return Seller_Store_Location;
    }

    public void setSeller_Store_Location(String seller_Store_Location) {
        Seller_Store_Location = seller_Store_Location;
    }

    public String getSeller_Contact() {
        return Seller_Contact;
    }

    public void setSeller_Contact(String seller_Contact) {
        Seller_Contact = seller_Contact;
    }

    public String getImage_Uri() {
        return Image_Uri;
    }

    public void setImage_Uri(String image_Uri) {
        Image_Uri = image_Uri;
    }
}
