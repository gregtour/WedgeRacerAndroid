// Tedge or the Team Duck Game Engine (pronounced tedgengine) is a proprietary, open-source video game
// engine for the Internet, mobile phone, and desktop PC platforms.

package tedge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;


/**
 * Created by Greg on 12/16/2015.
 */
public class Mesh
{
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer indexBuffer;
    private int vertexCount;
    private int indexCount;
    private boolean useIndices;
    private float meshColor[] = {1.0f, 1.0f, 1.0f, 1.0f};

    public Mesh(float[] vertices)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        vertexCount = (vertices.length / 3);
        useIndices = false;
    }

    public Mesh(float[] vertices, float[] colors)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer cb = ByteBuffer.allocateDirect(colors.length * 4);
        cb.order(ByteOrder.nativeOrder());
        colorBuffer = cb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        vertexCount = (vertices.length / 3);
        useIndices = false;
    }

    public Mesh(float[] vertices, short[] indices)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ib = ByteBuffer.allocateDirect(indices.length * 2);
        ib.order(ByteOrder.nativeOrder());
        indexBuffer = ib.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        // create new buffer from index buffer

        vertexCount = (vertices.length / 3);
        indexCount = indices.length;
        useIndices = true;
    }

    public int Stride() { return 3*4; }
    public FloatBuffer VertexBuffer() { return vertexBuffer; }
    public FloatBuffer ColorBuffer() { return colorBuffer; }
    public int Count() { return vertexCount; }
    public void SetColor(float[] color) {
        assert(color.length == 4); for (int i = 0; i < 4; i++) meshColor[i] = color[i];
    }
    public float[] Color() {
        return meshColor;
    }
}
