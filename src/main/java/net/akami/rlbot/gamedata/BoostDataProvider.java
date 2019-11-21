package net.akami.rlbot.gamedata;

import net.akami.rlbot.vector.Vector3f;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.FieldInfo;
import rlbot.flat.GameTickPacket;

import java.util.ArrayList;
import java.util.List;

public class BoostDataProvider implements DataProvider {

    private final List<BoostPad> orderedBoosts = new ArrayList<>();
    private final List<BoostPad> fullBoosts = new ArrayList<>();
    private final List<BoostPad> smallBoosts = new ArrayList<>();

    @Override
    public void update(GameTickPacket packet) {
        FieldInfo fieldInfo;
        try {
            fieldInfo = RLBotDll.getFieldInfo();
        } catch (RLBotInterfaceException e) {
            e.printStackTrace();
            return;
        }
        synchronized (orderedBoosts) {
            orderedBoosts.clear();
            fullBoosts.clear();
            smallBoosts.clear();

            for (int i = 0; i < fieldInfo.boostPadsLength(); i++) {
                if(!packet.boostPadStates(i).isActive()) {
                    continue;
                }
                rlbot.flat.BoostPad flatPad = fieldInfo.boostPads(i);
                BoostPad ourPad = new BoostPad(new Vector3f(flatPad.location()), flatPad.isFullBoost());
                orderedBoosts.add(ourPad);
                if (ourPad.isFullBoost()) {
                    fullBoosts.add(ourPad);
                } else {
                    smallBoosts.add(ourPad);
                }
            }
        }
    }

    public BoostPad getNearestPad(Vector3f carLocation) {
        double distance = 1000000;
        BoostPad currentNearest = null;

        for(BoostPad pad : orderedBoosts) {
            double localDistance = pad.getLocation().distance(carLocation);
            if(localDistance < distance) {
                distance = localDistance;
                currentNearest = pad;
            }
        }
        return currentNearest;
    }

    public boolean isPadActive(BoostPad destination) {
        return orderedBoosts.contains(destination);
    }
}
