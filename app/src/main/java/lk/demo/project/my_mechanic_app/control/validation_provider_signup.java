package lk.demo.project.my_mechanic_app.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation_provider_signup {

   public static boolean is_fill_all(String shop_name,String shop_startdate,String shop_address,String shop_city,String shop_post,String shop_contact,String shop_open,String shop_close)
   {
       if (shop_name.isEmpty()||shop_startdate.isEmpty()||shop_address.isEmpty()|| shop_city.isEmpty()||shop_post.isEmpty()||shop_contact.isEmpty()||shop_open.isEmpty()||shop_close.isEmpty())
       {
           return false;
       }else {
           return true;
       }
   }
   public static boolean is_valide_contact(String contact)
   {
       if (validation_client_signup.is_contact(contact))
       {
           return true;
       }else {
           return false;
       }
   }

    public static boolean is_Validpostcode(String postcode) {

        String stringToSearch = postcode;

        Pattern p = Pattern.compile("([0-9]{5})");
        Matcher m = p.matcher(stringToSearch);


        if (m.find() && postcode.length()==5)
            return true;
        else
            return false;
    }
}
