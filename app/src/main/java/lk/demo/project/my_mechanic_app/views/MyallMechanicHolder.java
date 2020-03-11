package lk.demo.project.my_mechanic_app.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;

class MyallMechanicHolder extends RecyclerView.ViewHolder {

    ImageView mechanic_logo;
    TextView mechanic_store_name,mechanic_store_owner_name,mechanic_store_location,mechanic_store_contact;
    View mechanic_v;

    public MyallMechanicHolder(@NonNull View itemView) {
        super(itemView);

        mechanic_logo = itemView.findViewById(R.id.seller_ui_image);
        mechanic_store_name = itemView.findViewById(R.id.seller_ui_stor_name);
        mechanic_store_owner_name = itemView.findViewById(R.id.seller_ui_owner_name);
        mechanic_store_location = itemView.findViewById(R.id.seller_ui_city_location);
        mechanic_store_contact = itemView.findViewById(R.id.seller_ui_contact);
        mechanic_v = itemView;

    }
}
