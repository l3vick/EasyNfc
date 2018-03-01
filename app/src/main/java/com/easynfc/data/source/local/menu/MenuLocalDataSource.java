package com.easynfc.data.source.local.menu;

import android.content.Context;

import com.easynfc.data.Menu;
import com.easynfc.data.TagMenu;
import com.easynfc.data.source.MenuDataSource;
import com.easynfc.data.source.local.EasyNfcDatabase;

import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
 */

public class MenuLocalDataSource implements MenuDataSource {

    private static final String TAG = "MenuLocalDataSource";

    private static MenuLocalDataSource INSTANCE;
    private static MenuDao menuDao;

    private MenuLocalDataSource() {

    }

    public static MenuLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MenuLocalDataSource();
            menuDao = EasyNfcDatabase.getAppDatabase(context).menuDao();
        }
        return INSTANCE;
    }


    @Override
    public void getMenu(final LoadMenuCallback callback) {

        final List<Menu> menus = menuDao.getMainMenu();
        if (menus.size() > 0) {
            callback.onMenusLoaded(menus);
        } else {
            callback.onMenusLoaded(addMenuData());
        }
    }

    @Override
    public void getTagMenu(LoadTagsMenuCallback callback) {
        //menuDao.deleteAllTagMenu();
        final List<TagMenu> tagsMenu = menuDao.getTagsMenu();
        if (tagsMenu.size() > 0) {
            callback.onTagsMenuLoaded(tagsMenu);
        } else {
            callback.onTagsMenuLoaded(addTagsMenuData());
        }
    }


    private void addAllMenu(Menu... menu) {
        menuDao.insertMenus(menu);
    }

    private void addAllTagMenu(TagMenu... tagsMenu) {
        menuDao.insertTagsMenu(tagsMenu);
    }



    private List<Menu> addMenuData() {
        Menu writeMenu = new Menu("Write/", "write & save your favorites tags");
        Menu readMenu = new Menu("Read/", "read tag content");
        Menu myTagsMenu = new Menu("My Tags/", "write, emulate & update your tags");
        addAllMenu(writeMenu, readMenu, myTagsMenu);
        return menuDao.getMainMenu();
    }

    private List<TagMenu> addTagsMenuData() {
        TagMenu simpleTextTag = new TagMenu("Simple-Text","ic_text_eb_superlight");
        TagMenu urlTag = new TagMenu("Url","ic_url_eb_superlight");
        TagMenu smsTag = new TagMenu("Sms","ic_sms_eb_superlight");
        TagMenu phoneTag = new TagMenu("Phone","ic_phone_eb_superlight");
        TagMenu launcherTag = new TagMenu("App-Launcher","ic_aar_eb_superlight");
        TagMenu locationTag = new TagMenu("Location","ic_location_eb_superlight");
        TagMenu wifiTag = new TagMenu("Wi-Fi","ic_wifi_eb_superlight");
        TagMenu emailTag = new TagMenu("Email","ic_email_eb_superlight");
        TagMenu ndefTag = new TagMenu("NDEF-Format","ic_format_eb_superlight");
        TagMenu lockTag = new TagMenu("Lock","ic_lock_eb_superlight");
        addAllTagMenu(simpleTextTag,urlTag, smsTag, phoneTag, launcherTag, locationTag, wifiTag, emailTag, ndefTag, lockTag);
        return menuDao.getTagsMenu();
    }
}
