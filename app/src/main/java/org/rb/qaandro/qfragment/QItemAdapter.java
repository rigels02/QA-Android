package org.rb.qaandro.qfragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.rb.qaandro.R;

import java.util.List;

public class QItemAdapter extends ArrayAdapter<QItem> {

    private final Context context;
    private final int layoutId;
    private final List<QItem> data;

    public QItemAdapter(Context context, int layoutId, List<QItem> data) {
        super(context, layoutId, data);
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        QItemHolder holder = null;

        if(row==null){

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new QItemHolder();
            holder.imgIcon = row.findViewById(R.id.imgIcon);
            holder.qtxt = row.findViewById(R.id.txtQText);

            row.setTag(holder);
        }else {
            holder = (QItemHolder) row.getTag();
        }

        QItem item = data.get(position);
        holder.imgIcon.setImageResource(item.icon);
        holder.qtxt.setText(item.qtext);
        return row;
    }

    static class QItemHolder {
        ImageView imgIcon;
        TextView qtxt;
    }
}
