package com.easynfc;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easynfc.data.source.MenuRepository;
import com.easynfc.data.source.local.menu.MenuLocalDataSource;
import com.easynfc.menu.MenuFragment;
import com.easynfc.menu.MenuPresenter;
import com.easynfc.mytags.MyTagsFragment;
import com.easynfc.reader.ReaderFragment;
import com.easynfc.tagsmenu.TagsMenuFragment;
import com.easynfc.tagsmenu.TagsMenuPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateToMenu(false);
    }


    public void navigateToMenu(boolean animated) {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(MenuFragment.TAG);
        if (menuFragment == null) {
            menuFragment = MenuFragment.newInstance();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (animated) {
                transaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
            }

            transaction.replace(R.id.container, menuFragment)
                    .commit();
        }

        MenuLocalDataSource menuLocalDataSource = MenuLocalDataSource.getInstance(this);

        MenuRepository menuRepository = MenuRepository.getInstance(menuLocalDataSource);

        MenuPresenter presenter = new MenuPresenter(menuRepository, menuFragment);
    }

    public void navigateToTagsMenu() {
        TagsMenuFragment tagsMenuFragment = (TagsMenuFragment) getSupportFragmentManager().findFragmentByTag(TagsMenuFragment.TAG);
        if (tagsMenuFragment == null) {
            tagsMenuFragment = TagsMenuFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, tagsMenuFragment)
                    .commit();
        }

        MenuLocalDataSource menuLocalDataSource = MenuLocalDataSource.getInstance(this);

        MenuRepository menuRepository = MenuRepository.getInstance(menuLocalDataSource);

        TagsMenuPresenter presenter = new TagsMenuPresenter(menuRepository, tagsMenuFragment);
    }

    public void navigateToTagReader() {
        ReaderFragment readerFragment = (ReaderFragment) getSupportFragmentManager().findFragmentByTag(ReaderFragment.TAG);
        if (readerFragment == null) {
            readerFragment = ReaderFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, readerFragment)
                    .commit();
        }
    }

    public void navigateToMyTags() {
        MyTagsFragment myTagsFragment = (MyTagsFragment) getSupportFragmentManager().findFragmentByTag(MyTagsFragment.TAG);
        if (myTagsFragment == null) {
            myTagsFragment = MyTagsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left)
                    .replace(R.id.container, myTagsFragment)
                    .commit();
        }
    }
}
