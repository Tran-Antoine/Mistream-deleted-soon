package net.akami.rlbot.trajectory;

import java.util.Queue;

public abstract class UnfragmentableOutputSequence implements OutputSequence {

    @Override
    public void queue(Queue<OutputSequence> target) {
        target.add(this);
    }
}
