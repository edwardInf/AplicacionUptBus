package com.example.gcoaquira.aplicacionuptbus.api;

import com.example.gcoaquira.aplicacionuptbus.modelo.BusesConductores;

public class BusConductorResp {
    private BusesConductores[] data;
    public BusesConductores[] getData() {
        return data;
    }

    public static interface OnReceived{

    }
}
