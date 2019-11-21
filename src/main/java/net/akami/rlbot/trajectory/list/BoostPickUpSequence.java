package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.gamedata.BoostDataProvider;
import net.akami.rlbot.gamedata.BoostPad;
import net.akami.rlbot.gamedata.LocationsProvider;
import net.akami.rlbot.trajectory.OutputSequence;
import net.akami.rlbot.vector.Vector3f;
import rlbot.flat.GameTickPacket;

import java.util.Queue;
import java.util.function.Function;


public class BoostPickUpSequence extends EndToEndSequence {

    private BoostDataProvider boostData;
    private BoostPad destination;

    public BoostPickUpSequence(Vector3f start, BoostDataProvider boostData, LocationsProvider locProvider) {
        super(boostData.getNearestPad(start).getLocation(), locProvider);

        this.boostData = boostData;
        this.destination = boostData.getNearestPad(start);
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
