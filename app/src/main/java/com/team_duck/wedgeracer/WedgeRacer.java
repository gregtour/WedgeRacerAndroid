package com.team_duck.wedgeracer;

import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.KeyEvent;

import tedge.Mesh;
import tedge.StaticRenderer;

/**
 * Created by Greg on 12/17/2015.
 */
public class WedgeRacer // WedgeRacerGame
{
    private long startTime;
    private long endTime;

    //private Mesh triangle;
    private Mesh leftSquare;
    private Mesh rightSquare;

    private boolean pressLeft;
    private boolean pressRight;
    private boolean pressForward;
    private boolean pressBackward;
    private boolean keyLeft;
    private boolean keyRight;

    //private final float triangleVertices[] = {0.0f, 1.0f, -2.0f, 0.866f, -0.5f, -2.0f, -0.866f, -0.5f, -2.0f};
    private final float leftVertices[] = { -1.0f, -1.0f, 0.0f,
                                           -0.6f, -1.0f, 0.0f,
                                           -1.0f, 1.0f, 0.0f,
                                           -0.6f, -1.0f, 0.0f,
                                           -0.6f, 1.0f, 0.0f,
                                           -1.0f, 1.0f, 0.0f };
    private final float rightVertices[] = { 1.0f, 1.0f, 0.0f,
                                            0.6f, -1.0f, 0.0f,
                                            1.0f, -1.0f, 0.0f,
                                            0.6f, 1.0f, 0.0f,
                                            0.6f, -1.0f, 0.0f,
                                            1.0f, 1.0f, 0.0f };
    private final float paneColors[] = {
            1.0f, 0.0f, 0.0f, 0.25f,
            0.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 0.25f,
            0.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f, 0.25f,
    };


    private Camera GameCamera;
    private Wedge Player;
    private Track RaceTrack;
    private Collision Collider;
    public WedgeRacer()
    {
        startTime = System.nanoTime();

        Collider = new Collision();
        GameCamera = new Camera();
        Player = new Wedge();
        RaceTrack = new Track(Collider);

        keyLeft = false;
        keyRight = false;
        pressLeft = false;
        pressRight = false;
        pressForward = false;
        pressBackward = false;
    }

    public void InitRenderResources()
    {
        float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
        //triangle = new Mesh(triangleVertices);
        leftSquare = new Mesh(leftVertices, paneColors);
        rightSquare = new Mesh(rightVertices, paneColors);
        leftSquare.SetColor(red);
        rightSquare.SetColor(red);

        // game resources
        Player.AcquireResources();
        RaceTrack.AcquireResources();
    }

    private final int maxTouchEvents = 8;
    private float touchPositionsX[] = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private float touchPositionsY[] = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private int touchId[] = {-1, -1, -1, -1, -1, -1, -1, -1};
    private boolean touchActive[] = {false, false, false, false, false, false, false, false};

/*
    public boolean KeyUp(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_Q:
                keyLeft = true;
                pressLeft = true;
                return true;
            case KeyEvent.KEYCODE_W:
                keyRight = true;
                pressRight = true;
                return true;
        }
        return false;
    }

    public boolean KeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_Q:
                keyLeft = true;
                pressLeft = true;
                return true;
            case KeyEvent.KEYCODE_W:
                keyRight = true;
                pressRight = true;
                return true;
        }
        return false;
    }
*/

    public void TouchEvent(MotionEvent e, float screenWidth, float screenHeight) {
        int pointerCount = e.getPointerCount();
        int itr, index;

        int maskedAction = e.getActionMasked();
        int actionIndex = e.getActionIndex();

        if (maskedAction == MotionEvent.ACTION_UP
            || maskedAction == MotionEvent.ACTION_POINTER_UP
            || maskedAction == MotionEvent.ACTION_CANCEL
            || maskedAction == MotionEvent.ACTION_OUTSIDE)
        {
            //for (itr = 0; itr < pointerCount; itr++)//
            {
                int pointerId = e.getPointerId(actionIndex);
                for (index = 0; index < maxTouchEvents; index++) {
                    if (touchId[index] == pointerId) {
                        touchActive[index] = false;
                        break;
                    }
                }
            }
        }

        if (maskedAction == MotionEvent.ACTION_DOWN
            || maskedAction == MotionEvent.ACTION_POINTER_DOWN
            || maskedAction == MotionEvent.ACTION_MOVE)
        {
            for (itr = 0; itr < pointerCount; itr++) {
                int pointerId = e.getPointerId(itr);
                for (index = 0; index < maxTouchEvents; index++) {
                    if (touchId[index] == pointerId) {
                        break;
                    }
                }

                if (index == maxTouchEvents) {
                    for (index = 0; index < maxTouchEvents; index++) {
                        if (touchActive[index] == false) {
                            touchId[index] = pointerId;
                            break;
                        }
                    }
                }

                if (index < maxTouchEvents) {
                    int pointerIndex = e.findPointerIndex(pointerId);
                    touchPositionsX[index] = (e.getX(itr) / screenWidth);
                    touchPositionsY[index] = (e.getY(itr) / screenHeight);
                    touchActive[index] = true;
                }
            }
        }

        pressLeft = keyLeft;
        pressRight = keyRight;
        pressForward = false;
        pressBackward = false;
        for (itr = 0; itr < maxTouchEvents; itr++) {
            if (touchActive[itr]) {
                if (touchPositionsX[itr] < 0.28f) pressLeft = true;
                else if (touchPositionsX[itr] > 0.72f) pressRight = true;
                else if (touchPositionsY[itr] < 0.2f) pressForward = true;
                else if (touchPositionsY[itr] > 0.84f) pressBackward = true;
            }
        }

        if (pressLeft && pressRight) {
            //performHapticFeedback();
        }
    }

    public void Render() {
        float cameraMtx[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
        //Matrix.setRotateM(cameraMtx, 0, rotation, 0.0f, 0.0f, 1.0f);

        // GameCamera.GetCameraMatrix();
        StaticRenderer.UpdateCameraMatrix(GameCamera.GetCameraMatrix());
        //StaticRenderer.UpdateCameraMatrix(cameraMtx);
        //StaticRenderer.Render(triangle);

        RaceTrack.Render();
        Player.Render();
    }

    public void RenderOrtho()
    {
        float identity[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
        Matrix.setIdentityM(identity, 0);
        StaticRenderer.UpdateCameraMatrix(identity);
        StaticRenderer.UpdateModelMatrix(identity);

        if (pressLeft) {
            StaticRenderer.RenderVertexColor(leftSquare);
        }
        if (pressRight) {
            StaticRenderer.RenderVertexColor(rightSquare);
        }
    }

    public void Update()
    {
        double deltaTime;
        endTime = System.nanoTime();
        deltaTime = (endTime - startTime) * 0.000000001f;

        RaceTrack.Update(deltaTime);
        //if (pressForward) {
//            Player.Inputs(true, true);
//        } else {
            Player.Inputs(pressLeft, pressRight, pressForward, pressBackward);
//        }
        Player.Update(deltaTime);
        Collider.UpdateCollision(Player);

        GameCamera.Update(deltaTime, Player);

        startTime = endTime;
    }
}
