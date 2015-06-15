import com.Luguan.Mroff.physics.Collision;
import com.Luguan.Mroff.physics.Side;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PhysicsTest {
	@Test
	public void someTest2() {
		Rectangle r1 = new Rectangle(50, 50, 50, 50);
		Rectangle r2 = new Rectangle(50, 40, 20, 20);
		Side side = Collision.intersects(r1, r2);
		System.out.println(side);
		assertTrue(side == Side.BOTTOM);
	}
}
