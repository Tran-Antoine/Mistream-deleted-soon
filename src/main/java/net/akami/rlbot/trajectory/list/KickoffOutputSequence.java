package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.trajectory.BotController;
import net.akami.rlbot.trajectory.FragmentedOutputSequence;
import net.akami.rlbot.trajectory.OutputSequence;
import net.akami.rlbot.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Queue;

public abstract class KickoffOutputSequence extends FragmentedOutputSequence {

    protected Vector3f location;

    public KickoffOutputSequence(Vector3f location, BotController botController) {
        super(botController);
        this.location = location;
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        Vector3f convertedLocation = new Vector3f(packet.players(0).physics().location());
        return convertedLocation.absoluteEquals(this.location);
    }
}
