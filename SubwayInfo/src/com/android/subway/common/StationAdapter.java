package com.android.subway.common;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.subway.R;

public class StationAdapter extends ArrayAdapter<StationInfo> {
	private ArrayList<StationInfo> items;
	Context ctx;

	public StationAdapter(Context context, int textViewResourceId, ArrayList<StationInfo> items) {
		super(context, textViewResourceId, items);
		ctx = context;

		// TODO Auto-generated constructor stub
		this.setItems(items);
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(ArrayList<StationInfo> items) {
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public ArrayList<StationInfo> getItems() {
		return items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (v == null) {
			v = vi.inflate(R.layout.row, null);
		}

		final StationInfo station = items.get(position);
		if (station != null) {
			TextView tText = (TextView) v.findViewById(R.id.toptext);
			TextView bText = (TextView) v.findViewById(R.id.bottomtext);

			if (tText != null) {
				tText.setText(station.getLine());
			}
			if (bText != null) {
				bText.setText(station.getStationName());
			}
		}

		return v;
	}
}