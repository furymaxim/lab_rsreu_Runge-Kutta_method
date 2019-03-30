package com.example.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.xml.sax.DTDHandler;

public class MainActivity extends AppCompatActivity {


    private Button mFind;
    private LineGraphSeries<DataPoint> SeriesOfFuncHalf;
    private LineGraphSeries<DataPoint> SeriesOfFuncDouble;
    private LineGraphSeries<DataPoint> SeriesOfFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final GraphView graph = findViewById(R.id.graph);
        final EditText Func = findViewById(R.id.FuncStr);
        final EditText x0 = findViewById(R.id.x0Str);
        final EditText y0 = findViewById(R.id.y0Str);
        final EditText h = findViewById(R.id.hStr);
        final EditText xEnd = findViewById(R.id.xEndStr);

        graph.setVisibility(View.GONE);

        mFind = findViewById(R.id.Find);

        mFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Func_str = Func.getText().toString();
                String x0_str = x0.getText().toString();
                String y0_str = y0.getText().toString();
                String h_str = h.getText().toString();
                String xEnd_str = xEnd.getText().toString();

                String doubleH_str = h.getText().toString();
                double doubleH_int = Double.parseDouble(doubleH_str);
                doubleH_int = 2 * doubleH_int;
                doubleH_str = Double.toString(doubleH_int);

                String halfH_str = h.getText().toString();
                double halfH_int = Double.parseDouble(halfH_str);
                halfH_int = 0.5 * halfH_int;
                halfH_str = Double.toString(halfH_int);


                try{

                    RungeKuttaMethod graphics = new RungeKuttaMethod(Func_str,x0_str,y0_str,h_str,xEnd_str);
                    RungeKuttaMethod graphicsHalf = new RungeKuttaMethod(Func_str,x0_str,y0_str,halfH_str,xEnd_str);
                    RungeKuttaMethod graphicsDouble = new RungeKuttaMethod(Func_str,x0_str,y0_str,doubleH_str,xEnd_str);

                    graph.setVisibility(View.VISIBLE);

                    SeriesOfFunc = graphics.getSolutionPoints();
                    SeriesOfFuncHalf = graphicsHalf.getSolutionPoints();
                    SeriesOfFuncDouble = graphicsDouble.getSolutionPoints();

                    graph.removeAllSeries();
                    graph.getViewport().setScrollable(true);
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(-10);
                    graph.getViewport().setMaxX(10);

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-10);
                    graph.getViewport().setMaxY(10);

                    graph.getViewport().setScalable(true);
                    graph.setTitle("Графики решений");

                    SeriesOfFunc.setColor(Color.GREEN);
                    SeriesOfFunc.setTitle("Шаг h");
                    SeriesOfFuncHalf.setColor(Color.RED);
                    SeriesOfFuncHalf.setTitle("Шаг h/2");
                    SeriesOfFuncDouble.setColor(Color.BLUE);
                    SeriesOfFuncDouble.setTitle("Шаг 2h");
                    graph.addSeries(SeriesOfFunc);
                    graph.addSeries(SeriesOfFuncHalf);
                    graph.addSeries(SeriesOfFuncDouble);


                    graph.getLegendRenderer().setVisible(true);
                    graph.getLegendRenderer().setTextSize(15);
                    graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
                    graph.getGridLabelRenderer().setNumVerticalLabels(6);
                    graph.getGridLabelRenderer().setNumHorizontalLabels(6);



                }catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(), "Проверьте корректность введенных данных", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
}
