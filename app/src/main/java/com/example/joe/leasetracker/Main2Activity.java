package com.example.joe.leasetracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        JodaTimeAndroid.init(this);
        calculateButton.setOnClickListener(load);
        daysLeased = (TextView) findViewById(R.id.daysLeased);
        expectedMiles = (TextView) findViewById(R.id.expectedMiles);
        overMiles = (TextView) findViewById(R.id.overMiles);
        currentMiles = (EditText) findViewById(R.id.currentMiles);


    }

    Button calculateButton;
    TextView daysLeased;
    TextView expectedMiles;
    TextView overMiles;
    EditText currentMiles;



    LocalDateTime today = LocalDateTime.now();

    DecimalFormat df2 = new DecimalFormat(".##");

    View.OnClickListener load = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("LeaseData", Context
                    .MODE_PRIVATE);
            String leaseDate = sharedPreferences.getString("leaseDate", "f");
            String leaseMonths = sharedPreferences.getString("leaseMonths", "f");
            String milesAllowed = sharedPreferences.getString("milesAllowed", "f");


            String currMilesString = currentMiles.getText().toString();
            int currMiles = Integer.parseInt(currMilesString);

            LocalDateTime jodalease = LocalDateTime.parse(leaseDate, DateTimeFormat
                    .forPattern("MM/dd/yyyy"));
            int currentDaysLeased = Days.daysBetween(jodalease, today).getDays();
            int leaseMonthsInt = Integer.parseInt(leaseMonths);
            int milesAllowedInt = Integer.parseInt(milesAllowed);

            LocalDateTime leaseEnd = jodalease.plusMonths(leaseMonthsInt);
            int leaseLengthDays = Days.daysBetween(jodalease, leaseEnd).getDays();

            double avgMiles = currMiles / currentDaysLeased;
            double anticipatedMiles = avgMiles * leaseLengthDays;

            int monthlyAllowedMiles = milesAllowedInt / 12;
            int totalAllowedMiles = monthlyAllowedMiles * leaseMonthsInt;
            double milesOver = anticipatedMiles - totalAllowedMiles;



            //overMiles.setText("" + df2.format(milesOver));
            //expectedMiles.setText("" + df2.format(anticipatedMiles));
            //daysLeased.setText("" + currentDaysLeased);

            //overMiles.setText(leaseDate);
            //expectedMiles.setText(leaseMonths);
            //daysLeased.setText(milesAllowed);
        }
    };


    @Override
    public void onClick(View view)
    {

    }
}
