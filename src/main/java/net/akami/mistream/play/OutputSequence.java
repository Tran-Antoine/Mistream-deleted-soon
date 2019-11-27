package net.akami.mistream.play;

import rlbot.ControllerState;
import rlbot.flat.GameTickPacket;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The root class of the queue architecture. Output sequences define what {@link ControllerState} is to be used at what moment.
 * Several implementations of the {@link OutputSequence} interface exist :
 * <ul>
 *     <li>
 *         {@link TerminalOutputSequence}s define linear movements, through one ControllerState that never changes. <br>
 *         Basically, they are simple wrappers to ControllerStates
 *     </li>
 *         {@link AlternativeOutputSequence}s define a list of sequences, containing one that is suitable
 *         for the current situation. <br>
 *
 *     <li>
 *
 *     </li>
 * </ul>
 *
 *
 */
public interface OutputSequence {

    ControllerState apply(LinkedList<OutputSequence> queue);
    boolean isStopped();
    default void queue(Queue<OutputSequence> target) { target.add(this); }
    default boolean isSuitable(GameTickPacket packet, Queue<OutputSequence> queue) { return false; }
}
