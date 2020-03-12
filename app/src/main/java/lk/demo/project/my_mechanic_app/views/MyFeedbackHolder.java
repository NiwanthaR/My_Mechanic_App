package lk.demo.project.my_mechanic_app.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;

class MyFeedbackHolder extends RecyclerView.ViewHolder {

    TextView txt_feedback_username,txt_feedback_satify_type,txt_feedback_description;
    ImageView img_feedback_image;

    public MyFeedbackHolder(@NonNull View itemView) {
        super(itemView);

        txt_feedback_username = itemView.findViewById(R.id.tv_feedback_single_ui_customer_name);
        txt_feedback_satify_type = itemView.findViewById(R.id.tv_feedback_single_ui_satisfaction);
        txt_feedback_description = itemView.findViewById(R.id.tv_feedback_single_ui_description);

        img_feedback_image = itemView.findViewById(R.id.img_feedback_single_ui_img);
    }
}
