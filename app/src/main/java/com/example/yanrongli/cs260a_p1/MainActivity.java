package com.example.yanrongli.cs260a_p1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public static String[] exercises;
    public static float[] countPer100Cal = {350, 200, 225, 25, 25, 10, 100, 12, 20, 12, 13, 15};
    public static String[] units = {"rep", "rep", "rep", "min", "min", "min", "rep", "min", "min", "min", "min", "min"};
    //For e2c
    public static int checkedButtonId;
    public static int countExercise;
    //For c2e
    public static EditText calorieNum;
    public static TextView[] resultNum = new TextView[12];
    //For e2e
    public static Spinner sp1, sp2;
    public static EditText count1;
    public static String exercise1, exercise2;
    public static TextView result;
    public static String radioVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        exercises = getResources().getStringArray(R.array.exercise_names);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabHost tab = (TabHost) findViewById(R.id.tabHost);
        tab.setup();

        TabHost.TabSpec spec1 = tab.newTabSpec("TAB 1");
        spec1.setIndicator("", getDrawable(R.drawable.tab1_icon));
        spec1.setContent(R.id.layout_e2c);
        tab.addTab(spec1);

        TabHost.TabSpec spec2 = tab.newTabSpec("TAB 2");
        spec2.setIndicator("", getDrawable(R.drawable.tab2_icon));
        spec2.setContent(R.id.layout_c2e);
        tab.addTab(spec2);

        TabHost.TabSpec spec3 = tab.newTabSpec("TAB 3");
        spec3.setIndicator("", getDrawable(R.drawable.tab3_icon));
        spec3.setContent(R.id.layout_e2e);
        tab.addTab(spec3);

        for (int i = 0; i < tab.getTabWidget().getChildCount(); i++)
        {
            tab.getTabWidget().getChildAt(i).setPadding(0,0,0,0);
        }

        //Layout_e2c activity:
        Button e2c_calc = (Button) findViewById(R.id.e2c_calc);

        e2c_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the amount of exercise
                EditText tmp3 = (EditText) findViewById(R.id.e2c_editText);
                if(tmp3.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please enter exercise amount", Toast.LENGTH_SHORT).show();
                    //System.out.println(exercises[1]);
                    return;
                }
                countExercise = (int) (Float.parseFloat(tmp3.getText().toString()));

                //Get the unit of exercise
                RadioGroup rgtmp = (RadioGroup) findViewById(R.id.e2c_radioGroup1);
                if(rgtmp.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "Please select the unit!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tmpUnit = ((RadioButton) findViewById(rgtmp.getCheckedRadioButtonId())).getText().toString();


                //Get the exercise
                RadioGridGroup tmpGrid = (RadioGridGroup) findViewById(R.id.e2c_radioGrid);
                if(tmpGrid.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "Please select the exercise!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tmpExer = ((RadioButton) findViewById(tmpGrid.getCheckedRadioButtonId())).getText().toString();
                System.out.println(tmpExer);
                int exerIdx = 0;
                boolean ifCalc = false;
                for (int i = 0; i < 12; i++)
                {
                    if(Objects.equals(tmpExer, exercises[i]))
                    {
                        exerIdx = i;
                        if(Objects.equals(units[i], tmpUnit))
                        {
                            ifCalc = true;
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Wrong unit!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    }
                }
                if(ifCalc)
                {
                    int res = (int) (100 * countExercise / countPer100Cal[exerIdx]);
                    result = (TextView) findViewById(R.id.e2c_result);
                    result.setText(Integer.toString(res) + " cal");
                }

            }
        });




        //Layout_c2e activity:
        Button c2e_calc = (Button) findViewById(R.id.c2e_calc);

        c2e_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Test II", Toast.LENGTH_LONG).show();

                calorieNum = (EditText) findViewById(R.id.c2e_editText);
                if(calorieNum.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please enter calories amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                float numCalorie = Float.parseFloat(calorieNum.getText().toString());
                int[] numExercises = new int[12];
                for (int i = 0; i < 12; i++)
                {
                    float tmp = numCalorie / 100 * countPer100Cal[i];
                    numExercises[i] = (int) tmp;
                    resultNum[i] = (TextView) findViewById(getResources().getIdentifier("c2e_item" + (i+1) + "_2", "id", getPackageName()));
                    //System.out.println(resultNum[i]);
                    resultNum[i].setText(Integer.toString(numExercises[i]));
                }



            }
        });

        //Layout_e2e activity

        sp1 = (Spinner)findViewById(R.id.e2e_spinner);
        sp2 = (Spinner)findViewById(R.id.e2e_spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exercises);
        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //int index = parent.getSelectedItemPosition();
                //Toast.makeText(getBaseContext(), "You select " + exercises[index], Toast.LENGTH_LONG).show();
                //String item = (String) parent.getItemAtPosition(position);
                //parent.getChildAt(0).setBackgroundColor(Color.parseColor("#000000"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //int index = parent.getSelectedItemPosition();
                //Toast.makeText(getBaseContext(), "You select " + exercises[index], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button e2e_calc = (Button) findViewById(R.id.e2e_calc);
        e2e_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Test III", Toast.LENGTH_LONG).show();
                exercise1 = sp1.getSelectedItem().toString();
                exercise2 = sp2.getSelectedItem().toString();
                EditText tmp = (EditText) findViewById(R.id.e2e_editText);
                if(tmp.getText().toString().matches("")){
                    Toast.makeText(MainActivity.this, "Please enter exercise amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                Float timeVal = Float.parseFloat(tmp.getText().toString());

                RadioGroup rg = (RadioGroup)findViewById(R.id.e2e_radioGroup1);
                if(rg.getCheckedRadioButtonId() == -1){
                    Toast.makeText(MainActivity.this, "Please select the unit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                radioVal = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

                int indexEx1 = 0, indexEx2 = 0;
                boolean ifCalc = false;

                for (int i = 0; i < 12; i++)
                {
                    if(Objects.equals(exercises[i], exercise1))
                    {
                        if(Objects.equals(radioVal, units[i])) {
                            indexEx1 = i;
                            ifCalc = true;
                        }
                        else {
                            //System.out.println(radioVal);
                            //System.out.println(units[i]);
                            Toast.makeText(MainActivity.this, "Wrong unit!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                    }
                }

                if(ifCalc) {
                    for (int i = 0; i < 12; i++) {
                        if (Objects.equals(exercises[i], exercise2)) {
                            indexEx2 = i;
                            break;
                        }
                    }

                    int resultNum = (int) (timeVal * countPer100Cal[indexEx2] / countPer100Cal[indexEx1]);
                    result = (TextView) findViewById(R.id.e2e_result);
                    result.setText(Integer.toString(resultNum) + " " + units[indexEx2]);
                }



            }
        });






    }





//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
