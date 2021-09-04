package dev.phonis.cannonliner.networking;

import net.minecraft.util.BlockPos;
import dev.phonis.cannonliner.state.CTLineManager;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CannonLinerClient {

    public static CannonLinerClient currentCannonLinerClient;

    private final String serverIP;
    private final int port;
    private final BlockPos blockPos;
    private final BlockPos middle;
    private final byte[] schemData;
    private final Socket socket;

    public CannonLinerClient(BlockPos blockPos, BlockPos middle, byte[] schemData) {
        this("localhost", 25566, blockPos, middle, schemData);
    }

    public CannonLinerClient(String serverIP, int port, BlockPos blockPos, BlockPos middle, byte[] schemData) {
        this.serverIP = serverIP;
        this.port = port;
        this.blockPos = blockPos;
        this.middle = middle;
        this.schemData = schemData;
        this.socket = new Socket();
    }

    public void close() {
        if (this.socket != null) {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        try {
            socket.setSoTimeout(0);
            socket.connect(new InetSocketAddress(this.serverIP, this.port));

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeInt(this.blockPos.getX());
            outputStream.writeInt(this.blockPos.getY());
            outputStream.writeInt(this.blockPos.getZ());
            outputStream.writeDouble(this.middle.getX());
            outputStream.writeDouble(this.middle.getY());
            outputStream.writeDouble(this.middle.getZ());
            outputStream.writeInt(this.schemData.length);
            outputStream.write(this.schemData);
            outputStream.flush();

            while (!Thread.currentThread().isInterrupted()) {
                this.readPacket(inputStream);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void readPacket(DataInputStream dataInputStream) throws IOException {
        int length = dataInputStream.readInt();
        byte[] packetData = new byte[length];

        dataInputStream.readFully(packetData);

        DataInputStream packetInputStream = new DataInputStream(new ByteArrayInputStream(packetData));

        this.handlePacket(packetInputStream.readByte(), packetInputStream);
    }

    private void handlePacket(byte packetID, DataInputStream packetInputStream) throws IOException {
        switch (packetID) {
            case Packets.newLinesID:
                CTNewLines ctNewLines = CTNewLines.fromBytes(packetInputStream);

                CTLineManager.instance.addLines(ctNewLines);

                break;
            case Packets.newArtifactsID:
                CTNewArtifacts ctNewArtifacts = CTNewArtifacts.fromBytes(packetInputStream);

                CTLineManager.instance.addArtifacts(ctNewArtifacts);

                break;
            default:
                System.out.println("Unknown packet ID");

                break;
        }
    }

}
