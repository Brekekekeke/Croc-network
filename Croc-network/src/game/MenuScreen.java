package game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



public class MenuScreen implements Screen {
	
	Game game;
	/**
	 * Skin class necessary to known what windows look like
	 */
	Skin skin;
	/**
	 * Stage class necessary to draw windows.
	 */
	Stage stage;
	/**
	 * the continue button's texture
	 */
	Texture texture1;
	/**
	 * the leviator's texture
	 */
	Texture texture2;
	/**
	 * label to display the fps
	 */
	Label fpsLabel;
	/**
	 * list containing the list of the .eq files
	 */
	List list;
	/**
	 * class used to handle errors.
	 */
//	ErrorHandler errorHandler;
	

	/**
	 * Constructs a MenuScreen
	 * @param game_game class, necessary to be able to change screens from this screen
	 */
	public MenuScreen(Game game_) {
		//dict init
//		FileManager.validOpDir("assets/res/op");

//		listEntries = FileManager.listEqDir("assets/res/eq");

		//initialisation
		game = game_;
//		errorHandler = new ErrorHandler(stage, skin);
		
		//window skin and texture loading.
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		texture1 = new Texture(Gdx.files.internal("data/continue.png"));
		texture2 = new Texture(Gdx.files.internal("data/leviator.png"));
		TextureRegion image = new TextureRegion(texture1);
		TextureRegion imageFlipped = new TextureRegion(image);
		imageFlipped.flip(true, true);
		TextureRegion image2 = new TextureRegion(texture2);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		//creating button style to give to buttons
		ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(image);
		style.imageDown = new TextureRegionDrawable(imageFlipped);
		
		//creating our start button, got a picture
		//TODO: change picture to something like "continue" or "choose"
		Button imgButton = new Button(new Image(image), skin);
		
		//just a box with an image
		Image imageActor = new Image(image2);
		ScrollPane scrollPane = new ScrollPane(imageActor);
		
		//list setup
		//TODO: create a link with .eq files instead of loading dummy list
		list = new List(skin);
//		list.setItems(listEntries);
		list.getSelection().setMultiple(false);
		list.getSelection().setRequired(true);
		
		//putting list in scrollable element
		ScrollPane scrollPane2 = new ScrollPane(list, skin);
		scrollPane2.setFlickScroll(false);
		
		//putting picture & list on the same line
		SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");
		
		
		//label to display FPS
		fpsLabel = new Label("fps:", skin);
		
		
		// Window setup to hold the components
		//TODO: look for better way to do it. Maybe remove the "X" to close it ?
		Window window = new Window("Welcome, please choose a level", skin);
		window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
		window.defaults().spaceBottom(10);
		window.row().fill().expandX();
		window.add(imgButton);
		window.row();
		window.add(splitPane).fill().expand().colspan(4).maxHeight(200);
		window.row();
		window.add(fpsLabel).colspan(4);
		window.pack();
		
		//putting window in middle of screen
		float x = (Gdx.graphics.getWidth() - window.getWidth()) / 2.0f;
		float y = (Gdx.graphics.getHeight() - window.getHeight()) / 2.0f;
		window.setPosition(x, y);

		//adding window to actors so you can relay events
		stage.addActor(window);
		
		//adding listener to button to start level
		imgButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				new Dialog("Level selection", skin, "dialog") {
					protected void result (Object object) {
						System.out.println("Chosen: " + object);
						if ((Boolean) object == true){
							ArrayList<String> k = new ArrayList<String>();
							if (Gdx.app.getType() == ApplicationType.Android) {
								//k = FileManager.validEqFile("res/eq"+(String)list.getSelected());
								System.out.println("log"+k);
							} else {
							  // ApplicationType.Desktop ..
								//k = FileManager.validEqFile("assets/res/eq/"+(String)list.getSelected());
								System.out.println("log"+k);
							}
							//k = FileManager.validEqFile("res/eq/"+(String) list.getSelected());
							//System.out.println("log"+k);
							if(k == null)
								Gdx.app.exit();
								//errorstuff mb
							//TODO: Switch to game screen load idata.
							else{
								game.setScreen(new MainScreen(game));
								dispose();
							}
						}
					}
				}.text("Are you sure you wanna select this level \"" + list.getSelected() + "\" ?" ).button("Yes", true).button("No", false).key(Keys.ENTER, true)
					.key(Keys.ESCAPE, false).show(stage);
			}
		});
		
	}

	
	@Override
	public void render(float delta) {

		//drawing gray background
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//sending value to fpsLabel to display fps
		fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

		//drawing of the stage (here the window)
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		//cleanup of stored data
		stage.dispose();
		skin.dispose();
		texture1.dispose();
		texture2.dispose();
		// Leave blank
	}

}