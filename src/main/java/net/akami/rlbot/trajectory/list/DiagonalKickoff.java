package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.gamedata.BoostDataProvider;
import net.akami.rlbot.gamedata.BoostPad;
import net.akami.rlbot.gamedata.LocationsProvider;
import net.akami.rlbot.trajectory.BotController;
import net.akami.rlbot.trajectory.OutputSequence;
import net.akami.rlbot.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class DiagonalKickoff extends KickoffOutputSequence {

    public DiagonalKickoff(BotController botController) {
        super(new Vector3f(-2048, -2560, 17.01), botController);
    }

    @Override
    protected List<OutputSequence> loadChildren() {
        return Collections.singletonList(moveTowardsPad());
    }

    private OutputSequence moveTowardsPad() {
        Vector3f carLocation = botController.getDataProvider(LocationsProvider.class).getBotLocation();
        BoostPad nearestBoost = botController.getDataProvider(BoostDataProvider.class).getNearestPad(carLocation);
        return new BoostPickUpSequence(nearestBoost.getLocation(),
                botController.getDataProvider(BoostDataProvider.class), botController.getDataProvider(LocationsProvider.class));
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        /*return queue.size() == 0 &&  packet.gameInfo().isKickoffPause()
                && super.isSuitable(packet, delay, queue);*/
        return queue.size() == 0;
    }
}
