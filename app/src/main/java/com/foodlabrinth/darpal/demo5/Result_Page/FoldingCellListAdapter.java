package com.foodlabrinth.darpal.demo5.Result_Page;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.foodlabrinth.darpal.demo5.R;
import com.ramotion.foldingcell.FoldingCell;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    Context context;
    String rate;

    public FoldingCellListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
        this.context = context;

    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // get item for selected view
        Item item = getItem(position);
        SharedPreferences preferences=context.getSharedPreferences("rat",Context.MODE_PRIVATE);
        rate=preferences.getString("key",null);
        // if cell exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            viewHolder.cuisine = (TextView) cell.findViewById(R.id.cuisineDisp);
            viewHolder.ambience = (TextView) cell.findViewById(R.id.ambience_title);
            //viewHolder.date = (TextView) cell.findViewById(R.id.title_date_label);
            viewHolder.resname = (TextView) cell.findViewById(R.id.resDispName);
            viewHolder.toAddress = (TextView) cell.findViewById(R.id.title_to_address);
            //viewHolder.requestsCount = (TextView) cell.findViewById(R.id.title_requests_count);
            viewHolder.avgcost = (TextView) cell.findViewById(R.id.title_pledge);
            viewHolder.avgrate = (TextView) cell.findViewById(R.id.avgrate);


            viewHolder.RESNAME = (TextView) cell.findViewById(R.id.restaurant_name);
            viewHolder.RESADD = (TextView) cell.findViewById(R.id.restaurant_address);
            viewHolder.contentRequestBtn = (TextView) cell.findViewById(R.id.content_request_btn);
            viewHolder.contact = (TextView) cell.findViewById(R.id.contact_display_one);
            viewHolder.timeDisp = (TextView) cell.findViewById(R.id.content_delivery_time);
            viewHolder.email = (TextView) cell.findViewById(R.id.email_display);
            viewHolder.RESCOST = (TextView) cell.findViewById(R.id.cost_value);
            viewHolder.RESCUISINE = (TextView) cell.findViewById(R.id.cuisine_name);
            viewHolder.AVGRATERES = (TextView) cell.findViewById(R.id.avgrate);

            cell.setTag(viewHolder);


        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        String s1, s2, s3;
        s1 = item.getCuisine();
        s1 = s1.replace(",", "\n");

        s2 = item.getAmbience();
        s2 = s2.replace(",", "\n");

        viewHolder.cuisine.setText(s1);
        viewHolder.ambience.setText(s2);
        viewHolder.RESCUISINE.setText(s1);


        viewHolder.AVGRATERES.setText(item.getAvgrate());
        viewHolder.RESCOST.setText(item.getPledgePrice());
        viewHolder.RESNAME.setText(item.getFromAddress());
        viewHolder.RESADD.setText(item.getToAddress());
        //viewHolder.date.setText(item.getDate());
        viewHolder.resname.setText(item.getFromAddress());
        viewHolder.toAddress.setText(item.getToAddress());
        viewHolder.avgcost.setText(item.getPledgePrice());
        viewHolder.timeDisp.setText(item.getDate());
        viewHolder.contact.setText(item.getContact());
        viewHolder.email.setText(item.getEmail());
        viewHolder.avgrate.setText(rate+"");

        ShineButton shineButton = (ShineButton) cell.findViewById(R.id.po_image0);
        shineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FoldingCell) v).toggle(false);
                // register in adapter that state for selected cell is toggled
                registerToggle(position);

            }
        });


        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }
        return cell;
    }


    Boolean check(Boolean b) {
        Toast.makeText(context, "checked --" + b, Toast.LENGTH_SHORT).show();
        return b;
    }


    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView cuisine;
        TextView contentRequestBtn;
        TextView avgcost;
        TextView avgrate;
        TextView resname;
        TextView toAddress;
        //TextView date;
        TextView ambience;
        TextView contact;
        TextView timeDisp;
        TextView email;

        TextView RESNAME;
        TextView RESADD;
        TextView RESCOST;
        TextView RESCUISINE;
        TextView AVGRATERES;
    }
}
