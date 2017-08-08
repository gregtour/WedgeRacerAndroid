package com.team_duck.wedgeracer;

import android.opengl.Matrix;

import tedge.Mesh;
import tedge.StaticRenderer;

/**
 * Created by Greg on 12/17/2015.
 */
public class Track implements Entity
{
    private Mesh groundMesh;
    private Mesh trackMesh;
    private float transform[] = {1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,0f,0f,1f};
    private final float [] position = {0f, 0f, 0f};
    private float [] groundColor = {
            0.4f, 0.2f, 0.3f, 1.0f
    };
    private float [] trackColor = {
      0.5f, 0.5f, 0.5f, 1.0f
    };
    private float [] groundGeometry = {
        -100.0f, 0.0f, -100.0f,
        -100.0f, 0.0f, 100.0f,
        100.0f, 0.0f, 100.0f,
        100.0f, 0.0f, 100.0f,
        100.0f, 0.0f, -100.0f,
        -100.0f, 0.0f, -100.0f,
    };

    private float [] trackGeometry;
    private float [] trackNormalColors;

    public Track(Collision col)
    {
        int[] track = TrackData.GetTrack(TrackData.selectedTrack);
        int triangleCount = track.length / 2;

        trackGeometry = new float[triangleCount * 3 * 3];
        trackNormalColors = new float[triangleCount * 3 *4];

        int tri = 0;
        for (int i = 0; i+3 < track.length && tri < triangleCount; i += 4)
        {
            float x1 = track[i] * TrackData.scaleFactor;
            float z1 = track[i+1] * TrackData.scaleFactor;
            float x2 = track[i+2] * TrackData.scaleFactor;
            float z2 = track[i+3] * TrackData.scaleFactor;
            col.addWall(x1, z1, x2, z2);

            float y1 = 0.0f;
            float y2 = 4.0f;

            float gamma = (float)Math.abs(Math.sin(Math.atan2(x2-x1,z2-z1))) * 0.25f + 0.30f;
            for (int j = 0; j < 24; j++)
            {
                if (j%4 == 3)
                    trackNormalColors[tri*12 + j] = 1.0f;
                else
                    trackNormalColors[tri*12 + j] = gamma;
            }

/*1:x*/     trackGeometry[tri*9 + 0] = x1;
/*1:y*/     trackGeometry[tri*9 + 1] = y1;
/*1:z*/     trackGeometry[tri*9 + 2] = z1;
/*2:x*/     trackGeometry[tri*9 + 3] = x1;
/*2:y*/     trackGeometry[tri*9 + 4] = y2;
/*2:z*/     trackGeometry[tri*9 + 5] = z1;
/*3:x*/     trackGeometry[tri*9 + 6] = x2;
/*3:y*/     trackGeometry[tri*9 + 7] = y2;
/*3:z*/     trackGeometry[tri*9 + 8] = z2;
            tri++;

/*1:x*/     trackGeometry[tri*9 + 0] = x2;
/*1:y*/     trackGeometry[tri*9 + 1] = y2;
/*1:z*/     trackGeometry[tri*9 + 2] = z2;
/*2:x*/     trackGeometry[tri*9 + 3] = x2;
/*2:y*/     trackGeometry[tri*9 + 4] = y1;
/*2:z*/     trackGeometry[tri*9 + 5] = z2;
/*3:x*/     trackGeometry[tri*9 + 6] = x1;
/*3:y*/     trackGeometry[tri*9 + 7] = y1;
/*3:z*/     trackGeometry[tri*9 + 8] = z1;
            tri++;
        }
    }

    public void AcquireResources()
    {
        groundMesh = new Mesh(groundGeometry);
        groundMesh.SetColor(groundColor);

        trackMesh = new Mesh(trackGeometry, trackNormalColors);
        trackMesh.SetColor(trackColor);
        //triangle = new Mesh(triangleVertices);
    }

    public void Render()
    {
        //Matrix.setIdentityM(transform, 0);
        Matrix.scaleM(transform, 0, 10.0f, 10.0f, 10.0f);
        StaticRenderer.UpdateModelMatrix(transform);
        StaticRenderer.Render(groundMesh);

        Matrix.setIdentityM(transform, 0);
        StaticRenderer.UpdateModelMatrix(transform);
        StaticRenderer.RenderVertexColor(trackMesh);
    }

    public void Update(double deltaTime)
    {
    }

    public float[] GetPosition()
    {
        return position;
    }
}

