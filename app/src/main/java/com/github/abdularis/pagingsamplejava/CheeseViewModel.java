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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

public class CheeseViewModel extends AndroidViewModel {

    private CheeseDao dao;

    public CheeseViewModel(@NonNull Application application) {
        super(application);
        dao = CheeseDb.getInstance(application).getCheeseDao();
    }

    public LiveData<PagedList<Cheese>> getAllCheese() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .build();

        return new LivePagedListBuilder(dao.allCheeseByName(), config).build();
    }

    public void insert(String text) {
        Executors.newSingleThreadExecutor().execute(() -> {
            dao.insert(new Cheese(0, text));
        });
    }

    public void remove(Cheese cheese) {
        Executors.newSingleThreadExecutor().execute(() -> {
            dao.delete(cheese);
        });
    }
}
