package com.example.mychatapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImagePost;
    ImageView postImage, likeImage, commentsImage;
    TextView postUsername, timeAgo, postDesc, likeCounter, commentsCounter;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImagePost = itemView.findViewById(R.id.profileImagePost);
        postImage = itemView.findViewById(R.id.postImage);
        postUsername = itemView.findViewById(R.id.postUsername);
        timeAgo = itemView.findViewById(R.id.timeAgo);
        postDesc = itemView.findViewById(R.id.postDesc);
        likeImage = itemView.findViewById(R.id.likeImage);
        commentsImage = itemView.findViewById(R.id.commentsImage);
        likeCounter = itemView.findViewById(R.id.likeCounter);
        commentsCounter = itemView.findViewById(R.id.commentsCounter);
    }
}
