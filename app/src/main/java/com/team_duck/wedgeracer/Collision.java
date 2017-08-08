package com.team_duck.wedgeracer;

/**
 * Created by Greg on 12/19/2015.
 */
public class Collision
{
    private float[] walls;
    private int count;

    public Collision()
    {
        walls = new float[64];
        count = 0;
    }

    public void addWall(float x1, float z1, float x2, float z2)
    {
        if (count * 4 >= walls.length)
        {
            float [] newArray = new float[walls.length * 2];
            for (int i = 0; i < count*4; i++)
            {
                newArray[i] = walls[i];
            }
            walls = newArray;
        }

        walls[count*4 + 0] = x1;
        walls[count*4 + 1] = z1;
        walls[count*4 + 2] = x2;
        walls[count*4 + 3] = z2;
        count++;
    }


    public void UpdateCollision(Wedge racer)
    {
        float xpos = racer.position[0];
        float zpos = racer.position[2];
        float radius = racer.size;

        for (int j = 0; j < count; j++)
        {
            float x1 = walls[j*4+0];
            float z1 = walls[j*4+1];
            float x2 = walls[j*4+2];
            float z2 = walls[j*4+3];

            // find closest point on (x1z1,x2z2) to (xpos, zpos)
            // round to endcaps
            // check distance
            // replace if necessary

            float dx = (x2-x1);
            float dz = (z2-z1);
            float len = (float)Math.sqrt(dx*dx+dz*dz) + 0.01f;
            dx = dx / len;
            dz = dz / len;
            float lx = -dz;
            float lz = dx;
            float C = x1*lx + z1*lz; // plane constant

            float maxDist = (xpos * lx + zpos * lz) - C;
            float sign = 1.0f;
            if (maxDist < 0.0f) { sign = -1.0f; maxDist = -maxDist; }
            if (maxDist < radius)
            {
                // find range in s, t
                float x3 = xpos - sign * maxDist * lx;
                float z3 = zpos - sign * maxDist * lz;
                float ddx = x3 - x1;
                float ddz = z3 - z1;
                float s = ddx * dx + ddz * dz;
                if (s <= 0.0f) {
                    x3 = x1;
                    z3 = z1;
                } else if (s >= len) {
                    x3 = x2;
                    z3 = z2;
                }

                ddx = xpos - x3;
                ddz = zpos - z3;
                float realDistance = (float)Math.sqrt(ddx*ddx + ddz*ddz);
                if (realDistance < radius)
                {
                    xpos = x3 + sign * lx * radius;
                    zpos = z3 + sign * lz * radius;
                }
            }
        }
        racer.position[0] = xpos;
        racer.position[2] = zpos;
    }
}
