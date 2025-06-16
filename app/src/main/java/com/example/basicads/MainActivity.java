package com.example.basicads;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout adContainerView; // or ViewGroup

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        // Initialize the ad container
        adContainerView = findViewById(R.id.ad_view_container);

        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();

// Request an anchored adaptive banner with a width of 360.
// Create a new ad view.
        AdManagerAdView adView = new AdManagerAdView(this);
        adView.setAdUnitId("ca-app-pub-5718236948049728/8953790546");
// Request an anchored adaptive banner with a width of 360.
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360));

// Replace ad container with new ad view.
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        adView.loadAd(new AdManagerAdRequest.Builder().build());

        //setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}