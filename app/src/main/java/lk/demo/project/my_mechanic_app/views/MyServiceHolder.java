package lk.demo.project.my_mechanic_app.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;

class MyServiceHolder extends RecyclerView.ViewHolder {

    ImageView single_service_img;
    TextView single_service_title,single_service_store_name,single_service_price,single_service_location;

    public MyServiceHolder(@NonNull View itemView) {
        super(itemView);

        single_service_img = itemView.findViewById(R.id.single_service_ui_img);

        single_service_title = itemView.findViewById(R.id.single_service_ui_title);
        single_service_store_name = itemView.findViewById(R.id.single_service_ui_store_name);
        single_service_price = itemView.findViewById(R.id.single_service_ui_price);
        single_service_location = itemView.findViewById(R.id.single_service_ui_location);
    }
}
