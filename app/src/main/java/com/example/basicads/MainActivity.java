package com.example.basicads;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    private FrameLayout adContainerView; // or ViewGroup
    private InterstitialAd interstitialAd;

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
        //adView.setAdUnitId("ca-app-pub-5718236948049728/8953790546");

        adView.setAdUnitId("ca-app-pub-3940256099942544/9214589741"); // google testadd

// Request an anchored adaptive banner with a width of 360.
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360));

// Replace ad container with new ad view.
        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        adView.loadAd(new AdManagerAdRequest.Builder().build());

        // Find the button by its ID
        Button button = findViewById(R.id.button);

        // Set click listener
        button.setOnClickListener(view -> {
            // Code to execute when button is clicked
            Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();

            // You can add any other actions here
            // For example:
            // startActivity(new Intent(this, AnotherActivity.class));

            // beof add

            // --- werkt niet goed (20250616 SDE)

            InterstitialAd.load(
                    this,
                    "ca-app-pub-5718236948049728/3498283882",
                    new AdRequest.Builder().build(),
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            Log.d("BasicAds", "Ad was loaded.");
                            MainActivity.this.interstitialAd = interstitialAd;
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            Log.d("BasicAds", loadAdError.getMessage());
                            interstitialAd = null;
                        }
                    });
            // eof ad
        });
        //setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}