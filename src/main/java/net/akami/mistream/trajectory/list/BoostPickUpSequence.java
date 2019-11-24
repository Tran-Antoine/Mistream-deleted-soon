package net.akami.mistream.trajectory.list;

import net.akami.mistream.gamedata.BoostDataProvider;
import net.akami.mistream.gamedata.BoostPad;
import net.akami.mistream.gamedata.CarInfoProvider;
import net.akami.mistream.trajectory.BotController;
import net.akami.mistream.trajectory.OutputSequence;
import rlbot.flat.GameTickPacket;

import java.util.Queue;
import java.util.function.Function;


public class BoostPickUpSequence extends EndToEndSequence {

    private BoostDataProvider boostData;
    private BoostPad destination;

    /*
    CarInfoProvider info = botController.data(CarInfoProvider.class);
        Vector3f carLocation = info.getBotLocation();
        BoostPad nearestBoost = botController.data(BoostDataProvider.class)
                .getFastestPad(carLocation, info.getBotDirection().flatten(), 0.4f, true);
        return new BoostPickUpSequence(nearestBoost.getLocation(),
                botController.data(BoostDataProvider.class), botController.data(CarInfoProvider.class));
     */
    public BoostPickUpSequence(BoostPad destination, BotController botController) {
        super(destination.getLocation(), botController.data(CarInfoProvider.class));
        this.boostData = botController.data(BoostDataProvider.class);
        this.destination = destination;
    }


    @Override
    protected Function<Integer, Float> getBoostFunction() {
        return (f) -> -1f;
    }

    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        return false;
    }

    @Override
    public boolean isStopped() {
        return !boostData.isPadActive(this.destination);
    }
}
