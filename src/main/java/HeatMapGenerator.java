/** Author: Shubham Rane www.linkedin.com/in/shubham-rane97 **/

//import org.tc33.jheatchart.HeatChart;
//

import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for creating, updating of heat chart of machine's movement
 * **/
public class HeatMapGenerator {

    /** Creating heatChart for visualizing vehicle movements */
    private static final double[][] heatChartData = new double[41][41];

    /** Start point is 20.5,20.5 index */
    private static final double i = heatChartData.length / 2.0;
    private static final double j = i;

    /** Keeping destination coordinates same as start while initializing*/
    private static double desti_i = i;
    private static double desti_j = j;

    /** heatChart data generation */
    public static void heatChartGenerator(String direction, long distance) throws NullPointerException {

        double ori_i = desti_i;
        double ori_j = desti_j;

        switch (direction) {
            case "Forward":
                desti_i -= distance;
                desti_j = ori_j;
                break;
            case "Left":
                desti_i = ori_i;
                desti_j -= distance;
                break;
            case "Right":
                desti_i = ori_i;
                desti_j += distance;
                break;
            case "Reverse":
                desti_i += distance;
                desti_j = ori_j;
                break;
        }

        double m = ori_i;
        double n = ori_j;
//        System.out.println("ori_i : " + ori_i + "ori_j : " + ori_j);
//        System.out.println("desti_i : " + desti_i + "desti_j : " + desti_j);

        /** Forward marking **/
        while(m > desti_i && direction.equals("Forward")){
            heatChartData[(int) m][(int) n] += 1;
            m--;
        }
        /** Reverse  marking **/
        while(m < desti_i && direction.equals("Reverse")){
            heatChartData[(int) m][(int) n] += 1;
            m++;
        }
        /** Left  marking **/
        while(n > desti_j && direction.equals("Left")){
            heatChartData[(int) m][(int) n] += 1;
            n--;
        }
        /** Right  marking **/
        while(n < desti_j && direction.equals("Right")){
            heatChartData[(int) m][(int) n] += 1;
            n++;
        }

    }

    /** This heatmap is called on each direction key release*/
    public static void heatMapGeneration() throws IOException {
        /**
         * HeatMap Generation Processing
         * */
        HeatChart heatChartObj = new HeatChart(heatChartData);

        heatChartObj.setTitle("Machine_1.1 Movement HeatMap");

        heatChartObj.saveToFile(new File(Constants.save_heatMapDirectory));
    }


}
