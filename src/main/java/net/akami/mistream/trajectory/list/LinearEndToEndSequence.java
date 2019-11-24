package net.akami.mistream.trajectory.list;

import net.akami.mistream.gamedata.CarInfoProvider;
import net.akami.mistream.trajectory.OutputSequence;
import net.akami.mistream.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Queue;
import java.util.function.Function;

public class LinearEndToEndSequence extends EndToEndSequence {

    private float speed;

    protected LinearEndToEndSequence(Vector3f end, CarInfoProvider locProvider, float speed) {
        super(end, locProvider);
        this.speed = speed;
    }

    @Override
    protected Function<Integer, Float> getBoostFunction() {
        return (i) -> speed;
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        return false;
    }
}
