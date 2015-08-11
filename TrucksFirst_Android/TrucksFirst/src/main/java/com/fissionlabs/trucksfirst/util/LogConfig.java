package com.fissionlabs.trucksfirst.util;

/**
 * Created by Ashok on 2/20/2015.
 */
public final class LogConfig {
    /**
     * Enable DEBUG logs. Leave this as false in git. Enable locally if you need to see them.
     */
    public static final boolean D = false;

    /**
     * Enable INFO logs. Leave this as false in git. Enable locally if you need to see them.
     */
    public static final boolean I = false;

    /**
     * Enable VERBOSE logs. Leave this as false in git. Enable locally if you need to see them.
     */
    public static final boolean V = false;

    /**
     * Enable WARNINGS logs. Leave this as true in git. disable locally if you need to.
     */
    public static final boolean W = false;

    /**
     * Enable ERROR logs. Leave this as true in git. disable locally if you need to.
     */
    public static final boolean E = true;

    private static final boolean DEBUG_FLAG = false;

    public static boolean DEBUG_FLAG_MIRROR = DEBUG_FLAG;
}
