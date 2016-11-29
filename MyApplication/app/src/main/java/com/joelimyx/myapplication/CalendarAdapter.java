package com.joelimyx.myapplication;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.provider.BaseColumns;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Joe on 11/28/16.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    List<Date> mDateList;

    public CalendarAdapter(List<Date> dateList) {
        mDateList = dateList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_date,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mEventText.setText(mDateList.get(position).getTitle());
        holder.mDateText.setText(mDateList.get(position).getDate());
        holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(), mDateList.get(holder.getAdapterPosition()).getTitle() + " is Deleted.", Toast.LENGTH_SHORT).show();

                view.getContext().getContentResolver()
                        .delete(ContentUris.withAppendedId(
                                CalendarContract.Events.CONTENT_URI,mDateList.get(holder.getAdapterPosition()).getId()),
                                null,
                                null);
                return false;
            }
        });
    }

    public void swapdata(Cursor cursor){

        mDateList.clear();

        if(cursor != null && cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events._ID));
                long date = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                mDateList.add(new Date(name,date,id));
                cursor.moveToNext();
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mEventText, mDateText;
        LinearLayout mLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mEventText = (TextView) itemView.findViewById(R.id.title);
            mDateText= (TextView) itemView.findViewById(R.id.date);
            mLayout = (LinearLayout) itemView.findViewById(R.id.layout);

        }
    }
}
