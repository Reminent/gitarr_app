package com.example.magnus.menufragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.magnus.menufragment.DB_Upload.DB_Delete;
import com.example.magnus.menufragment.XML_Parsing.Transaction;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created by Albin and Martin on 2016-03-09.
 */

/**
 * Class EkonomiAdapter, it is like a bridge between the data and the adapterview, and that's the view that is being reused
 * multiple times
 */
public class EkonomiAdapter extends ArrayAdapter<Transaction>  {

    private int total = 0;
    private int temp=0;
    Context context;
    int layoutResourceId;
    private List<Transaction> data;
    private List<Transaction> arr_temp = new ArrayList<>();
    private String showTotal = "";
    private String testInt = "";

    /**
     * Constructor thats calles in the class getEkonomi
     * @param context
     * @param layoutResourceId
     * @param data
     */
    public EkonomiAdapter(Context context, int layoutResourceId, List<Transaction> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    /**
     * getView method that creates holders and connects to the XML icons and fills them with
     * Transaction data depending on the position in the ArrayList
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EkonomiHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EkonomiHolder();

            holder.txtDate = (TextView)row.findViewById(R.id.datum_summa);
            holder.txtTotal = (TextView)row.findViewById(R.id.total_inkomst);
            holder.txtTitle = (TextView)row.findViewById(R.id.ekonomi_item_title);
            holder.change = (Button)row.findViewById(R.id.redigera);
            holder.remove = (Button)row.findViewById(R.id.ta_bort);

            row.setTag(holder);
        }
        else
        {
            holder = (EkonomiHolder)row.getTag();
        }

        /**
         * For loop that flips the data in the output so the newest added goes to top
         */
        for(int i=0; i < data.size(); i++) {
            arr_temp.add(data.get(data.size() - i - 1));
        }

        /**
         * Fix to make balance match the flipped data
         */
        Transaction transactionTest;
        total =0;
        for(int i = 0; i < data.size()- position; i++){
            transactionTest=data.get(i);
            testInt = transactionTest.getTransactionAmount();
            try {
                temp = NumberFormat.getInstance().parse(testInt).intValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            total = total + temp;
        }

        /**
         * Sets the amount, date and balance.
         */
        final Transaction transaction = arr_temp.get(position);
        holder.txtTitle.setText(transaction.getTransactionAmount()+" kr");
        holder.txtDate.setText(transaction.getTransactionDate().substring(0, 10));

        showTotal = Integer.toString(total);
        holder.txtTotal.setText(showTotal + " kr");

        /**
         * Function to remove the entry also creates popup
         */
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ta bort transaction nr." + position, Toast.LENGTH_LONG).show();
                DB_Delete delete = new DB_Delete();
                String URL = "http://spaaket.no-ip.org:1080/GitarrAppAPI/webresources/rest.transaction/" + transaction.getTransactionid();
                delete.execute(URL);
            }
                   });
        return row;
    }

    /**
     * When this holder is eing used you can easily access each view
     * without having to use look-up and that saves processor cycles
     */
    static class EkonomiHolder
    {
        TextView txtTitle;
        TextView txtTotal;
        TextView txtDate;
        Button remove;
        Button change;
    }
}
