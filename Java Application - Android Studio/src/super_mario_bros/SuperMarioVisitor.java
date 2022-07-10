package com.example.efaideleon.super_mario_bros;

import com.example.efaideleon.super_mario_bros.Enemy;

/**
 * Created by yashaswiniamaresh on 5/24/17.
 */

public interface SuperMarioVisitor {
    public int visit(Enemy enemy);
    public int visit(Item item);
}
