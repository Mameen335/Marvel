package com.mameen.marvel.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mameen.marvel.R;
import com.mameen.marvel.data.models.Section;
import com.mameen.marvel.listeners.OnItemClickListener;
import com.mameen.marvel.ui.activities.CharacterDetailsActivity;
import com.mameen.marvel.ui.adapters.SectionAdapter;

import java.util.List;

import io.github.sporklibrary.Spork;
import io.github.sporklibrary.android.annotations.BindClick;
import io.github.sporklibrary.android.annotations.BindView;

public class SectionDialog extends Dialog implements SectionAdapter.Paging {

    private Context context;
    private List<Section> sections;

    private SectionAdapter adapter;

    private RecyclerView rv;
    private TextView tvItemNumber;
    private ImageButton btnClose;

    public SectionDialog(@NonNull Context context, List<Section> sections) {
        super(context);

        this.context = context;
        this.sections = sections;



        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_section, null);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(contentView);
        findViewsByIDs(contentView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void findViewsByIDs(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        tvItemNumber = (TextView) view.findViewById(R.id.tvItemNumber);
        btnClose = (ImageButton) view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        initList();
    }

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);

        adapter = new SectionAdapter(context, sections, R.layout.row_section_dialog, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onViewPage(int number) {
        tvItemNumber.setText(number + "/" + sections.size());
    }
}
