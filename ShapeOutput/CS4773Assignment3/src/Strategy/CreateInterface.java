package Strategy;

import Builders.*;

public interface CreateInterface {
	Shape createCircle(int radius);
	Shape createRectangle(int width, int height);
}