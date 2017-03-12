import org.junit.Test;

import com.turtle.activity.common.ActivityType;

public class TestEnum {
	@Test
	public void test() {
		ActivityType[] arr = ActivityType.values();
		System.out.println(arr.length);
		for (ActivityType at : arr) {
			System.out.println(at.name());
		}
	}
}
