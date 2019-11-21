package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.gamedata.LocationsProvider;
import net.akami.rlbot.output.ControlsOutput;
import net.akami.rlbot.trajectory.UnfragmentableOutputSequence;
import net.akami.rlbot.vector.Vector3f;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.manager.BotLoopRenderer;
import rlbot.render.Renderer;

import java.awt.*;
import java.util.function.Function;

public abstract class EndToEndSequence extends UnfragmentableOutputSequence {

    protected Vector3f end;
    private LocationsProvider locProvider;
    private int frameExecuted;

    // for testing only
    public static Bot bot;

    protected EndToEndSequence(Vector3f end, LocationsProvider locProvider) {
        this.end = end;
        this.locProvider = locProvider;
    }

    protected abstract Function<Integer, Float> getBoostFunction();

    @Override
    public ControllerState apply() {

        Vector3f carDir = locProvider.getBotDirection();
        Vector3f carIdealDir = end.minus(locProvider.getBotLocation());

        Renderer renderer = BotLoopRenderer.forBotLoop(bot);
        renderer.drawLine3d(Color.BLUE, locProvider.getBotLocation(), end);
        renderer.drawLine3d(Color.BLACK, locProvider.getBotLocation(), carDir.scaled(10000).plus(locProvider.getBotLocation()));

        double angle = Math.toDegrees(Math.acos(carDir.dot(carIdealDir) / (carIdealDir.magnitude() * carDir.magnitude())));
        float steer;

        if(angle > 8) {
            steer = -1;
        } else if(angle < -8) {
            steer = 1;
        } else {
            steer = 0;
        }

        float speed = getBoostFunction().apply(++frameExecuted);
        boolean drift = Math.abs(angle) > 60 && angle % 2 == 0;

        return new ControlsOutput()
                .withSteer(steer)
                .withThrottle(speed == -1 ? 1 : speed)
                .withBoost(speed == -1 && !drift)
                .withSlide(drift);
    }

    @Override
    public int frameExecutionsNumber() {
        return 0;
    }

    @Override
    public boolean isStopped() {
        return locProvider.getBotLocation().equals(end);
    }
}
