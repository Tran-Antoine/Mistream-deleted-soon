package net.akami.mistream.trajectory;

import rlbot.ControllerState;

import java.util.List;
import java.util.Queue;

public abstract class FragmentedOutputSequence implements OutputSequence {

    private List<OutputSequence> children;
    protected BotController botController;

    public FragmentedOutputSequence(BotController botController) {
        this.botController = botController;
    }

    protected abstract List<OutputSequence> loadChildren();

    @Override
    public int frameExecutionsNumber() {
        if(children == null)
            children = loadChildren();

        return children
                .stream()
                .mapToInt(OutputSequence::frameExecutionsNumber)
                .reduce((a, b) -> a+b)
                .orElse(0);
    }

    @Override
    public void queue(Queue<OutputSequence> target) {
        if(children == null)
            children = loadChildren();
        children.forEach((child) -> child.queue(target));
    }

    @Override
    public boolean isStopped() {
        throw new IllegalStateException("FragmentedOutputSequences should not be present in the queue");
    }

    @Override
    public ControllerState apply() {
        throw new IllegalStateException("FragmentedOutputSequences should not be present in the queue");
    }
}
