package com.example.ken.materialdesginexample.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ken.materialdesginexample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String[] MENU = {"Categorias", "Mapa", "Busqueda"};

    public static final String PREF_NAME_FILE = "myPreferences";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mdrawerToggle;
    private View conteinerView;
    public DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstantceState;
    private ListView mOptionsList;
    private ArrayAdapter<String> mAdapter;


    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstantceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_fragment, container, false);
        mOptionsList = (ListView) view.findViewById(R.id.listview_navigation_top);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_drawer_list, R.id.item_title, MENU);
        mOptionsList.setAdapter(adapter);

        return view;
    }


    public void setUp(int FragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        this.conteinerView = getActivity().findViewById(FragmentId);
        this.mDrawerLayout = drawerLayout;
        this.mdrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mUserLearnedDrawer && !mFromSavedInstantceState) {
            mDrawerLayout.openDrawer(conteinerView);
        }
        mDrawerLayout.setDrawerListener(mdrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawerToggle.syncState();
            }
        });
    }


    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME_FILE, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaulValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME_FILE, context.MODE_PRIVATE);
        return preferences.getString(preferenceName, defaulValue);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOptionsList.setSelection(position);

    }
}
