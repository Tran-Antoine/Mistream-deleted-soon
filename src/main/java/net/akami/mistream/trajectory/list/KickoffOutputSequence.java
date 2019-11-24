package net.akami.mistream.trajectory.list;

import net.akami.mistream.gamedata.CarInfoProvider;
import net.akami.mistream.trajectory.FragmentedOutputSequence;
import net.akami.mistream.trajectory.BotController;
import net.akami.mistream.trajectory.OutputSequence;
import net.akami.mistream.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Queue;

public abstract class KickoffOutputSequence extends FragmentedOutputSequence {

    protected Vector3f absoluteLocation;

    public KickoffOutputSequence(Vector3f absoluteLocation, BotController botController) {
        super(botController);
        this.absoluteLocation = absoluteLocation;
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        Vector3f convertedLocation = new Vector3f(packet.players(0).physics().location());
        return convertedLocation.absoluteEquals(this.absoluteLocation)
                && queue.size() == 0
                && true;//packet.gameInfo().isKickoffPause();
    }

    public boolean isOnLeft() {
        return botController.data(CarInfoProvider.class).getBotLocation().x < 0;
    }
}
