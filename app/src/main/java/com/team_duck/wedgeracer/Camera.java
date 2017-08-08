package com.team_duck.wedgeracer;

import android.opengl.Matrix;

/**
 * Created by Greg on 12/17/2015.
 */
public class Camera
{
    private float cameraMtx[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
    private float position[] = {0f, 7f, -1.0f};
    private float targetPosition[] = {0f, 0f, 0f};
    private final float targetDistance = 10.0f;
    private float rotation = 0.0f;
    private static final float epsRotation = 1.01f;

    public Camera()
    {
        rotation = 0.0f;
    }

    public void Update(double deltaTime, Wedge player)
    {
        float playerRotation = player.GetRotation();
        if (playerRotation > rotation + epsRotation)
        {
            rotation += deltaTime * 100.0f;
        }
        else if (playerRotation < rotation - epsRotation)
        {
            rotation -= deltaTime * 100.0f;
        }


        float[] pos = player.GetPosition();

        float[] direction = player.GetDirection(player.GetRotation());

        targetPosition[0] = pos[0] + direction[0] * 1.2f;
        targetPosition[1] = pos[1];
        targetPosition[2] = pos[2] + direction[2] * 1.2f;

        direction = player.GetDirection(rotation);

        position[0] = targetPosition[0] - direction[0] * 5.0f;
        position[1] = targetPosition[1] + 3.5f;
        position[2] = targetPosition[2] - direction[2] * 5.0f;
    }

    public float[] GetCameraMatrix()
    {
        Matrix.setLookAtM(cameraMtx, 0, position[0], position[1], position[2],
                targetPosition[0], targetPosition[1], targetPosition[2],
                0.0f, 1.0f, 0.0f);
        return cameraMtx;
    }
}

