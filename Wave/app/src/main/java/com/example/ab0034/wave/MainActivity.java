package com.example.ab0034.wave;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.gelitenight.waveview.library.WaveView;

public class MainActivity extends AppCompatActivity {
    WaveHelper mWaveHelper;
Button btn;
    int mBorderColor = Color.parseColor("#51c3dc");
//    int mBorderWidth = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WaveView waveView = (WaveView) findViewById(R.id.wave);
//        waveView.setBorder(mBorderWidth, mBorderColor);
//        btn =(Button)findViewById(R.id.btn);
        mWaveHelper = new WaveHelper(waveView);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        waveView.setShapeType(WaveView.ShapeType.SQUARE);
        waveView.setWaveColor(
                Color.parseColor("#a992cdda"),
                Color.parseColor("#51c3dc"));
//        mBorderColor = Color.parseColor("#51c3dc");
//                waveView.setBorder(mBorderWidth, mBorderColor);
        mWaveHelper.start();
    }
}
