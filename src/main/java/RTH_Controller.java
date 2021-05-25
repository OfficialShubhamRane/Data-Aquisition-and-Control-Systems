import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class RTH_Controller {

    /** Create a Data structure to hold Body movement commands */



    /** Push every body movement done by machine in stack as a log*/

    /** For backtracking Pop actions out of Stack */

    /**
     * This is path retracing for RTH
     * For every pop action create keyboard button press using java.awt.robot class
     */

    /**
     * Get 2D heat chart (heatChartData) from HeatChartGenerator method and find the shortest path from current position to origin
     * Shortest path finding ( Use efficient algorithms )
     * This is self RTH
     * */

    static Stack<String> traceBackStack = new Stack<>();

    public static void traceBackController() throws IOException {

        String direction = "Forward";

        while (!traceBackStack.empty()) {
            String currPath = traceBackStack.pop();

            if (currPath.contains("F")){
                System.out.println("Forward" + currPath.substring(1));
                direction = "Forward";

            }else if (currPath.contains("R")){
                System.out.println("Right" + currPath.substring(1));
                direction = "Right";

            }else if (currPath.contains("B")){
                System.out.println("Reverse" + currPath.substring(1));
                direction = "Reverse";

            }else if (currPath.contains("L")){
                System.out.println("Left" + currPath.substring(1));
                direction = "Left";

            }

            long distance = Long.parseLong(currPath.substring(1));

            /** Sending data to heatMapGenerator class after each key release*/
            HeatMapGenerator.heatChartGenerator(direction, distance);
            HeatMapGenerator.heatMapGeneration();

            /** Responsible for creating blended heatChart with maps*/
            new MapBlender().combineHeatmapWithGoogleMap();
        }



    }
}