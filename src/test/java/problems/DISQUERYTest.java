package problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DISQUERYTest {

    @Test
    public void result(){
        var streets = new String[]{
                "2 3 100",
                "4 3 200",
                "1 5 150",
                "1 3 50",
        };

        var journeys = new String[]{
                "2 4",
                "3 5",
                "1 2",
        };

        var output = new String[]{
                "100 200",
                "50 150",
                "50 100",
        };


        var roadMap = new RoadMap(streets,5);

        for(int i = 0; i < journeys.length; i++) {
            assertEquals(output[i], roadMap.result(journeys[i]));
        }

    }

}