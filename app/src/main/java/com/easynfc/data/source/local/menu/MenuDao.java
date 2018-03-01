package com.easynfc.data.source.local.menu;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;
import com.easynfc.data.source.MenuDataSource;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */
@Dao
public interface MenuDao {

    @Query("SELECT * FROM Menu")
    List<Menu> getMainMenu();

    @Query("SELECT * FROM TagMenu")
    List<TagMenu> getTagsMenu();

    @Insert
    void insertMenus(Menu... menus);

    @Insert
    void insertTagsMenu(TagMenu... tagsMenu);

    @Query("SELECT COUNT(*) from Menu")
    int countMenus();

    @Query("DELETE FROM MENU")
    void deleteAllMenu();

    @Delete
    void delete(Menu menu);

    @Query("DELETE FROM TagMenu")
    void deleteAllTagMenu();
}
