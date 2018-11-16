/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.abdularis.pagingsamplejava;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheeseViewModel mViewModel;
    private TextView inputText;
    private RecyclerView cheeseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(CheeseViewModel.class);

        inputText = findViewById(R.id.inputText);

        CheeseAdapter adapter = new CheeseAdapter();
        cheeseList = findViewById(R.id.cheeseList);
        cheeseList.setAdapter(adapter);
        cheeseList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mViewModel.getAllCheese().observe(this, adapter::submitList);

        initAddButtonListener();
        initSwipeToDelete();
    }

    private void addCheese() {
        String text = inputText.getText().toString();
        if (!text.isEmpty()) {
            mViewModel.insert(text);
            inputText.setText("");
        }
    }

    private void initSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                CheeseViewHolder cvh = (CheeseViewHolder) viewHolder;
                if (cvh.getCheese() != null) {
                    mViewModel.remove(cvh.getCheese());
                }
            }
        }).attachToRecyclerView(cheeseList);
    }

    private void initAddButtonListener() {
        View addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> addCheese());

        // when the user taps the "Done" button in the on screen keyboard, save the item.
        inputText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addCheese();
                return true;
            }
            return false;
        });

        // When the user clicks on the button, or presses enter, save the item.
        inputText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addCheese();
                return true;
            }
            return false;
        });
    }

}
