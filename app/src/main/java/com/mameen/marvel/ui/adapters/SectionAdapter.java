package com.mameen.marvel.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mameen.marvel.R;
import com.mameen.marvel.data.models.Character;
import com.mameen.marvel.data.models.Section;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SectionAdapter extends ParentRecyclerAdapter<Section> {

    static final String TAG = SectionAdapter.class.getSimpleName();

    private Paging paging;

    public SectionAdapter(Context context, List<Section> data, int layoutId) {
        super(context, data, layoutId);
    }

    public SectionAdapter(Context context, List<Section> data, int layoutId, Paging paging) {
        super(context, data, layoutId);

        this.paging = paging;
    }

    @Override
    public ParentRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        holder.setOnItemClickListener(itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ParentRecyclerViewHolder holder, int position) {
        final ViewHolder viewholder = (ViewHolder) holder;
        if (paging != null) {
            paging.onViewPage(position + 1);
        }

        Section section = data.get(position);

        viewholder.tvTitle.setText(section.getTitle());

        try {
            Picasso.get()
                    .load(section.getThumbnail().getFullPathPortrait())
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .into(viewholder.img, new Callback() {
                        @Override
                        public void onSuccess() {
                            viewholder.progress.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            viewholder.progress.setVisibility(View.GONE);
                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private class ViewHolder extends ParentRecyclerViewHolder {

        TextView tvTitle;
        ImageView img;
        ProgressBar progress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            img = itemView.findViewById(R.id.img);
            progress = itemView.findViewById(R.id.progress);

            setClickableRootView(itemView);
        }
    }

    public interface Paging {
        void onViewPage(int number);
    }
}