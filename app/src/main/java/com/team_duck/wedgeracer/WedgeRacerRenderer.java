package com.team_duck.wedgeracer;

import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;

import tedge.Mesh;
import tedge.StaticRenderer;

/**
 * Created by Greg on 12/16/2015.
 */
public class WedgeRacerRenderer implements GLSurfaceView.Renderer
{
    private static float[] mOrthoMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
    private static float[] mProjMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // initialize surface
        GLES20.glClearColor(0.0f, 0.0f, 0.4f, 1.0f);
        StaticRenderer.CreateAndLinkShaderAndProgram();

        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        // load and stream resources
        if (WedgeRacerActivity.GameInstance != null)
        {
            WedgeRacerActivity.GameInstance.InitRenderResources();
        }
    }

    public void onDrawFrame(GL10 unused) {
        // render frame
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);

        float idMtx[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
        //Matrix.setIdentityM(idMtx, 0);
        StaticRenderer.UpdateCameraMatrix(idMtx);
        StaticRenderer.UpdateModelMatrix(idMtx);

        // frame
        if (WedgeRacerActivity.GameInstance != null)
        {
            // render game frame
            StaticRenderer.UpdateProjectionMatrix(mProjMatrix);
            WedgeRacerActivity.GameInstance.Render();

            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glDisable(GLES20.GL_DEPTH_TEST);
            GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            StaticRenderer.UpdateProjectionMatrix(mOrthoMatrix);

            WedgeRacerActivity.GameInstance.RenderOrtho();
            GLES20.glDisable(GLES20.GL_BLEND);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);

            // post-render update
            WedgeRacerActivity.GameInstance.Update();
        }
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float w = (float)width;
        float h = (float)height;
        if (h == 0.0f) h = 1.0f;
        float ratio = w/h;
        Matrix.perspectiveM(mProjMatrix, 0, 75.0f, ratio, 0.1f, 500.0f);
        //Matrix.orthoM(mOrthoMatrix, 0, -w/2.0f, w/2.0f, -h/2.0f, h/2.0f, -100.0f, 100.0f);
        Matrix.orthoM(mOrthoMatrix, 0, -1.0f, 1.0f, -1.0f, 1.0f, -100.0f, 1000.0f);
    }
}
