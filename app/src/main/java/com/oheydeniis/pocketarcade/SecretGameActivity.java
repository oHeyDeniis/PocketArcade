package com.oheydeniis.pocketarcade;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivitySecretGameBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecretGameActivity extends AppCompatActivity {

    private ActivitySecretGameBinding binding;

    private String secretCode = "";
    private int attempts = 0;
    private final int MAX_ATTEMPTS = 8;
    private boolean isGameOver = false;


    private long startTimeMs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecretGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startNewGame();

        binding.btnTry.setOnClickListener(v -> processAttempt());
        binding.btnNewGame.setOnClickListener(v -> startNewGame());
    }

    private void startNewGame() {
        secretCode = generateSecretCode();
        attempts = 0;
        isGameOver = false;

        startTimeMs = System.currentTimeMillis();

        binding.historyContainer.removeAllViews();
        binding.edtCodeInput.setText("");
        binding.edtCodeInput.setEnabled(true);
        binding.btnTry.setEnabled(true);
        binding.conclusionCard.setVisibility(View.GONE);
        binding.txtSecretDots.setText("• • • •");
        binding.txtAttemptsCount.setText("Tentativas: 0/" + MAX_ATTEMPTS);
    }

    private String generateSecretCode() {
        List<Integer> digits = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            digits.add(i);
        }
        Collections.shuffle(digits);

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(digits.get(i));
        }
        return code.toString();
    }

    private void processAttempt() {
        if (isGameOver) return;

        String input = binding.edtCodeInput.getText().toString().trim();

        if (!isValidInput(input)) {
            return;
        }

        attempts++;
        binding.txtAttemptsCount.setText("Tentativas: " + attempts + "/" + MAX_ATTEMPTS);


        addColoredBoxesToHistory(input);

        binding.edtCodeInput.setText("");


        if (input.equals(secretCode)) {
            endGame(true);
            return;
        }


        if (attempts >= MAX_ATTEMPTS) {
            endGame(false);
        }
    }

    private boolean isValidInput(String input) {
        if (input.length() != 4) {
            Toast.makeText(this, "Digite exatamente 4 números!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Set<Character> uniqueDigits = new HashSet<>();
        for (char c : input.toCharArray()) {
            uniqueDigits.add(c);
        }

        if (uniqueDigits.size() < 4) {
            Toast.makeText(this, "Os quatro números não podem se repetir!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void addColoredBoxesToHistory(String guess) {
        View rowView = LayoutInflater.from(this).inflate(R.layout.item_secret_list, binding.historyContainer, false);

        int[] boxIds = {R.id.box1, R.id.box2, R.id.box3, R.id.box4};
        int[] digitIds = {R.id.txtDigit1, R.id.txtDigit2, R.id.txtDigit3, R.id.txtDigit4};
        int[] labelIds = {R.id.txtLabel1, R.id.txtLabel2, R.id.txtLabel3, R.id.txtLabel4};

        for (int i = 0; i < 4; i++) {
            char c = guess.charAt(i);
            LinearLayout box = rowView.findViewById(boxIds[i]);
            TextView txtDigit = rowView.findViewById(digitIds[i]);
            TextView txtLabel = rowView.findViewById(labelIds[i]);

            txtDigit.setText(String.valueOf(c));

            if (c == secretCode.charAt(i)) {

                box.setBackgroundColor(Color.parseColor("#86EFAC"));
                txtLabel.setText("Posição certa");
            } else if (secretCode.indexOf(c) != -1) {

                box.setBackgroundColor(Color.parseColor("#FDE047"));
                txtLabel.setText("Posição errada");
            } else {

                box.setBackgroundColor(Color.parseColor("#94A3B8"));
                txtLabel.setText("Não existe");
            }
        }


        rowView.setAlpha(0f);
        binding.historyContainer.addView(rowView, 0);
        rowView.animate().alpha(1f).setDuration(400).start();
    }

    private void endGame(boolean isWin) {
        isGameOver = true;


        long endTimeMs = System.currentTimeMillis();
        long elapsedSeconds = (endTimeMs - startTimeMs) / 1000;

        binding.edtCodeInput.setEnabled(false);
        binding.btnTry.setEnabled(false);
        binding.txtSecretDots.setText(secretCode);


        if (isWin) {
            binding.txtConclusionTitle.setText("🏆 Vitória!");
            binding.txtConclusionDetails.setText("Você acertou o código em " + attempts + " tentativas e " + elapsedSeconds + " segundos!");
        } else {
            binding.txtConclusionTitle.setText("❌ Derrota!");
            binding.txtConclusionDetails.setText("Esgotou as 8 tentativas. O código correto era " + secretCode + ". Tempo: " + elapsedSeconds + "s.");
        }


        binding.conclusionCard.setVisibility(View.VISIBLE);
        binding.conclusionCard.setAlpha(0f);
        binding.conclusionCard.setTranslationY(40f);
        binding.conclusionCard.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .start();
    }
}