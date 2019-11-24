package net.akami.mistream.trajectory.list;

import net.akami.mistream.output.ControlsOutput;
import net.akami.mistream.trajectory.BotController;
import net.akami.mistream.trajectory.TerminalOutputSequence;
import rlbot.ControllerState;

public class ResetMovement extends TerminalOutputSequence {

    public ResetMovement(int frameExecutions, BotController botController) {
        super(frameExecutions, botController);
    }

    @Override
    protected ControllerState loadController() {
        return ControlsOutput.EMPTY;
    }
}
