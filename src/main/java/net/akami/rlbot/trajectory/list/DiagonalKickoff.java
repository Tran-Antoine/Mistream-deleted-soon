package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.trajectory.OutputSequence;
import net.akami.rlbot.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class DiagonalKickoff extends KickoffOutputSequence {

    public DiagonalKickoff() {
        super(new Vector3f(-2048, -2560, 17.01));
    }

    @Override
    protected List<OutputSequence> loadChildren() {
        return Collections.singletonList(new ForwardMovement(50, 1));
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        return packet.gameInfo().isKickoffPause() && queue.size() == 0
                && super.isSuitable(packet, delay, queue);
    }
}
