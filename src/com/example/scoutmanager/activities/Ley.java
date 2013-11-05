package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class Ley extends Activity {
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

		this.setContentView(R.layout.page_adapter_ley);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("LEY SCOUT");

        MyPagerAdapter adapter = new MyPagerAdapter();
        ViewPager myPager = (ViewPager) findViewById(R.id.pagerLey);
        myPager.setAdapter(adapter);
        myPager.setCurrentItem(0);
    }
    
    public void farLeftButtonClick(View v)
    {
            Toast.makeText(this, "Far Left Button Clicked", Toast.LENGTH_SHORT).show(); 

    }

    public void farRightButtonClick(View v)
    {
            Toast.makeText(this, "Far Right Elephant Button Clicked", Toast.LENGTH_SHORT).show(); 

    }

    private class MyPagerAdapter extends PagerAdapter {

            public int getCount() {
                    return 10;
            }

            public Object instantiateItem(View collection, int position) {

                    LayoutInflater inflater = (LayoutInflater) collection.getContext()
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    int resId = 0;
                    switch (position) {
                    case 0:
                            resId = R.layout.articulo1;
                            break;
                    case 1:
                            resId = R.layout.articulo2;
                            break;
                    case 2:
                            resId = R.layout.articulo3;
                            break;
                    case 3:
                            resId = R.layout.articulo4;
                            break;
                    case 4:
                            resId = R.layout.articulo5;
                            break;
                    case 5:
	                        resId = R.layout.articulo6;
	                        break;
                    case 6:
	                        resId = R.layout.articulo7;
	                        break;
                    case 7:
	                        resId = R.layout.articulo8;
	                        break;
                    case 8:
	                        resId = R.layout.articulo9;
	                        break;
                    case 9:
	                        resId = R.layout.articulo10;
	                        break;
                    }

                    View view = inflater.inflate(resId, null);

                    ((ViewPager) collection).addView(view, 0);

                    return view;
            }

            @Override
            public void destroyItem(View arg0, int arg1, Object arg2) {
                    ((ViewPager) arg0).removeView((View) arg2);

            }

            @Override
            public void finishUpdate(View arg0) {
                    // TODO Auto-generated method stub

            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                    return arg0 == ((View) arg1);

            }

            @Override
            public void restoreState(Parcelable arg0, ClassLoader arg1) {
                    // TODO Auto-generated method stub

            }

            @Override
            public Parcelable saveState() {
                    // TODO Auto-generated method stub
                    return null;
            }

            @Override
            public void startUpdate(View arg0) {
                    // TODO Auto-generated method stub

            }

    }

}
