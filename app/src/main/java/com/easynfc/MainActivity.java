package com.easynfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.easynfc.menu.MenuFragment;
import com.easynfc.mytags.MyTagsFragment;
import com.easynfc.tagreader.TagReaderFragment;
import com.easynfc.tagwriter.TagWriterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateToMenu();
    }



    public void navigateToMenu() {
        MenuFragment menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentByTag(MenuFragment.TAG);
        if (menuFragment == null) {
            menuFragment = MenuFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, menuFragment)
                    .commit();
        }
    }

    public void navigateToTagWriter() {
        TagWriterFragment tagWriterFragment = (TagWriterFragment) getSupportFragmentManager().findFragmentByTag(TagWriterFragment.TAG);
        if (tagWriterFragment == null) {
            tagWriterFragment = TagWriterFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, tagWriterFragment)
                    .commit();
        }
    }

    public void navigateToTagReader() {
        TagReaderFragment tagReaderFragment = (TagReaderFragment) getSupportFragmentManager().findFragmentByTag(TagReaderFragment.TAG);
        if (tagReaderFragment == null) {
            tagReaderFragment = TagReaderFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, tagReaderFragment)
                    .commit();
        }
    }

    public void navigateToMyTags() {
        MyTagsFragment myTagsFragment = (MyTagsFragment) getSupportFragmentManager().findFragmentByTag(MyTagsFragment.TAG);
        if (myTagsFragment == null) {
            myTagsFragment = MyTagsFragment.newInstance();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, myTagsFragment)
                    .commit();
        }
    }
}
