package lk.demo.project.my_mechanic_app.control;

import android.net.Uri;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation_client_signup {

    public static boolean is_fill(String email,String password)
    {
        if (email.isEmpty() || password.isEmpty())
            return false;
        else
            return true;
    }

    public static boolean is_password_same(String password,String repassword)
    {
        if (password.equals(repassword))
            return true;
        else
            return false;
    }

    public static boolean is_fill(String fname,String lname,String nic,String dob,String address,String city,String contact,String email,String password)
    {
        if (fname.isEmpty() || lname.isEmpty() || nic.isEmpty() || dob.isEmpty() || address.isEmpty() || city.isEmpty() || contact.isEmpty() || email.isEmpty() || password.isEmpty())
            return false;
        else
            return true;
    }

    public static boolean is_ValidNic(String nic) {

        String stringToSearch = nic;

        Pattern p = Pattern.compile("([0-9]{9}[a-z]{1})");
        Matcher m = p.matcher(stringToSearch);


        if (m.find() && nic.length()==10)
            return true;
        else
            return false;
    }

    public static boolean is_ValidPassword(String password) {

        String stringToSearch = password;

        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])");
        Matcher m = p.matcher(stringToSearch);


        if (m.find() && password.length()>7)
            return true;
        else
            return false;

    }

    public static boolean is_Validmail(final String email)
    {
        String StringTosearch = email;

        Pattern p = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(StringTosearch);


        if (m.find())
            return true;
        else
            return false;
    }

    public static boolean is_contact(final String contact)
    {
        String StringTosearch = contact;

        Pattern p = Pattern.compile("(^1300\\d{6}$)|(^0[1|3|7|6|8]{1}[0-9]{8}$)|(^13\\d{4}$)|(^04\\d{2,3}\\d{6}$)");
        Matcher m = p.matcher(StringTosearch);


        if (m.find())
            return true;
        else
            return false;
    }

    public static boolean is_fill_update(String fname,String lname,String address,String city,String contact)
    {
        if (fname.isEmpty() || lname.isEmpty() || address.isEmpty() || city.isEmpty() || contact.isEmpty())
            return false;
        else
            return true;
    }

    public static boolean is_image_ok(Uri image_location)
    {
        if (image_location != null)
        {
            return true;
        }
        return false;
    }
}
