package com.team_duck.wedgeracer;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * Created by Greg on 12/16/2015.
 * Copyright Team Duck.
 */
public class WedgeRacerActivity extends Activity
{
    public static String DefaultVertexShader;
    public static String DefaultFragmentShader;
    public static String ColorVertexShader;
    public static String ColorFragmentShader;
    public static WedgeRacer GameInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        GameInstance = new WedgeRacer();

        Initialize();
    }

    public void Initialize()
    {
        DefaultVertexShader = getText(R.string.static_vertex_shader).toString();
        DefaultFragmentShader = getText(R.string.static_frag_shader).toString();
        ColorVertexShader = getText(R.string.static_vertex_shader_color).toString();
        ColorFragmentShader = getText(R.string.static_frag_shader_color).toString();
    }

/*
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        // _WedgeRacer_ //
        if (WedgeRacerActivity.GameInstance != null)
        {
            if (WedgeRacerActivity.GameInstance.KeyUp(keyCode, event))
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        // _WedgeRacer_ //
        if (WedgeRacerActivity.GameInstance != null)
        {
            if (WedgeRacerActivity.GameInstance.KeyDown(keyCode, event))
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
*/
}



