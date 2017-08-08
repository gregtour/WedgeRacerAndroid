package com.team_duck.wedgeracer;

import android.opengl.GLSurfaceView;
import android.content.Context;
import android.view.MotionEvent;
import android.view.KeyEvent;


/**
 * Created by Greg on 12/16/2015.
 */
public class OpenGLESSurfaceView extends GLSurfaceView
{
    private final WedgeRacerRenderer mRenderer;

    public OpenGLESSurfaceView(Context context)
    {
        super(context);
        // Create OpenGL ES 2 Context
        setEGLContextClientVersion(2);
        /* _WedgeRacer_ */
        mRenderer = new WedgeRacerRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); // sorry power monitor
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        /* _WedgeRacer_ */
        if (WedgeRacerActivity.GameInstance != null)
        {
            WedgeRacerActivity.GameInstance.TouchEvent(e, (float)getWidth(), (float)getHeight());
        }
        return true;
    }


}
