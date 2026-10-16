package com.oheydeniis.pocketarcade;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.mainEnterButton.setOnClickListener(view -> {
            binding.mainEnterButton.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            binding.mainEnterButton.animate()
                                    .scaleX(1.0f)
                                    .scaleY(1.0f)
                                    .setDuration(100)
                                    .start();
                        }
                    })
                    .start();
            String text = binding.mainNickInput.getText().toString().trim();
            if(text.isEmpty() || text.length() < 3) {
                Toast.makeText(this, "Insira um apelido valido", Toast.LENGTH_SHORT).show();
                binding.mainNickInput.setError("Insira um apelido valido");
                return;
            }
            Intent intent = new Intent(this, LoggedActivity.class);
            intent.putExtra("nick", text);
            startActivity(intent);
        });
    }
}