package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.output.ControlsOutput;
import net.akami.rlbot.trajectory.TerminalOutputSequence;
import rlbot.ControllerState;

public class ForwardMovement extends TerminalOutputSequence {

    private float speed;

    public ForwardMovement(int frameExecutions, float speed) {
        super(frameExecutions);
        this.speed = speed;
    }

    @Override
    protected ControllerState loadController() {
        return new ControlsOutput()
                .withSteer(0)
                .withThrottle(speed);
    }
}
