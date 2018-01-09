package com.example.meyer.easydraw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    int BrushSize=40;
    float oldX=0,oldY=0;
    Bitmap saveBitMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        imageView = (ImageView)  findViewById(R.id.con_image);
        Display currentDisplay = getWindowManager().getDefaultDisplay();
        float dw = currentDisplay.getWidth();
        float dh = currentDisplay.getHeight();

        saveBitMap=Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);
        bitmap = Bitmap.createBitmap((int) dw, (int) dh, Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        imageView.setImageBitmap(bitmap);
        imageView.setOnTouchListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            ClearImage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ClearImage(){
        int cTemp= paint.getColor();
        paint.setColor(Color.WHITE);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        View view=(View)findViewById(R.id.con_image);
        view.invalidate();
        paint.setColor(cTemp);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Color color=new Color();

        if (id == R.id.nav_rot) {
            paint.setColor(Color.rgb(230,50,0));
        } else if (id == R.id.nav_blau) {
            paint.setColor(Color.rgb(30,30,200));
        } else if (id == R.id.nav_gruen) {
            paint.setColor(Color.rgb(30,170,40));
        } else if (id == R.id.nav_braun) {
            paint.setColor(Color.rgb(170,100,30));
        } else if (id == R.id.nav_gelb) {
            paint.setColor(Color.rgb(255,250,10));
        } else if (id == R.id.nav_haut) {
            paint.setColor(Color.rgb(255,230,180));
        } else if (id == R.id.nav_schwarz) {
            paint.setColor(Color.rgb(0,0,0));
        } else if (id == R.id.nav_weiss) {
            paint.setColor(Color.rgb(255,255,255));
        }
        else if (id == R.id.nav_kleiner) {
            try {
                BrushSize=BrushSize/2;
            }catch (Exception ex) {}
        }
        else if (id == R.id.nav_groesser) {
            try {
                BrushSize=BrushSize*2;
            }catch (Exception ex) {}
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void touch_move(View view,float x, float y)
    {

        canvas.drawCircle(x,y,BrushSize/2,paint);

        paint.setStrokeWidth(BrushSize);
        canvas.drawLine(oldX,oldY,x,y,paint);
        view.invalidate();
        oldY=y;
        oldX=x;

        //alte x,y werte speichern und linie ziehen
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX=motionEvent.getX();
                oldY=motionEvent.getY();
                touch_move(view,x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(view,x, y);
                break;
        }
        return true;
    }
}
