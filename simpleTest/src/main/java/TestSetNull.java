import java.util.HashSet;
import java.util.Set;

public class TestSetNull {

    public static void main(String[] args) {
        Set<Long> set = new HashSet<Long>();
        set.add(1L);
        set.add(1L);
        set.add(2L);
        set.add(null);
        set.add(null);
        set.add(3L);
        set.add(4L);
    }

}
