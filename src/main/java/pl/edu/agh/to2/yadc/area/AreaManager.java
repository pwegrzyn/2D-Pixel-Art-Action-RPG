package pl.edu.agh.to2.yadc.area;


public class AreaManager {

    private static Area currentArea;

    public static void setCurrentArea(Area area) {
        currentArea = area;
    }

    public static Area getCurrentArea() {
        return currentArea;
    }
}