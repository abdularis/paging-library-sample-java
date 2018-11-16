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

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CheeseAdapter extends PagedListAdapter<Cheese, CheeseViewHolder> {

    public CheeseAdapter() {
        super(new DiffCb());
    }

    @NonNull
    @Override
    public CheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheese_item, parent, false);
        return new CheeseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheeseViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    private static class DiffCb extends DiffUtil.ItemCallback<Cheese> {
        @Override
        public boolean areItemsTheSame(Cheese oldItem, Cheese newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Cheese oldItem, Cheese newItem) {
            return oldItem == newItem;
        }
    }
}
