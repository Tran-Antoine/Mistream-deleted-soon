package net.akami.rlbot.trajectory.list;

import net.akami.rlbot.output.ControlsOutput;
import net.akami.rlbot.trajectory.TerminalOutputSequence;
import rlbot.ControllerState;

public class LeftMovement extends TerminalOutputSequence {

    private float speed;

    public LeftMovement(int frameExecutions, float speed) {
        super(frameExecutions, null);
        this.speed = speed;
    }

    @Override
    protected ControllerState loadController() {
        return new ControlsOutput()
                .withSteer(-1)
                .withThrottle(speed);
    }
}
