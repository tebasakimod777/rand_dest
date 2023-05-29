package info.tebiirand_dest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private LocationManager mLocationManager;
    Random x_rand;
    Random y_rand;
    private double user_x = 36.1085765;
    private double user_y = 140.0998895;
    private int mLocationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        x_rand = new Random();
        y_rand = new Random();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

    }
    @Override
    protected void onResume() {
        Log.d("PlaceSample", "plog onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("PlaceSample", "plog onPause()");
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("PlaceSample", "plog onLcationChanged");

        Log.d("PlaceSample", "user location " + String.valueOf(location.getLatitude()) + " and " + String.valueOf(location.getLongitude()));
        user_x = location.getLatitude();
        user_y = location.getLongitude();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        switch (i) {
            case LocationProvider.AVAILABLE:
                Log.v("PlaceSample", "AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.v("PlaceSample", "OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.v("PlaceSample", "TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_button) {
            @SuppressLint("DefaultLocale") String url = String.format("https://www.google.co.jp/maps/place/%f,%f", x_rand.nextDouble() + user_x, y_rand.nextDouble() + user_y);
            Log.d("PlaceSample", url);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else {
            Log.d("PlaceSample", "unknown button");
        }
    }

}