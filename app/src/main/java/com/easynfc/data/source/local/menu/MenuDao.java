package com.easynfc.data.source.local.menu;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.easynfc.data.Menu;
import com.easynfc.data.source.MenuDataSource;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */
@Dao
public interface MenuDao {

    @Query("SELECT * FROM Menu")
    List<Menu> getAll();

    @Query("SELECT COUNT(*) from Menu")
    int countMenus();

    @Insert
    void insertAll(Menu... menus);

    @Delete
    void delete(Menu menu);
}
