package com.oheydeniis.pocketarcade;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivityScannerGameBinding;

import java.text.Normalizer;

public class ScannerGameActivity extends AppCompatActivity {

    private ActivityScannerGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAnalyze.setOnClickListener(v -> analyzeText());
        
        binding.btnClear.setOnClickListener(v -> clearInputs());
    }

    private void analyzeText() {
        String input = binding.edtMessage.getText() != null ? binding.edtMessage.getText().toString() : "";
        
        if (input.trim().length() < 3) {
            Toast.makeText(this, "A mensagem deve conter pelo menos 3 caracteres!", Toast.LENGTH_SHORT).show();
            binding.resultPanel.setVisibility(View.GONE);
            return;
        }

        String textNoSpaces = input.replaceAll("\\s+", "");
        int charCount = textNoSpaces.length();

      
        String[] words = input.trim().split("\\s+");
        int wordCount = words.length;
        int vowelCount = countVowels(input);
        String reversedText = new StringBuilder(input).reverse().toString();
        boolean isPalindrome = checkIsPalindrome(input);
        
        binding.txtResultCharCount.setText(String.valueOf(charCount));
        binding.txtResultWordCount.setText(String.valueOf(wordCount));
        binding.txtResultVowelCount.setText(String.valueOf(vowelCount));
        binding.txtResultReversedText.setText(reversedText);
        binding.txtResultIsPalindrome.setText(isPalindrome ? "Sim" : "Nao");

      
        if (binding.resultPanel.getVisibility() != View.VISIBLE) {
            binding.resultPanel.setVisibility(View.VISIBLE);
            binding.resultPanel.setAlpha(0f);
            binding.resultPanel.setTranslationY(50f);

            binding.resultPanel.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(400)
                    .start();
        }
    }

    private int countVowels(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();

        int count = 0;
        for (char c : normalized.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    private boolean checkIsPalindrome(String text) {
        
        String cleanText = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase();

        String reversedCleanText = new StringBuilder(cleanText).reverse().toString();
        return cleanText.equals(reversedCleanText);
    }

    private void clearInputs() {
        binding.edtMessage.setText("");
        if (binding.resultPanel.getVisibility() == View.VISIBLE) {
            binding.resultPanel.animate()
                    .alpha(0f)
                    .translationY(30f)
                    .setDuration(250)
                    .withEndAction(() -> binding.resultPanel.setVisibility(View.GONE))
                    .start();
        }
    }
}