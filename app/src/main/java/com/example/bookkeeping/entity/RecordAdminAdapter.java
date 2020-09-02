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
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.example.bookkeeping.others.Constant.*;

public class RecordAdminAdapter extends ArrayAdapter<RecordAdmin> {

    private final int resourceId;

    public RecordAdminAdapter(@NonNull @NotNull Context context, int resource, @NonNull @NotNull List<RecordAdmin> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RecordAdmin recordAdmin = getItem(position);

        View view;
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

            viewHolder.type = view.findViewById(R.id.RECORD_ADMIN_type);
            viewHolder.username = view.findViewById(R.id.RECORD_ADMIN_username);
            viewHolder.decimal = view.findViewById(R.id.RECORD_ADMIN_decimal);
            viewHolder.time = view.findViewById(R.id.RECORD_ADMIN_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (RecordAdminAdapter.ViewHolder) view.getTag();
        }

        switch (recordAdmin.getOptionType()) {
            case OPTION_ADD_USER:
                viewHolder.type.setText("添加用户");
                break;
            case OPTION_ADD_ADMIN:
                viewHolder.type.setText("添加管理员");
                break;
            case OPTION_DELETE:
                viewHolder.type.setText("删除帐户");
                break;
            case OPTION_CHANGE_MONEY:
                viewHolder.type.setText("管理子账户");
                break;
        }
        viewHolder.username.setText(recordAdmin.getUsername());
        viewHolder.decimal.setText(recordAdmin.getDecimal().toString());
        viewHolder.time.setText(recordAdmin.getTime());
        return view;
    }

    static class ViewHolder {
        TextView type;
        TextView username;
        TextView decimal;
        TextView time;
    }
}
