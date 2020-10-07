package com.example.retrofit.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.ModelClass.SecondModelClass;
import com.squareup.picasso.Picasso;

import java.util.List;

    public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.viewholder> {

        private List<SecondModelClass> modleClassList;
        private firstAdapter.OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(firstAdapter.OnItemClickListener listener) {
            mListener = listener;
        }

        public SecondAdapter(List<SecondModelClass> modleClassList) {
            this.modleClassList = modleClassList;
        }

        @NonNull
        @Override
        public SecondAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recondrv, viewGroup, false);
            return new SecondAdapter.viewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SecondAdapter.viewholder holder, int position) {
            String imgurl = modleClassList.get(position).getImageurl();
            String title = modleClassList.get(position).getTitle();
            String tutor = modleClassList.get(position).getTutor();
            float rating=modleClassList.get(position).getRating();
            holder.setData(imgurl, title, tutor,rating);
        }

        @Override
        public int getItemCount() {
            return modleClassList.size();
        }

        class viewholder extends RecyclerView.ViewHolder {

            private ImageView image;
            private TextView text1;
            TextView text2;
            TextView startrate;
            RatingBar star;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.img);
                text1 = itemView.findViewById(R.id.nametxt);
                text2 = itemView.findViewById(R.id.tutortxt);
                startrate=itemView.findViewById(R.id.stars);
                star=itemView.findViewById(R.id.ratingBar);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                mListener.onItemClick(position);
                            }
                        }
                    }
                });
            }

            public void setData(String imageurl, String title, String tutor, float rating) {
                Picasso.get().load(imageurl).into(image);
                text1.setText(title);
                text2.setText(tutor);
                String rate=Float.toString(rating);
                startrate.setText(rate);
                star.setRating(rating);
            }
        }
    }
