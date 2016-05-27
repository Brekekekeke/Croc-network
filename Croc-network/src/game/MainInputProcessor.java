package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class MainInputProcessor implements InputProcessor {

	/**
	 * Constructs MyInputProcessor.
	 * @param g_ Adds the GameScreen which is concerned by the input processor.
	 */	
	MainScreen m;
	public MainInputProcessor(MainScreen m_){
		m = m_;
	}
	
	
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		if(m.exit.contains(m.cursor.x, m.cursor.y))
			Gdx.app.exit();
		if(m.create.contains(m.cursor.x, m.cursor.y))
			m.playerAmount.show(m.stage);
		if(m.join.contains(m.cursor.x, m.cursor.y))
			m.chooseIP.show(m.stage);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
