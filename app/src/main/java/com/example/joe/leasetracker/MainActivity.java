//Experimental changes

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
import android.widget.Toast;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        saveButton1 = (Button) findViewById(R.id.saveButton1);
        saveButton1.setOnClickListener(saveOne);
        saveButton2 = (Button) findViewById(R.id.saveButton2);
        saveButton2.setOnClickListener(saveTwo);
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(nextActivity);
        leaseDate = (EditText) findViewById(R.id.leaseDate);
        leaseMonths = (EditText) findViewById(R.id.leaseMonths);
        milesAllowed = (EditText) findViewById(R.id.milesAllowed);
        overage = (EditText) findViewById(R.id.overage);
        Toast.makeText(this, "Enter Date as MM/DD/YYYY", Toast.LENGTH_LONG).show();
    }

    Button saveButton2;
    Button saveButton1;
    Button nextButton;
    EditText leaseDate;
    EditText leaseMonths;
    EditText milesAllowed;
    EditText overage;


    View.OnClickListener saveOne = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {


            SharedPreferences leaseInfo = getSharedPreferences("LeaseData1", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = leaseInfo.edit();
            editor.putString("leaseDate", leaseDate.getText().toString());
            editor.putString("leaseMonths", leaseMonths.getText().toString());
            editor.putString("milesAllowed", milesAllowed.getText().toString());
            editor.putString("overageCharge", overage.getText().toString());

            editor.apply();

            Toast.makeText(MainActivity.this, "Vehicle 1 Saved", Toast.LENGTH_LONG).show();

        }
    };

    View.OnClickListener saveTwo = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            SharedPreferences leaseInfo = getSharedPreferences("LeaseData2", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = leaseInfo.edit();
            editor.putString("leaseDate", leaseDate.getText().toString());
            editor.putString("leaseMonths", leaseMonths.getText().toString());
            editor.putString("milesAllowed", milesAllowed.getText().toString());
            editor.putString("overageCharge", overage.getText().toString());


            editor.apply();

            Toast.makeText(MainActivity.this, "Vehicle 2 Saved", Toast.LENGTH_LONG).show();
        }
    };


    View.OnClickListener nextActivity = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            next();
        }
    };


    public void next()
    {
        Intent next = new Intent(this, Main3Activity.class);
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