package com.example.bookkeeping.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.bookkeeping.R;

import java.util.List;

public class RecordUserAdapter extends ArrayAdapter<RecordUser> {

    private final int resourceId;

    public RecordUserAdapter(@NonNull Context context, int resource, @NonNull List<RecordUser> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RecordUser recordUser = getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.decimal = view.findViewById(R.id.RECORD_USER_decimal);
            viewHolder.time = view.findViewById(R.id.RECORD_USER_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.decimal.setText(recordUser.getDecimal().toString() + "元");
        viewHolder.time.setText(recordUser.getTime().toString());
        return view;
    }

    static class ViewHolder {
        TextView decimal;
        TextView time;
    }
}
