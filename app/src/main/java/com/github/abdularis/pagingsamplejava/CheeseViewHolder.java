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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CheeseViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private Cheese mCheese;

    public CheeseViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
    }

    void bindTo(Cheese cheese) {
        mCheese = cheese;
        if (cheese != null) {
            name.setText(cheese.getName());
        }
    }

    public Cheese getCheese() {
        return mCheese;
    }
}
