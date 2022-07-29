package problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GEORGETest {

    @Test
    void time() {
        var routeArr = new int[]{5, 3, 2, 4};
        var map = new int[][]{
                {1,2,2},
                {2,3,8},
                {2,4,3},
                {3,6,10},
                {3,5,15}
        };

        var itinerary = new Itinerary(6, 5, 1, 6, 20, 4, routeArr, map);
        assertEquals(21, itinerary.time());
    }
}