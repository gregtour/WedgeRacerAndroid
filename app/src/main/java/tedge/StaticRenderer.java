// Tedge or the Team Duck Game Engine (pronounced tedgengine) is a proprietary, open-source video game
// engine for the Internet, mobile phone, and desktop PC platforms.

package tedge;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.team_duck.wedgeracer.WedgeRacerActivity;

/**
 * Created by Greg on 12/16/2015.
 */
public class StaticRenderer
{
    private static boolean loaded = false;
    private static int defVertShader;
    private static int defFragShader;
    private static int pDefaultShader;
    private static int colorVertShader;
    private static int colorFragShader;
    private static int pColorShader;

    private static float[] mModelMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
    private static float[] mCameraMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };
    private static float[] mProjectionMatrix = { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f };

    public static void UpdateCameraMatrix(float[] newMtx)
    {
        assert(newMtx.length == 16);
        for (int i = 0; i < 16; i++)
        {
            mCameraMatrix[i] = newMtx[i];
        }
    }

    public static void UpdateModelMatrix(float[] newMtx)
    {
        assert(newMtx.length == 16);
        for (int i = 0; i < 16; i++)
        {
            mModelMatrix[i] = newMtx[i];
        }
    }

    public static void UpdateProjectionMatrix(float[] newMtx)
    //(float w, float h, float fov)
    {
        assert(newMtx.length == 16);
        for (int i = 0; i < 16; i++)
        {
            mProjectionMatrix[i] = newMtx[i];
        }
    }

    public StaticRenderer()
    {
    }

    public static void CreateAndLinkShaderAndProgram()
    {
        // _WedgeRacer_ //

        // Default Shader
        String vert_shader_src = WedgeRacerActivity.DefaultVertexShader;
        String frag_shader_src = WedgeRacerActivity.DefaultFragmentShader;

        defVertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(defVertShader, vert_shader_src);
        GLES20.glCompileShader(defVertShader);

        defFragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(defFragShader, frag_shader_src);
        GLES20.glCompileShader(defFragShader);

        pDefaultShader = GLES20.glCreateProgram();

        GLES20.glAttachShader(pDefaultShader, defVertShader);
        GLES20.glAttachShader(pDefaultShader, defFragShader);
        GLES20.glLinkProgram(pDefaultShader);

        //String log = GLES20.glGetProgramInfoLog(pDefaultShader);
        //System.out.println(log);

        // Vertex Color Shader
        String vert_shader_color_src = WedgeRacerActivity.ColorVertexShader;
        String frag_shader_color_src = WedgeRacerActivity.ColorFragmentShader;

        colorVertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(colorVertShader, vert_shader_color_src);
        GLES20.glCompileShader(colorVertShader);

        colorFragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(colorFragShader, frag_shader_color_src);
        GLES20.glCompileShader(colorFragShader);

        pColorShader = GLES20.glCreateProgram();

        GLES20.glAttachShader(pColorShader, colorVertShader);
        GLES20.glAttachShader(pColorShader, colorFragShader);
        GLES20.glLinkProgram(pColorShader);

        //log = GLES20.glGetProgramInfoLog(pColorShader);
        //System.out.println(log);

        loaded = true;
    }

    public static void Render(Mesh mesh)
    {
        int pos;
        int color;
        int modelMtx;
        int cameraMtx;
        int projMtx;

        GLES20.glUseProgram(pDefaultShader);

        projMtx = GLES20.glGetUniformLocation(pDefaultShader, "uProjMatrix");
        GLES20.glUniformMatrix4fv(projMtx, 1, false, mProjectionMatrix, 0);

        cameraMtx = GLES20.glGetUniformLocation(pDefaultShader, "uCameraMatrix");
        GLES20.glUniformMatrix4fv(cameraMtx, 1, false, mCameraMatrix, 0);

        modelMtx = GLES20.glGetUniformLocation(pDefaultShader, "uModelMatrix");
        GLES20.glUniformMatrix4fv(modelMtx, 1, false, mModelMatrix, 0);

        pos = GLES20.glGetAttribLocation(pDefaultShader, "vPosition");
        GLES20.glEnableVertexAttribArray(pos);
        GLES20.glVertexAttribPointer(pos, 3, GLES20.GL_FLOAT, false, 3*4, mesh.VertexBuffer());

        color = GLES20.glGetUniformLocation(pDefaultShader, "vColor");

        GLES20.glUniform4fv(color, 1, mesh.Color(), 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mesh.Count());

        GLES20.glDisableVertexAttribArray(pos);
    }

    public static void RenderVertexColor(Mesh mesh)
    {
        int pos;
        int color;
        int modelMtx;
        int cameraMtx;
        int projMtx;

        GLES20.glUseProgram(pColorShader);

        projMtx = GLES20.glGetUniformLocation(pColorShader, "uProjMatrix");
        GLES20.glUniformMatrix4fv(projMtx, 1, false, mProjectionMatrix, 0);

        cameraMtx = GLES20.glGetUniformLocation(pColorShader, "uCameraMatrix");
        GLES20.glUniformMatrix4fv(cameraMtx, 1, false, mCameraMatrix, 0);

        modelMtx = GLES20.glGetUniformLocation(pColorShader, "uModelMatrix");
        GLES20.glUniformMatrix4fv(modelMtx, 1, false, mModelMatrix, 0);

        pos = GLES20.glGetAttribLocation(pColorShader, "vPosition");
        GLES20.glEnableVertexAttribArray(pos);
        GLES20.glVertexAttribPointer(pos, 3, GLES20.GL_FLOAT, false, 3 * 4, mesh.VertexBuffer());

        color = GLES20.glGetAttribLocation(pColorShader, "vVertColor");
        GLES20.glEnableVertexAttribArray(color);
        GLES20.glVertexAttribPointer(color, 4, GLES20.GL_FLOAT, false, 4 * 4, mesh.ColorBuffer());

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mesh.Count());

        GLES20.glDisableVertexAttribArray(pos);
        GLES20.glDisableVertexAttribArray(color);

    }

}
