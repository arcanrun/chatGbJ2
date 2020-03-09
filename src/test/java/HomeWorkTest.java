import org.junit.Assert;
import org.junit.Before;
import lesson06.HomeWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class HomeWorkTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}, new int[]{1, 2, 4, 5}, new int[]{1,7}, true},
                {new int[]{1, 2, 3, 4, 1, 1, 1, 5, 4, 0, 0, 0, 0}, new int[]{3, 2, 6, 5}, new int[]{0,0,0,0}, false},
                {new int[]{1,4,1}, new int[]{1}, new int[]{1}, true}


        });
    }

    private int[] arrOne, arrTwo, arrOneRes;
    private boolean arrCheckRes;

    public HomeWorkTest(int[] arrOne, int[] arrTwo, int[] arrOneRes, boolean arrCheckRes) {
        this.arrOne = arrOne;
        this.arrTwo = arrTwo;
        this.arrOneRes = arrOneRes;
        this.arrCheckRes = arrCheckRes;
    }

    private HomeWork hm;

    @Before
    public void init() {
        hm = new HomeWork();
    }

    @Test
    public void testNewArr() {

        Assert.assertArrayEquals(arrOneRes, hm.newArr(arrOne));
    }

    @Test
    public void testCheckArr() {
        Assert.assertEquals(arrCheckRes,hm.checkArr(arrTwo));
    }

    @Test(expected = RuntimeException.class)
    public  void testNewArronEmptyArr(){
        hm.newArr(new int[]{});
    }
    @Test(expected = RuntimeException.class)
    public void testNewArrOnExpectedNumber(){
        hm.newArr(new int[]{1,2,3,5,7});
    }

}
