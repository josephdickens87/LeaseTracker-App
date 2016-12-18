package com.example.joe.leasetracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JodaTimeAndroid.init(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy");

        //final String formattedDateToday = formatter.print(today);


        currentMiles = (EditText) findViewById(R.id.currentMiles);
        leaseMonths = (EditText) findViewById(R.id.leaseMonths);
        daysLeased = (TextView) findViewById(R.id.daysLeased);
        leaseDate = (EditText) findViewById(R.id.leaseDate);
        expectedMiles = (TextView) findViewById(R.id.expectedMiles);
        milesAllowed = (EditText) findViewById(R.id.milesAllowed);
        overMiles = (TextView) findViewById(R.id.overMiles);

        final DecimalFormat df2 = new DecimalFormat(".##");

        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String leaseString = leaseDate.getText().toString();
                LocalDateTime today = LocalDateTime.now();
                LocalDateTime jodalease = LocalDateTime.parse(leaseString, DateTimeFormat
                        .forPattern("MM/dd/yyyy"));
                int currentDaysLeased = Days.daysBetween(jodalease, today).getDays();

                daysLeased.setText("" + currentDaysLeased);


                String leaseMonthsInput = leaseMonths.getText().toString();
                int leaseMonthsInt = Integer.parseInt(leaseMonthsInput);

                LocalDateTime leaseEnd = jodalease.plusMonths(leaseMonthsInt);
                int leaseLengthDays = Days.daysBetween(jodalease, leaseEnd).getDays();


                String milesInput = currentMiles.getText().toString();
                int currentMilesInt = Integer.parseInt(milesInput);
                double avgMiles = (double)currentMilesInt/(double)currentDaysLeased;
                double anticipatedMiles = avgMiles*leaseLengthDays;

                expectedMiles.setText("" + df2.format(anticipatedMiles));


                String milesString = milesAllowed.getText().toString();
                int milesyearInt = Integer.parseInt(milesString);
                int monthlyAllowedMiles = milesyearInt/12;
                int totalAllowedMiles = monthlyAllowedMiles*leaseMonthsInt;
                double milesOver = anticipatedMiles - totalAllowedMiles;

                overMiles.setText("" + df2.format(milesOver));


                //Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                //startActivity(intent);
            }
        });

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
