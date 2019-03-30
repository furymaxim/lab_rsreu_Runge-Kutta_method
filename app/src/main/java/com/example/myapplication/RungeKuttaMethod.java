package com.example.myapplication;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RungeKuttaMethod {

    private Expression Func;
    private double x0,y0,h,xEnd;

    public RungeKuttaMethod (String FuncStr,String x0Str,String y0Str,String hStr,String xEndStr) {
        Func = new ExpressionBuilder(FuncStr).variables("x", "y").build();
        x0 = Double.parseDouble(x0Str);
        y0 = Double.parseDouble(y0Str);
        h = Double.parseDouble(hStr);
        xEnd = Double.parseDouble(xEndStr);
    }


    public LineGraphSeries<DataPoint> getSolutionPoints () {
        double x,y,K1,K2,K3,K4;
        int n;
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        n = (int)((xEnd-x0)/h);
        x=x0;y=y0;
        for (int i=0;i<n;i++) {
            series.appendData(new DataPoint(x,y),false,n);
            K1 = h*Func.setVariable("x",x).setVariable("y",y).evaluate();
            K2 = h*Func.setVariable("x",x+h/2).setVariable("y",y+K1/2).evaluate();
            K3 = h*Func.setVariable("x",x+h/2).setVariable("y",y+K2/2).evaluate();
            K4 = h*Func.setVariable("x",x+h).setVariable("y",y+K3).evaluate();
            y = y + (K1+2*K2+2*K3+K4)/6;
            x = x + h;
        }
        return series;
    }
}
