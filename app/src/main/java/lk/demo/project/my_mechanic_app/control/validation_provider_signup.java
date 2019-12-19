package lk.demo.project.my_mechanic_app.control;

public class validation_provider_signup {

    public static boolean is_fill(String shop_name, String shop_startdate, String shop_address, String shop_contact , String open_time, String close_time  , String fname,String lname,String nic,String dob,String address,String city,String contact,String email,String password)
    {
        if (fname.isEmpty() || shop_name.isEmpty() || shop_startdate.isEmpty() || shop_address.isEmpty() || shop_contact.isEmpty() || open_time.isEmpty() || close_time.isEmpty()  || lname.isEmpty() || nic.isEmpty() || dob.isEmpty() || address.isEmpty() || city.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty())
            return false;
        else
            return true;
    }
}
