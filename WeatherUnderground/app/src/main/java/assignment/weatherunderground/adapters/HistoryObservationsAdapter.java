package assignment.weatherunderground.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import assignment.weatherunderground.R;
import assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels.HistoryObservationElement;

/**
 HistoryObservationsAdapter
 <p>
 Created by hmehta on 10/29/17.
 */

public class HistoryObservationsAdapter extends BaseAdapter {

    private ArrayList<HistoryObservationElement> mObservationsList;
    private LayoutInflater layoutInflater;

    public HistoryObservationsAdapter(Context context, ArrayList<HistoryObservationElement> mObservationsList) {
        this.mObservationsList = mObservationsList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (mObservationsList != null) {
            return mObservationsList.size();
        }
        return 0;
    }

    @Override
    public HistoryObservationElement getItem(int position) {
        if (mObservationsList != null) {
            return mObservationsList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.observation_cell, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        HistoryObservationElement observation = mObservationsList.get(i);

        viewHolder.tvTempi.setText("Tempi : " + observation.getTempi());
        viewHolder.tvDate.setText("Date : " + observation.getDate().getPretty());
        viewHolder.tvTempm.setText("Tempm : " + observation.getTempm());
        viewHolder.tvSnow.setText("Snow : " + observation.getSnow());
        viewHolder.tvFog.setText("Fog : " + observation.getFog());
        viewHolder.tvRain.setText("Rain : " + observation.getRain());

        return view;
    }

    private class ViewHolder {
        TextView tvDate;
        TextView tvFog;
        TextView tvRain;
        TextView tvSnow;
        TextView tvTempm;
        TextView tvTempi;

        ViewHolder(View view) {
            this.tvDate = (TextView) view.findViewById(R.id.tvDateObservationCell);
            this.tvFog = (TextView) view.findViewById(R.id.tvFogObservationCell);
            this.tvRain = (TextView) view.findViewById(R.id.tvRainObservationCell);
            this.tvSnow = (TextView) view.findViewById(R.id.tvSnowObservationCell);
            this.tvTempm = (TextView) view.findViewById(R.id.tvTempmObservationCell);
            this.tvTempi = (TextView) view.findViewById(R.id.tvTempiObservationCell);
        }
    }
}
