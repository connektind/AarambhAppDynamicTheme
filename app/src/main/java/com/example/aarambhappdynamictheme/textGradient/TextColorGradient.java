package com.example.aarambhappdynamictheme.textGradient;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aarambhappdynamictheme.R;

public class TextColorGradient {

    public void getColorTextGradient(TextView text,String start_color,String end_color) {
        TextPaint paint = text.getPaint();
        float width = paint.measureText("");

        Shader textShader = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            textShader = new LinearGradient(0, 0, width, text.getTextSize(),
                    new int[]{
                            Color.parseColor(start_color),
                            Color.parseColor(end_color),
                    }, null, Shader.TileMode.CLAMP);

        }
        text.getPaint().setShader(textShader);
    }

//    Shader shader = new LinearGradient(0,100,10,100,
//            new int[]{resources.getColor(R.color.clrc2),resources.getColor(R.color.clrfe),resources.getColor(R.color.clrc2)}
//            ,new float[]{0.4f,0.2f,0.4f}, Shader.TileMode.MIRROR);
//        option_.setTextColor(resources.getColor(R.color.clrc2));
//        option_.getPaint().setShader( shader );
}
