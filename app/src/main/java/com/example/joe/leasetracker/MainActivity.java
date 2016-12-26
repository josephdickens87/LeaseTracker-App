//Experimental

package com.example.joe.leasetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        savebutton=(Button)findViewById(R.id.saveButton);
        savebutton.setOnClickListener(save);
        loadButton=(Button)findViewById(R.id.loadButton);
        loadButton.setOnClickListener(load);
        calculateButton = (Button)findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(calc);
        leaseDate = (EditText) findViewById(R.id.leaseDate);
        leaseMonths = (EditText) findViewById(R.id.leaseMonths);
        milesAllowed = (EditText) findViewById(R.id.milesAllowed);
        daysLeased = (TextView) findViewById(R.id.daysLeased);
        expectedMiles = (TextView) findViewById(R.id.expectedMiles);
        overMiles = (TextView) findViewById(R.id.overMiles);
        currentMiles = (EditText) findViewById(R.id.currentMiles);

    }

    Button calculateButton;
    Button savebutton;
    Button loadButton;
    EditText leaseDate;
    EditText currentMiles;
    EditText leaseMonths;
    TextView daysLeased;
    TextView expectedMiles;
    EditText milesAllowed;
    TextView overMiles;




    DecimalFormat df2 = new DecimalFormat(".##");


    View.OnClickListener save = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {


            SharedPreferences leaseInfo = getSharedPreferences("LeaseData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = leaseInfo.edit();
            editor.putString("leaseDate",leaseDate.getText().toString());
            editor.putString("leaseMonths",leaseMonths.getText().toString());
            editor.putString("milesAllowed",milesAllowed.getText().toString());

            editor.apply();

            Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();


            String leaseString = leaseDate.getText().toString();
            LocalDateTime today = LocalDateTime.now();
            LocalDateTime jodalease = LocalDateTime.parse(leaseString, DateTimeFormat
                    .forPattern("MM/dd/yyyy"));
            int currentDaysLeased = Days.daysBetween(jodalease, today).getDays();

            //daysLeased.setText("" + currentDaysLeased);


            String leaseMonthsInput = leaseMonths.getText().toString();
            int leaseMonthsInt = Integer.parseInt(leaseMonthsInput);

            LocalDateTime leaseEnd = jodalease.plusMonths(leaseMonthsInt);
            int leaseLengthDays = Days.daysBetween(jodalease, leaseEnd).getDays();


            String milesInput = currentMiles.getText().toString();
            int currMilesInt = Integer.parseInt(milesInput);

            double avgMiles = (currMilesInt / currentDaysLeased);
            double anticipatedMiles = avgMiles*leaseLengthDays;


            //expectedMiles.setText("" + df2.format(anticipatedMiles));


            String milesString = milesAllowed.getText().toString();
            int milesyearInt = Integer.parseInt(milesString);
            int monthlyAllowedMiles = milesyearInt/12;
            int totalAllowedMiles = monthlyAllowedMiles*leaseMonthsInt;
            double milesOver = anticipatedMiles - totalAllowedMiles;

            daysLeased.setText("" + currentDaysLeased);
            expectedMiles.setText("" + df2.format(anticipatedMiles));
            overMiles.setText("" + df2.format(milesOver));

        }
    };

    /*
    private double GetAverageMiles(String currMiles, int currDays ){
        return Double.parseDouble(currMiles)/(double)currDays;
    }
    private double GetAnticipatedMiles(double avgMiles, int leaseLengthDays ){
        return avgMiles*(double)leaseLengthDays;
    }
    */






    View.OnClickListener load =new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            SharedPreferences sharedPreferences = getSharedPreferences("LeaseData", Context
                    .MODE_PRIVATE);
            String leaseDate = sharedPreferences.getString("leaseDate", "f");
            String leaseMonths = sharedPreferences.getString("leaseMonths", "f");
            String milesAllowed = sharedPreferences.getString("milesAllowed", "f");

            LocalDateTime today = LocalDateTime.now();


            String currMilesString = currentMiles.getText().toString();
            int currMilesInt = Integer.parseInt(currMilesString);

            LocalDateTime jodalease = LocalDateTime.parse(leaseDate, DateTimeFormat
                    .forPattern("MM/dd/yyyy"));
            int currentDaysLeased = Days.daysBetween(jodalease, today).getDays();
            int leaseMonthsInt = Integer.parseInt(leaseMonths);
            int milesAllowedYearInt = Integer.parseInt(milesAllowed);

            LocalDateTime leaseEnd = jodalease.plusMonths(leaseMonthsInt);
            int leaseLengthDays = Days.daysBetween(jodalease, leaseEnd).getDays();



            double avgMiles = (currMilesInt / currentDaysLeased);

            double anticipatedMiles = avgMiles * leaseLengthDays;



            int monthlyAllowedMiles = milesAllowedYearInt / 12;
            int totalAllowedMiles = monthlyAllowedMiles * leaseMonthsInt;
            double milesOver = anticipatedMiles - totalAllowedMiles;





            daysLeased.setText("" + currentDaysLeased);
            expectedMiles.setText("" + df2.format(anticipatedMiles));
            overMiles.setText("" + df2.format(milesOver));
        }
    };

    View.OnClickListener calc = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {

        }
    };

    public void next()
    {
        Intent next = new Intent(this, Main2Activity.class);
        startActivity(next);
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

    @Override
    public void onClick(View view)
    {

    }
}