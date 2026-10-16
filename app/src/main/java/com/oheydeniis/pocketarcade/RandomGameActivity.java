package com.oheydeniis.pocketarcade;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.oheydeniis.pocketarcade.databinding.ActivityRandomGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGameActivity extends AppCompatActivity {

    private static final String TAG = "Lifecycle_RandomGame";

    private ActivityRandomGameBinding binding;
    private final List<ItemModel> itemList = new ArrayList<>();
    private RandomAdapter adapter;

    private boolean isGridMode = false;
    private MediaPlayer soundDraw;

    private final String[] categories = {"Filmes", "Comidas", "Lugares", "Desafios"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() executado");

        binding = ActivityRandomGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        soundDraw = MediaPlayer.create(this, R.raw.win_sound);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        binding.spCategory.setAdapter(spinnerAdapter);

        adapter = new RandomAdapter(itemList, this::deleteItem);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));
        binding.rvItems.setAdapter(adapter);

        populateInitialData();
        binding.btnAdd.setOnClickListener(v -> addItem());
        binding.btnToggleLayout.setOnClickListener(v -> toggleLayoutManager());
        binding.btnDraw.setOnClickListener(v -> drawItemByCategory());
    }

    private void populateInitialData() {
        itemList.add(new ItemModel("Matrix", "Filmes"));
        itemList.add(new ItemModel("Pizza", "Comidas"));
        itemList.add(new ItemModel("Paris", "Lugares"));
        itemList.add(new ItemModel("10 Flexões", "Desafios"));
        adapter.notifyDataSetChanged();
    }


    private void addItem() {
        String word = binding.edtItemValue.getText().toString().trim();
        String category = binding.spCategory.getSelectedItem().toString();

        if (word.isEmpty()) {
            Toast.makeText(this, "Digite um valor para o item!", Toast.LENGTH_SHORT).show();
            return;
        }

        ItemModel newItem = new ItemModel(word, category);
        itemList.add(newItem);

        int insertedPosition = itemList.size() - 1;
        adapter.notifyItemInserted(insertedPosition);
        binding.rvItems.scrollToPosition(insertedPosition);

        binding.edtItemValue.setText("");
    }

    private void deleteItem(int position) {
        if (position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(this, "Item removido!", Toast.LENGTH_SHORT).show();
        }
    }


    private void toggleLayoutManager() {
        isGridMode = !isGridMode;
        if (isGridMode) {
            binding.rvItems.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            binding.rvItems.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void drawItemByCategory() {
        String selectedCategory = binding.spCategory.getSelectedItem().toString();
        List<ItemModel> filteredList = new ArrayList<>();

        for (ItemModel item : itemList) {
            if (item.getCategory().equalsIgnoreCase(selectedCategory)) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Nenhum item cadastrado para a categoria: " + selectedCategory, Toast.LENGTH_SHORT).show();
            return;
        }

        if (soundDraw != null) {
            if (soundDraw.isPlaying()) soundDraw.seekTo(0);
            soundDraw.start();
        }

        ItemModel winner = filteredList.get(new Random().nextInt(filteredList.size()));


        binding.rvItems.animate().alpha(0.3f).setDuration(150).withEndAction(() -> {
            binding.rvItems.animate().alpha(1.0f).setDuration(250).start();
            Toast.makeText(this, "🎉 Sorteado: " + winner.getWord() + " (" + winner.getCategory() + ")", Toast.LENGTH_LONG).show();
        }).start();
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
    protected void onPause() {
        super.onPause();
        if (soundDraw != null && soundDraw.isPlaying()) {
            soundDraw.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executado");
        if (soundDraw != null) {
            soundDraw.release();
            soundDraw = null;
        }
    }


    private void logoutSession() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}