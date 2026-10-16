package com.oheydeniis.pocketarcade;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivityArenaBinding;

import java.util.Random;

public class ArenaActivity extends AppCompatActivity {

    private ActivityArenaBinding binding;

    private MediaPlayer soundWin;
    private MediaPlayer soundLose;

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArenaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        soundWin = MediaPlayer.create(this, R.raw.win_sound);
        soundLose = MediaPlayer.create(this, R.raw.loose_sound);

        binding.btnPlayRound.setOnClickListener(v -> playRound());
    }

    private void playRound() {

        binding.btnPlayRound.setEnabled(false);
        int playerChoice = random.nextInt(3);
        int cpuChoice = random.nextInt(3);


        binding.containerPlayer.setTranslationX(-100f);
        binding.containerPlayer.setAlpha(0f);
        binding.containerPlayer.animate()
                .translationX(0f)
                .alpha(1f)
                .setDuration(500)
                .start();


        binding.containerCpu.setTranslationX(100f);
        binding.containerCpu.setAlpha(0f);
        binding.containerCpu.animate()
                .translationX(0f)
                .alpha(1f)
                .setDuration(500)
                .start();


        boolean isWin = false;
        if (playerChoice == cpuChoice) {
            binding.txtResultMessage.setText("Empate na Arena!");
            playSound(soundWin);
        } else if ((playerChoice == 0 && cpuChoice == 2) ||
                (playerChoice == 1 && cpuChoice == 0) ||
                (playerChoice == 2 && cpuChoice == 1)) {
            binding.txtResultMessage.setText("Vitória do Jogador!");
            isWin = true;
            playSound(soundWin);
        } else {
            binding.txtResultMessage.setText("Vitória do Dispositivo!");
            playSound(soundLose);
        }


        binding.txtResultMessage.setScaleX(0.5f);
        binding.txtResultMessage.setScaleY(0.5f);
        binding.txtResultMessage.setAlpha(0f);

        binding.txtResultMessage.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .alpha(1f)
                .setDuration(600)
                .withEndAction(() -> {
                    // Retorna ao tamanho normal suavemente
                    binding.txtResultMessage.animate()
                            .scaleX(1.0f)
                            .scaleY(1.0f)
                            .setDuration(200)
                            .withEndAction(() -> binding.btnPlayRound.setEnabled(true))
                            .start();
                })
                .start();
    }

    private void playSound(MediaPlayer player) {
        if (player != null) {
            if (player.isPlaying()) {
                player.seekTo(0);
            } else {
                player.start();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (soundWin != null && soundWin.isPlaying()) {
            soundWin.pause();
        }
        if (soundLose != null && soundLose.isPlaying()) {
            soundLose.pause();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundWin != null) {
            soundWin.release();
            soundWin = null;
        }
        if (soundLose != null) {
            soundLose.release();
            soundLose = null;
        }
    }
}