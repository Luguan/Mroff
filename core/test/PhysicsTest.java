import com.Luguan.Mroff.physics.Collision;
import com.Luguan.Mroff.physics.Side;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PhysicsTest {
	@Test
	public void collideTop() {
		Rectangle r1 = new Rectangle(10, 10, 10, 10);
		Rectangle r2 = new Rectangle(1, 1, 10, 10);
		Vector2 collision = Collision.intersects(r1, r2);
		System.out.println(collision);
		//assertEquals(collision, Side.TOP);
	}
/*
	@Test
	public void collideLeft() {
		Rectangle r1 = new Rectangle(9, 0, 10, 10);
		Rectangle r2 = new Rectangle(0, 0, 10, 10);
		Side side = Collision.intersects(r1, r2);
		assertEquals(side, Side.LEFT);
	}

	@Test
	public void collideRight() {
		Rectangle r1 = new Rectangle(0, 0, 10, 10);
		Rectangle r2 = new Rectangle(9, 0, 10, 10);
		Side side = Collision.intersects(r1, r2);
		assertEquals(side, Side.RIGHT);
	}

	@Test
	public void collideBottom() {
		Rectangle r1 = new Rectangle(0, 9, 10, 10);
		Rectangle r2 = new Rectangle(0, 0, 10, 10);
		Side side = Collision.intersects(r1, r2);
		assertEquals(side, Side.BOTTOM);
	}
*/
	@Test
	public void collideNothing() {
		Rectangle r1 = new Rectangle(0, 0, 10, 10);
		Rectangle r2 = new Rectangle(100, 100, 10, 10);
		Vector2 collision = Collision.intersects(r1, r2);
		System.out.println(collision);
		assertEquals(collision, new Vector2());
	}
}
