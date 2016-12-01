package com.market_pymes.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.market_pymes.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.market_pymes.Single.Globals;
import com.market_pymes.helper.JsonHelper;

public class Fragment1 extends Fragment {
    private TextView Vcont, Vcred;
    private String date, DB_name;

    public Fragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_home, container, false);
        BarChart barChart = (BarChart) rootView.findViewById(R.id.graf1);

        date = getdate();
        Globals DataBase = Globals.getInstance();
        DB_name = DataBase.getDB();
        Vcont = (TextView) rootView.findViewById(R.id.Vcontado);
        Vcred = (TextView) rootView.findViewById(R.id.Vcredito);

        new Ccontado().execute(date, DB_name);
        new Ccredito().execute(date, DB_name);

        ArrayList<BarEntry> batEntries = new ArrayList<BarEntry>();
        batEntries.add(new BarEntry(44f,0));
        batEntries.add(new BarEntry(88f,1));
        batEntries.add(new BarEntry(66f,2));
        batEntries.add(new BarEntry(12f,3));
        batEntries.add(new BarEntry(19f,4));
        batEntries.add(new BarEntry(91f,5));

        BarDataSet barDataSet;

        barDataSet = new BarDataSet(batEntries,"Dates");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        ArrayList<IBarDataSet> theDates = new ArrayList<IBarDataSet>();
        theDates.add(barDataSet);

        BarData barData = new BarData(theDates);
        barData.setValueTextSize(12f);
        barData.setBarWidth(0.9f);
        barChart.setTouchEnabled(false);
        barChart.setData(barData);

        return rootView;
    }

    private String getdate() {
        Date d = new Date();
        CharSequence fecha  = DateFormat.format("yyyy/MM/dd", d.getTime());
        String Fecha = fecha.toString();
        return Fecha;
    }

    class Ccontado extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            JsonHelper JsonHelper = new JsonHelper();
            String json = "";
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", DB_name));
                param.add(new BasicNameValuePair("fecha", date));
                param.add(new BasicNameValuePair("tipo", "1"));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/home.php";
                json = JsonHelper.HttpRequest(url_home, param);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String json) {
            if (json != null){
                Vcont.setText("Monto total: " + json);
            }
        }
    }

    class Ccredito extends AsyncTask<String, String, String> {
        protected String doInBackground(String... args) {
            JsonHelper JsonHelper = new JsonHelper();
            String json = "";
            try {
                List param = new ArrayList();
                param.add(new BasicNameValuePair("DB_name", DB_name));
                param.add(new BasicNameValuePair("fecha", date));
                param.add(new BasicNameValuePair("tipo", "2"));
                String url_home = "http://www.demomp2015.yoogooo.com/demoMovil/Web-Service/home.php";
                json = JsonHelper.HttpRequest(url_home, param);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String json) {
            if (json != null){
                Vcred.setText("Monto total: " + json);
            }
        }
    }
}
