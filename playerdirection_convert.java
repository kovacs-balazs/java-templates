public static String direction(Player p) {
        double rotation = p.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "North";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "North";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "East";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "East";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "South";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D)) {
            return "South";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D)) {
            return "West";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D)) {
            return "West";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "North";
        }
        return null;
    }
}
