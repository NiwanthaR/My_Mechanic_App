package lk.demo.project.my_mechanic_app.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_in_posts;
    TextView textView_in_posts_title,textView_in_posts_storename,textView_in_posts_price,textView_in_posts_condition;
    View v;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView_in_posts=itemView.findViewById(R.id.single_ui_image);
        textView_in_posts_title=itemView.findViewById(R.id.single_ui_title);
        textView_in_posts_storename=itemView.findViewById(R.id.single_ui_storename);
        textView_in_posts_price=itemView.findViewById(R.id.single_ui_price);
        textView_in_posts_condition=itemView.findViewById(R.id.single_ui_condition);

        v=itemView;
    }
}
