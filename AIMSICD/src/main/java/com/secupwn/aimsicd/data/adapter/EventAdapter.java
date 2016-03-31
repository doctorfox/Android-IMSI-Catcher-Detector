package com.secupwn.aimsicd.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.secupwn.aimsicd.R;
import com.secupwn.aimsicd.data.Event;

import java.text.DateFormat;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class EventAdapter extends RealmBaseAdapter<Event> {

    public EventAdapter(Context context, RealmResults<Event> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.eventlog_items, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Event item = getItem(position);
        holder.updateDisplay(item, position);

        return convertView;
    }

    private class ViewHolder {

        private final View mRootView;
        private final TextView mtime;
        private final TextView mLAC;
        private final TextView mCID;
        private final TextView mPSC;
        private final TextView mgpsd_lat;
        private final TextView mgpsd_lon;
        private final TextView mgpsd_accu;
        private final TextView mDF_id;
        private final TextView mDF_desc;

        private final TextView mRecordId;
        private final TextView mExample;

        ViewHolder(View rootView) {
            mRootView = rootView;

            mtime =         (TextView) mRootView.findViewById(R.id.time);
            mLAC =          (TextView) mRootView.findViewById(R.id.LAC);
            mCID =          (TextView) mRootView.findViewById(R.id.CID);
            mPSC =          (TextView) mRootView.findViewById(R.id.PSC);
            mgpsd_lat =     (TextView) mRootView.findViewById(R.id.gpsd_lat);
            mgpsd_lon =     (TextView) mRootView.findViewById(R.id.gpsd_lon);
            mgpsd_accu =    (TextView) mRootView.findViewById(R.id.gpsd_accu);
            mDF_id =        (TextView) mRootView.findViewById(R.id.DF_id);
            mDF_desc =      (TextView) mRootView.findViewById(R.id.DF_desc);

            mRecordId =     (TextView) mRootView.findViewById(R.id.record_id);
            mExample =      (TextView) mRootView.findViewById(R.id.example);

            rootView.setTag(this);
        }

        public void updateDisplay(Event item, int position) {
            mtime.setText(DateFormat.getDateTimeInstance().format(item.getTimestamp()));          // need fix ?
            mLAC.setText(item.getLac());
            mCID.setText(item.getCellId());
            mPSC.setText(item.getPsc());
            mgpsd_lat.setText(String.valueOf(item.getLocationInfo().getLatitude()));
            mgpsd_lon.setText(String.valueOf(item.getLocationInfo().getLongitude()));
            mgpsd_accu.setText(String.valueOf(item.getLocationInfo().getAccuracy()));
            mDF_id.setText(String.valueOf(item.getDfId()));
            mDF_desc.setText(item.getDfDescription());

            mRecordId.setText(String.valueOf(position));
            if (item.isFakeData()) {
                mExample.setText(mRootView.getContext().getString(R.string.example));
                mExample.setVisibility(View.VISIBLE);
            } else {
                mExample.setVisibility(View.GONE);
            }
        }
    }
}
