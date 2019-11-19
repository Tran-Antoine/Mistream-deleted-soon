package net.akami.rlbot.trajectory;

import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

import java.util.Queue;

public interface OutputSequence {

    ControllerState apply();
    int frameExecutionsNumber();
    boolean isStopped();
    boolean isSuitable(GameTickPacket packet, int delay, Queue<OutputSequence> queue);
    void queue(Queue<OutputSequence> target);
}
