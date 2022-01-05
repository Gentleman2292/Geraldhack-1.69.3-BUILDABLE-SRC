/*
 * Decompiled with CFR 0.150.
 */
package me.gerald.hack.util.friend;

import java.util.ArrayList;
import java.util.List;
import me.gerald.hack.util.friend.Friend;

public class FriendManager {
    private List<Friend> friends = new ArrayList<Friend>();

    public List<Friend> getFriends() {
        return this.friends;
    }

    public void addFriend(String name) {
        this.friends.add(new Friend(name));
    }

    public void delFriend(String name) {
        this.friends.remove(this.getFriendByName(name));
    }

    public Friend getFriendByName(String name) {
        Friend out = null;
        for (Friend friend : this.getFriends()) {
            if (!friend.getName().equalsIgnoreCase(name)) continue;
            out = friend;
        }
        return out;
    }

    public boolean isFriend(String name) {
        for (Friend f : this.getFriends()) {
            if (!f.getName().equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }
}

