package phonis.cannontracer.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CTVec3 implements CTSerializable {

    public double x;
    public double y;
    public double z;

    public CTVec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CTVec3 plus(CTVec3 other) {
        return new CTVec3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    @Override
    public void toBytes(DataOutputStream dos) throws IOException {
        dos.writeFloat((float) this.x);
        dos.writeFloat((float) this.y);
        dos.writeFloat((float) this.z);
    }

    public static CTVec3 fromBytes(DataInputStream dis) throws IOException {
        return new CTVec3(
            dis.readFloat(),
            dis.readFloat(),
            dis.readFloat()
        );
    }

}
