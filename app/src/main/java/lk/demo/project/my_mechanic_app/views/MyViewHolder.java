package lk.demo.project.my_mechanic_app.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lk.demo.project.my_mechanic_app.R;

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView_in_posts;
    TextView textView_in_posts_title,textView_in_posts_ownere;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView_in_posts=itemView.findViewById(R.id.img_single_addui);
        textView_in_posts_title=itemView.findViewById(R.id.tv_single_addui_title);
        textView_in_posts_ownere=itemView.findViewById(R.id.tv_email_addui);
    }
}
