package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import engine.models.Player;

public class EndScreen implements Screen{

	/**
	 * the game's winner
	 */
	Player winner;
	/**
	 * the game class
	 */
	Game game;
	/**
	 * Texture displayed at end of game.
	 */
	Texture endTexture;
	
	/**
	 * the camera used to display the screen's content
	 */
	OrthographicCamera camera;
	/**
	 * batch used to draw the sprites
	 */
	SpriteBatch batch;
	/**
	 * the text's font
	 */
	BitmapFont text;
	public EndScreen(Game g_, Player winner_, Boolean isVictory){
		game = g_;
		winner = winner_;
		if(isVictory)
			endTexture = new Texture(Gdx.files.internal("data/win.png"));
		else
			endTexture = new Texture(Gdx.files.internal("data/lose.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 640);
		text = new BitmapFont((Gdx.files.internal("data/default.fnt")));
		Gdx.input.setInputProcessor(new EndInputProcessor(this));
		batch = new SpriteBatch();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//drawing gray background (also clearing screen)
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//tell camera to update it's matrices
		camera.update();
		//tell spritbatch to render in the camera's coordinate system
		batch.setProjectionMatrix(camera.projection);		
		//begin a new batch and draws everything
		batch.begin();
		batch.draw(endTexture, -250, 100);
		text.draw(batch, "the winner is "+winner.name+"!", -75, 0);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
