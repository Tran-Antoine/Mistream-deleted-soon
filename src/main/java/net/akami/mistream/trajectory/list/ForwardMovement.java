package net.akami.mistream.trajectory.list;

import net.akami.mistream.output.ControlsOutput;
import net.akami.mistream.trajectory.TerminalOutputSequence;
import rlbot.ControllerState;

public class ForwardMovement extends TerminalOutputSequence {

    private float speed;

    public ForwardMovement(int frameExecutions, float speed) {
        super(frameExecutions, null);
        this.speed = speed;
    }

    @Override
    protected ControllerState loadController() {
        return new ControlsOutput()
                .withSteer(0)
                .withThrottle(speed == -1 ? 1 : speed)
                .withBoost(speed == -1);
    }
}
