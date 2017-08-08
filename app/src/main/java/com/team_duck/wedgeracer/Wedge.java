package com.team_duck.wedgeracer;

import android.opengl.Matrix;

import tedge.Mesh;
import tedge.StaticRenderer;

/**
 * Created by Greg on 12/17/2015.
 */
public class Wedge implements Entity
{
    private Mesh wedgeMesh;

    public float position[] = {0f, 0.001f, 0f};
    public float size = 2.0f;

    public static final float forwardSpeedFactor = 2.2f;
    public static final float backwardSpeedFactor = 1.0f;


    private float direction[] = {0f, 0.0f, 1f};
    private float thetaRotation;
    private float wedgeColor[] = {0.72f, 0.72f, 0.72f, 0.0f};
    private float transform[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
    private float geometry[] =
    {
        // back
        -1.0f, 0.0f, -1.5f,
        1.0f, 1.0f, -1.5f,
        1.0f, 0.0f, -1.5f,
        1.0f, 1.0f, -1.5f,
        -1.0f, 0.0f, -1.5f,
        -1.0f, 1.0f, -1.5f,
        // bottom
        -1.0f, 0.0f, -1.5f,
        1.0f, 0.0f, -1.5f,
        1.0f, 0.0f, 1.5f,
        1.0f, 0.0f, 1.5f,
        -1.0f, 0.0f, 1.5f,
        -1.0f, 0.0f, -1.5f,
        // side A
        -1.0f, 0.0f, -1.5f,
        -1.0f, 1.0f, -1.5f,
        -1.0f, 0.0f, 1.5f,
        // side B
        1.0f, 0.0f, -1.5f,
        1.0f, 1.0f, -1.5f,
        1.0f, 0.0f, 1.5f,
        // top
        1.0f, 1.0f, -1.5f,
        1.0f, 0.0f, 1.5f,
        -1.0f, 0.0f, 1.5f,
        -1.0f, 0.0f, 1.5f,
        -1.0f, 1.0f, -1.5f,
        1.0f, 1.0f, -1.5f,
    };

    private float colors[] =
    {
        // back
        0.62f, 0.62f, 0.62f, 1.0f,
        0.62f, 0.62f, 0.62f, 1.0f,
        0.62f, 0.62f, 0.62f, 1.0f,
        0.62f, 0.62f, 0.62f, 1.0f,
        0.62f, 0.62f, 0.62f, 1.0f,
        0.62f, 0.62f, 0.62f, 1.0f,
        // bottom
        0.2f, 0.2f, 0.2f, 1.0f,
        0.2f, 0.2f, 0.2f, 1.0f,
        0.2f, 0.2f, 0.2f, 1.0f,
        0.2f, 0.2f, 0.2f, 1.0f,
        0.2f, 0.2f, 0.2f, 1.0f,
        0.2f, 0.2f, 0.2f, 1.0f,
        // side A
        0.82f, 0.82f, 0.82f, 1.0f,
        0.82f, 0.82f, 0.82f, 1.0f,
        0.82f, 0.82f, 0.82f, 1.0f,
        // side B
        0.82f, 0.82f, 0.82f, 1.0f,
        0.82f, 0.82f, 0.82f, 1.0f,
        0.82f, 0.82f, 0.82f, 1.0f,
        // top
        0.72f, 0.72f, 0.72f, 1.0f,
        0.72f, 0.72f, 0.72f, 1.0f,
        0.72f, 0.72f, 0.72f, 1.0f,
        0.72f, 0.72f, 0.72f, 1.0f,
        0.72f, 0.72f, 0.72f, 1.0f,
        0.72f, 0.72f, 0.72f, 1.0f,
    };

    private boolean inputs[] = {false, false, false, false};

    public Wedge()
    {
        thetaRotation = 0.0f;
        //position[0] = 169f * 1.5f;
        //position[1] = 0f;
        //position[2] = 11f * 1.5f;

        TrackData.SetStartPos(position, TrackData.selectedTrack);

        size = 1.5f;
    }

    public void AcquireResources()
    {
        wedgeMesh = new Mesh(geometry, colors);
        wedgeMesh.SetColor(wedgeColor);
    }

    public void Render()
    {
        float temp[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
        float result[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};

        Matrix.setIdentityM(transform, 0);
        Matrix.setIdentityM(temp, 0);
        Matrix.translateM(result, 0, position[0], position[1], position[2]);
        Matrix.rotateM(temp, 0, thetaRotation, 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(transform, 0, result, 0, temp, 0);
        StaticRenderer.UpdateModelMatrix(transform);
        //StaticRenderer.Render(wedgeMesh);
        StaticRenderer.RenderVertexColor(wedgeMesh);
    }

    public void Update(double deltaTime) {
        float thrust = 0.0f;
        float rotation = 0.0f;

        if (inputs[2]) {
            thrust = 17.0f * forwardSpeedFactor;
        } else if (inputs[3]) {
            thrust = -5.0f * backwardSpeedFactor;
        } else {
            if (inputs[0]) {
                rotation = rotation + 120.0f;
                thrust = thrust + 5.0f * forwardSpeedFactor;
            }
            if (inputs[1]) {
                rotation = rotation - 120.0f;
                thrust = thrust + 5.0f * forwardSpeedFactor;
            }
            if (inputs[0] && inputs[1]) {
                rotation = 0.0f;
                thrust = thrust + 7.0f * forwardSpeedFactor;
            }
        }

        if (thrust > 0.0f || thrust < 0.0f)
        {
            float angleRads = thetaRotation / 180.0f * (float)Math.PI;
            position[0] += (float)Math.sin(angleRads) * thrust * deltaTime;
            position[2] += (float)Math.cos(angleRads) * thrust * deltaTime;
        }
        thetaRotation += rotation * deltaTime;
    }

    public void Inputs(boolean left, boolean right, boolean forward, boolean backward)
    {
        inputs[0] = left;
        inputs[1] = right;
        inputs[2] = forward;
        inputs[3] = backward;
    }

    public float[] GetPosition()
    {
        return position;
    }

    public float GetRotation()
    {
        return thetaRotation;
    }

    public float[] GetDirection(float rot)
    {
        float angleRads = rot / 180.0f * (float)Math.PI;
        direction[0] = (float)Math.sin(angleRads);
        direction[1] = 0.0f;
        direction[2] = (float)Math.cos(angleRads);

        return direction;
    }
}
