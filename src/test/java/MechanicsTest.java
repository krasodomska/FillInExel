import org.junit.Test;

import java.util.ArrayList;

public class MechanicsTest {
    @Test
    public void hoursPerDayTest(){
        Integer sum = 0;
        for(Integer element : new Mechanics().hoursPerDay(80)){
            //assert (element <=4);
            sum +=element;
            assert (element >0);
            assert (element <=4);
        }
        assert (sum == 80);
    }
}
