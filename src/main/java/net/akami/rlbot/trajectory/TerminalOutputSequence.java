package net.akami.rlbot.trajectory;

import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

import java.util.Queue;

public abstract class TerminalOutputSequence implements OutputSequence {

    private int frameExecutions;
    private int currentFrameExecutions;
    private ControllerState controllerState;

    public TerminalOutputSequence(int frameExecutions) {
        this.frameExecutions = frameExecutions;
    }

    protected abstract ControllerState loadController();

    @Override
    public ControllerState apply() {
        this.currentFrameExecutions++;
        if(controllerState == null) {
            controllerState = loadController();
        }
        return controllerState;
    }

    @Override
    public void queue(Queue<OutputSequence> target) {
        target.add(this);
    }

    @Override
    public int frameExecutionsNumber() {
        return frameExecutions;
    }

    // Terminal sequences are usually not suitable for any situation. They are rather used in FragmentedOutputSequences
    @Override
    public boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue) {
        return false;
    }

    @Override
    public boolean isStopped() {
        return currentFrameExecutions > frameExecutionsNumber();
    }

}
