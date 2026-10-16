package com.oheydeniis.pocketarcade;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivityLoggedBinding;

public class LoggedActivity extends AppCompatActivity {

    private static final String TAG = "LoggedActivity";
    private ActivityLoggedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoggedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d(TAG, "onCreate() executado");
        String nickname = getIntent().getStringExtra("nick");
        if (nickname != null && !nickname.trim().isEmpty()) {
            binding.txtGreeting.setText("Olá, " + nickname + "!");
        }
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade);
        binding.contentContainer.startAnimation(fadeInAnimation);
        setupNavigation();
    }

    private void setupNavigation() {

        binding.cardScanner.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedActivity.this, ScannerGameActivity.class);
            startActivity(intent);
        });

        binding.cardArena.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedActivity.this, ArenaActivity.class);
            startActivity(intent);
        });

        binding.cardSecretCode.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedActivity.this, SecretGameActivity.class);
            startActivity(intent);
        });

        binding.cardRandomizer.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedActivity.this, RandomGameActivity.class);
            startActivity(intent);
        });
        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() executado");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() executado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executado");
    }
}