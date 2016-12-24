package com.example.joe.leasetracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity
{
    //TextView instructionText;
    EditText leaseDate;
    EditText currentMiles;
    EditText leaseMonths;
    TextView daysLeased;
    TextView expectedMiles;
    EditText milesAllowed;
    TextView overMiles;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        currentMiles = (EditText) findViewById(R.id.currentMiles);
        leaseMonths = (EditText) findViewById(R.id.leaseMonths);
        daysLeased = (TextView) findViewById(R.id.daysLeased);
        leaseDate = (EditText) findViewById(R.id.leaseDate);
        expectedMiles = (TextView) findViewById(R.id.expectedMiles);
        milesAllowed = (EditText) findViewById(R.id.milesAllowed);
        overMiles = (TextView) findViewById(R.id.overMiles);

        final DecimalFormat df2 = new DecimalFormat(".##");
        final LocalDateTime today = LocalDateTime.now();


        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LocalDateTime jodalease = LocalDateTime.parse(GetLeaseDate(), DateTimeFormat
                        .forPattern("MM/dd/yyyy"));
                int currentDaysLeased = Days.daysBetween(jodalease, today).getDays();


                int leaseMonthsInt = Integer.parseInt(GetLeaseMonths());
                LocalDateTime leaseEnd = jodalease.plusMonths(leaseMonthsInt);
                int leaseLengthDays = Days.daysBetween(jodalease, leaseEnd).getDays();


                double avgMiles = GetAverageMiles(GetCurrentMiles(), currentDaysLeased);
                double anticipatedMiles = GetAnticipatedMiles(avgMiles, leaseLengthDays);


                int milesyearInt = Integer.parseInt(GetMilesAllowed());
                int monthlyAllowedMiles = milesyearInt / 12;
                int totalAllowedMiles = monthlyAllowedMiles * leaseMonthsInt;
                double milesOver = anticipatedMiles - totalAllowedMiles;

                daysLeased.setText("" + currentDaysLeased);
                expectedMiles.setText("" + df2.format(anticipatedMiles));
                overMiles.setText("" + df2.format(milesOver));

            }
        });

    }

    private double GetAverageMiles(String currMiles, int currDays)
    {
        return Double.parseDouble(currMiles) / (double) currDays;
    }

    private double GetAnticipatedMiles(double avgMiles, int leaseLengthDays)
    {
        return avgMiles * (double) leaseLengthDays;
    }

    private String GetLeaseDate()
    {
        return leaseDate.getText().toString();
    }

    private String GetLeaseMonths()
    {
        return leaseMonths.getText().toString();
    }

    private String GetCurrentMiles()
    {
        return currentMiles.getText().toString();
    }

    private String GetMilesAllowed()
    {
        return milesAllowed.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}