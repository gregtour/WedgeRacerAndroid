package com.team_duck.wedgeracer;

import android.os.Bundle;

import android.opengl.GLSurfaceView;


public class OpenGLES20Activity extends WedgeRacerActivity /* _WedgeRacer_ */
{
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mGLView = new OpenGLESSurfaceView(this);
        setContentView(mGLView);
    }
}
