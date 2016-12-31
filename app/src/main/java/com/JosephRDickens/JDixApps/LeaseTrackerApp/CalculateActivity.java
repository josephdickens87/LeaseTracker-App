package com.JosephRDickens.JDixApps.LeaseTrackerApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.JDixApps.LeaseTrackerApp.R;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;

public class CalculateActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_activity);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        editText1 = (EditText) findViewById(R.id.editText1);
        textView10 = (TextView) findViewById(R.id.textView10);
        calcOne = (Button) findViewById(R.id.calcOne);
        calcOne.setOnClickListener(calculateOne);
        calcTwo = (Button) findViewById(R.id.calcTwo);
        calcTwo.setOnClickListener(calculateTwo);

    }

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView10;
    EditText editText1;
    Button calcOne;
    Button calcTwo;

    DecimalFormat df2 = new DecimalFormat(".##");



    @Override
    public void onClick(View view)
    {

    }

    View.OnClickListener calculateOne = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            String enterMiles = editText1.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("LeaseData1", Context
                    .MODE_PRIVATE);
            String leaseDate = sharedPreferences.getString("leaseDate", "01/01/2001");
            String milesDelivery = sharedPreferences.getString("milesDelivery", "0");
            String leaseMonths = sharedPreferences.getString("leaseMonths", "24");
            String milesAllowed = sharedPreferences.getString("milesAllowed", "12000");
            String overageCharge = sharedPreferences.getString("overageCharge", ".25");

            textView1.setText(enterMiles);
            textView2.setText(leaseDate);
            textView3.setText(leaseMonths);
            textView4.setText(milesAllowed);

            LocalDateTime today = LocalDateTime.now();



            LocalDateTime jodaLease = LocalDateTime.parse(leaseDate, DateTimeFormat
                    .forPattern("MM/dd/yyyy"));

            int currentDaysLeased = Days.daysBetween(jodaLease, today).getDays();
            int leaseMonthsInt = Integer.parseInt(leaseMonths);
            int milesAllowedYearInt = Integer.parseInt(milesAllowed);

            LocalDateTime leaseEnd = jodaLease.plusMonths(leaseMonthsInt);
            int leaseLengthDays = Days.daysBetween(jodaLease, leaseEnd).getDays();

            double enterMilesDoub = Double.parseDouble(enterMiles);
            double milesDeliveryDoub = Double.parseDouble(milesDelivery);
            double avgMiles = (enterMilesDoub / currentDaysLeased);
            double anticipatedMiles = avgMiles * leaseLengthDays;
            double monthlyAllowedMiles = milesAllowedYearInt / 12;
            double totalAllowedMiles = (monthlyAllowedMiles * leaseMonthsInt) + milesDeliveryDoub;
            double milesOver = anticipatedMiles - totalAllowedMiles;
            double overageChargeDouble = Double.parseDouble(overageCharge);
            double overageChargeAmt = milesOver * overageChargeDouble;

            textView1.setText(enterMiles);
            textView2.setText("" + currentDaysLeased);
            textView3.setText("" + df2.format(anticipatedMiles));

            if (milesOver <= 0)
            {
                textView4.setText("On pace to stay under miles!");
            }

            else
            {
                textView4.setText("" + df2.format(milesOver));
            }

            if (overageChargeAmt <= 0 )
            {
                textView10.setText("$0.00");
            }

            else
            {
                textView10.setText("$" + df2.format(overageChargeAmt));
            }

        }
    };

    View.OnClickListener calculateTwo = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            String enterMiles = editText1.getText().toString();


            SharedPreferences sharedPreferences = getSharedPreferences("LeaseData2", Context
                    .MODE_PRIVATE);
            String leaseDate = sharedPreferences.getString("leaseDate", "01/01/2001");
            String milesDelivery = sharedPreferences.getString("milesDelivery", "0");
            String leaseMonths = sharedPreferences.getString("leaseMonths", "24");
            String milesAllowed = sharedPreferences.getString("milesAllowed", "12000");
            String overageCharge = sharedPreferences.getString("overageCharge", ".25");

            textView1.setText(enterMiles);
            textView2.setText(leaseDate);
            textView3.setText(leaseMonths);
            textView4.setText(milesAllowed);

            LocalDateTime today = LocalDateTime.now();

            LocalDateTime jodaLease = LocalDateTime.parse(leaseDate, DateTimeFormat
                    .forPattern("MM/dd/yyyy"));
            int currentDaysLeased = Days.daysBetween(jodaLease, today).getDays();
            int leaseMonthsInt = Integer.parseInt(leaseMonths);
            int milesAllowedYearInt = Integer.parseInt(milesAllowed);

            LocalDateTime leaseEnd = jodaLease.plusMonths(leaseMonthsInt);
            int leaseLengthDays = Days.daysBetween(jodaLease, leaseEnd).getDays();

            double enterMilesDoub = Double.parseDouble(enterMiles);
            double milesDeliveryDoub = Double.parseDouble(milesDelivery);
            double avgMiles = (enterMilesDoub / currentDaysLeased);
            double anticipatedMiles = avgMiles * leaseLengthDays;
            double monthlyAllowedMiles = milesAllowedYearInt / 12;
            double totalAllowedMiles = (monthlyAllowedMiles * leaseMonthsInt) + milesDeliveryDoub;
            double milesOver = anticipatedMiles - totalAllowedMiles;
            double overageChargeDouble = Double.parseDouble(overageCharge);
            double overageChargeAmt = milesOver * overageChargeDouble;



            textView1.setText(enterMiles);
            textView2.setText("" + currentDaysLeased);
            textView3.setText("" + df2.format(anticipatedMiles));
            if (milesOver <= 0)
            {
                textView4.setText("On pace to stay under miles!");
            }

            else
            {
                textView4.setText("" + df2.format(milesOver));
            }

            if (overageChargeAmt <= 0 )
            {
                textView10.setText("$0.00");
            }

            else
            {
                textView10.setText("$" + df2.format(overageChargeAmt));
            }

        }
    };

}





