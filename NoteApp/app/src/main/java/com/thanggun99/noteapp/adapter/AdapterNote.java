package com.thanggun99.noteapp.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thanggun99.noteapp.R;
import com.thanggun99.noteapp.model.entity.Note;
import com.thanggun99.noteapp.presenter.MainPresenter;

import java.util.ArrayList;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.Holder> {
    private MainPresenter mainPresenter;
    private ArrayList<Note> noteList;

    public AdapterNote(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.lnItem.setBackgroundColor(Color.parseColor(noteList.get(position).getColor()));
        holder.date.setText(noteList.get(position).getDate());
        holder.content.setText(noteList.get(position).getContent());
        holder.date.setText(noteList.get(position).getDate());

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (noteList != null) {
            return noteList.size();
        }
        return 0;
    }

    public void setDatas(ArrayList<Note> noteList) {
        this.noteList = noteList;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public LinearLayout lnItem;
        public TextView date;
        public TextView content;
        public ImageView delete;
        public ImageView edit;

        public Holder(View itemView) {
            super(itemView);

            lnItem = (LinearLayout) itemView.findViewById(R.id.ln_item);
            date = (TextView) itemView.findViewById(R.id.txt_date);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            delete = (ImageView) itemView.findViewById(R.id.btn_delete);
            edit = (ImageView) itemView.findViewById(R.id.btn_edit);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.onClickEditAt(getAdapterPosition());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.onClickDeleteAt(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainPresenter.onClickNote(getAdapterPosition());
                }
            });

        }
    }

}
