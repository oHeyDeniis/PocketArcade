package com.oheydeniis.pocketarcade;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oheydeniis.pocketarcade.databinding.ActivityRockPaperScissorsBinding;

import java.util.Random;

public class RockPaperScissorsActivity extends AppCompatActivity {

    private ActivityRockPaperScissorsBinding binding;

    // 0: Pedra, 1: Papel, 2: Tesoura
    private final String[] OPTIONS = {"Pedra", "Papel", "Tesoura"};
    private final Random random = new Random();

    private int playerWins = 0;
    private int cpuWins = 0;
    private boolean isGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRockPaperScissorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRock.setOnClickListener(v -> handlePlayerMove(0, v));
        binding.btnPaper.setOnClickListener(v -> handlePlayerMove(1, v));
        binding.btnScissors.setOnClickListener(v -> handlePlayerMove(2, v));


        binding.btnNewGame.setOnClickListener(v -> resetGame());
    }

    private void handlePlayerMove(int playerChoice, View clickedView) {
        if (isGameOver) {
            Toast.makeText(this, "A partida acabou! Clique em Nova partida.", Toast.LENGTH_SHORT).show();
            return;
        }

        clickedView.animate()
                .scaleX(0.85f)
                .scaleY(0.85f)
                .setDuration(100)
                .withEndAction(() -> clickedView.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start())
                .start();

        // Escolha aleatória do dispositivo (CPU)
        int cpuChoice = random.nextInt(3);

        String playerMoveStr = OPTIONS[playerChoice];
        String cpuMoveStr = OPTIONS[cpuChoice];

        binding.txtMovesComparison.setText("Você escolheu " + playerMoveStr + " × Dispositivo escolheu " + cpuMoveStr);

        // Lógica do Vencedor da Rodada
        if (playerChoice == cpuChoice) {
            binding.txtRoundResult.setText("Empate!");
        } else if ((playerChoice == 0 && cpuChoice == 2) ||  // Pedra ganha de Tesoura
                (playerChoice == 1 && cpuChoice == 0) ||  // Papel ganha de Pedra
                (playerChoice == 2 && cpuChoice == 1)) {  // Tesoura ganha de Papel
            playerWins++;
            binding.txtRoundResult.setText("Você venceu a rodada!");
        } else {
            cpuWins++;
            binding.txtRoundResult.setText("Dispositivo venceu a rodada!");
        }


        binding.txtRoundResult.setAlpha(0f);
        binding.txtRoundResult.animate().alpha(1f).setDuration(300).start();

        updateScore();
    }

    private void updateScore() {
        binding.txtScoreBoard.setText("Placar: Você " + playerWins + " x " + cpuWins + " Dispositivo");


        if (playerWins == 2) {
            isGameOver = true;
            binding.txtRoundResult.setText("🏆 VOCÊ VENCEU A PARTIDA!");
            binding.txtGameDescription.setText("Parabéns! Você fez 2 pontos primeiro.");
        } else if (cpuWins == 2) {
            isGameOver = true;
            binding.txtRoundResult.setText("❌ DISPOSITIVO VENCEU A PARTIDA!");
            binding.txtGameDescription.setText("O dispositivo alcançou 2 pontos primeiro.");
        }
    }

    private void resetGame() {
        playerWins = 0;
        cpuWins = 0;
        isGameOver = false;

        binding.txtRoundResult.setText("Vitória, derrota ou empate");
        binding.txtMovesComparison.setText("Jogador × dispositivo");
        binding.txtScoreBoard.setText("Placar: Você 0 x 0 Dispositivo");
        binding.txtGameDescription.setText("Vence quem alcançar duas vitórias primeiro.");

        Toast.makeText(this, "Placar zerado!", Toast.LENGTH_SHORT).show();
    }
}