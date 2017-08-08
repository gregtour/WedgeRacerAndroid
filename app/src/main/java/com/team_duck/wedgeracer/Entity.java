package com.team_duck.wedgeracer;

/**
 * Created by Greg on 12/17/2015.
 */
public interface Entity
{
    public void AcquireResources();
    public void Render();
    public void Update(double deltaTime);
    public float[] GetPosition();
}
