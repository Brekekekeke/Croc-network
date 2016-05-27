package game;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import engine.controller.GameBuilder;
import engine.exceptions.PlayerAmountException;
import engine.models.CrGame;
import engine.models.PirateColor;
import net.headers.ComputeInterface;
import net.headers.ServerInterface;
import net.server.Server;

public class MainScreen implements Screen{
	
	public ServerInterface server;
	
	/**
	 * Game class, needs to be passed around to be able to change screens
	 */
	public Game game;
	
	/**
	 * skin used to display windows
	 */
	Skin skin;
	/**
	 * a stage is necessary to display windows
	 */
	Stage stage;
	/**
	 * used to multiplex the inputprocessor and the stage's processor
	 */
	private InputMultiplexer inputMultiplexer;
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
	/**
	 * representation of the cursor
	 */
	public Vector3 cursor;
	/**
	 * processor used to process the inputs. (clicks only)
	 */
	MainInputProcessor inputProcessor;
	/**
	 * placeholder create game button image 100*50
	 */
	Texture createImage;
	/**
	 * placeholder join game button image 100*50
	 */
	Texture joinImage;
	/**
	 * placeholder exit game button image 100*50
	 */
	Texture exitImage;

	Rectangle exit;
	Rectangle create;
	Rectangle join;
	Dialog playerAmount;
	Window createGame;
	Dialog chooseIP;

	TextArea ipGet;
	TextArea nameGet;
	
	int players;
	int distants;
	int connected;
	SelectBox<PirateColor>[] playerColors;	
	TextArea[] localPlayerNames;
	String[] playerNames;
	TextButton startGame;
	CheckBox[] bots;
	
	public MainScreen(Game game_){
		players = 0; 
		distants = 0;
		connected = 0;
		game = game_;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1024, 640);
		text = new BitmapFont((Gdx.files.internal("data/default.fnt")));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		batch = new SpriteBatch();
		cursor = new Vector3();
		inputProcessor = new MainInputProcessor(this);
		stage = new Stage(new ScreenViewport());
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage);
		inputMultiplexer.addProcessor(inputProcessor);
		createImage = new Texture(Gdx.files.internal("data/createButton.png"));
		joinImage = new Texture(Gdx.files.internal("data/joinButton.png"));
		exitImage = new Texture(Gdx.files.internal("data/exitButton.png"));
		Gdx.input.setInputProcessor(inputMultiplexer);
		create = new Rectangle();
		create.height = 50;
		create.width = 100;
		create.x = camera.position.x - 50;
		create.y = camera.position.y;
		join = new Rectangle();
		join.height = 50;
		join.width = 100;
		join.x =  camera.position.x - 50;
		join.y = camera.position.y - 75;
		exit = new Rectangle();
		exit.height = 50;
		exit.width = 100;
		exit.x = camera.position.x - 50;
		exit.y = camera.position.y - 150;
		playerAmount = new Dialog("Choose player amount", skin){
			protected void result (Object object) {
				players = (Integer) object;
				playerNames = new String[players];
				Dialog distant = new Dialog("How many distant players (will have to connect to you)", skin){
					protected void result (Object object) {
						distants = (Integer) object;
						//TODO: add server here, we know how many players we are waiting for
						server = startServer(players);
						generateGameSettings();
					}
				};;
				for(int i = 0; i < players; i++){
					distant.button(Integer.toString(i), i);
				}
				distant.show(stage);
				
			}
		};;
		playerAmount.button("2", 2);
		playerAmount.button("3", 3);
		playerAmount.button("4", 4);
		playerAmount.button("5", 5);
		playerAmount.button("6", 6);
		playerAmount.button("7", 7);
		chooseIP = new Dialog("enter ip adress (fromat type: x.x.x.x)",skin){
			protected void result (Object object) {
				System.out.println(ipGet.getText() + " " + nameGet.getText());
				//TODO: use ipget.gettext to get the ip of the host and connect to server, color chosen by server
			}
		};;
		chooseIP.add(new Label("ip:", skin) );
		ipGet = new TextArea("",skin);
		chooseIP.add(ipGet);
		chooseIP.add(new Label("name:", skin));
		nameGet = new TextArea("",skin);
		chooseIP.add(nameGet);
		chooseIP.button("ok");
		

		
		
	}
	/**
	 * Sets name for local players and color for all players.
	 */


	@SuppressWarnings("unchecked")
	private void generateGameSettings(){
		localPlayerNames = new TextArea[players - distants];
		bots = new CheckBox[players - distants];

		if(players > 3){
			 playerColors = new SelectBox[players];
		}
		else{
			playerColors = new SelectBox[players*2];
		}
		createGame = new Window("Welcome, please choose your settings", skin);
		createGame.defaults().spaceBottom(10);
		//createGame.row().fill().expandX();
		createGame.row();
		Array<PirateColor> colors = new Array<PirateColor>();
		colors.add(PirateColor.RED);
		colors.add(PirateColor.BLACK);
		colors.add(PirateColor.GREEN);
		colors.add(PirateColor.ORANGE);
		colors.add(PirateColor.PURPLE);
		colors.add(PirateColor.WHITE);
		colors.add(PirateColor.YELLOW);
		
		for(int i = 0; i < players; i++){
			createGame.add(new Label("name", skin));
			if(i<players-distants){
				localPlayerNames[i] = new TextArea("",skin);
				createGame.add(localPlayerNames[i]);
				bots[i] = new CheckBox("bot",skin);
				createGame.add(bots[i]);
			}
			else{
				createGame.add(new Label("Distant player "+ Integer.toString(i + (distants - players) +1), skin));
			}
				
			if(players > 3){
				playerColors[i] = new SelectBox<PirateColor>(skin);
				playerColors[i].setItems(colors);
				createGame.add(playerColors[i]);
			}
			else{
				playerColors[i*2] = new SelectBox<PirateColor>(skin);
				playerColors[i*2+1] = new SelectBox<PirateColor>(skin);
				playerColors[i*2].setItems(colors);
				createGame.add(playerColors[i*2]);
				playerColors[i*2+1].setItems(colors);
				createGame.add(playerColors[i*2+1]);
			}
			createGame.row();
		}
		startGame = new TextButton("start",skin);
		startGame.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				
				if(connected == distants){
					try {
						GameBuilder gb = new GameBuilder(players);
						for(int i = 0; i < players - distants; i++){
							server.addPlayer(localPlayerNames[i].getText(), bots[i].isChecked(), playerColors[i].getSelected());
						}
		
						CrGameInterface g = server.createGame();
//						CrGame g = gb.createGame();
						game.setScreen(new GameScreen(game, g));
					} catch (PlayerAmountException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//game.setScreen(new GameScreen(game, ));
				}
			}
		});
		createGame.add(startGame);
		if(distants != 0){
			startGame.setColor(0.2f, 0.2f, 0.2f, 1);
			createGame.add(new Label("Button will become clickable when enough distant player joined.",skin));
		}
		createGame.pack();

		createGame.setPosition(0, 0);
		

		//adding window to actors so you can relay events
		stage.addActor(createGame);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		if(connected > 0 && connected == distants){
			startGame.setColor(0.8f, 0.8f, 0.8f, 1);
		}
		//drawing gray background (also clearing screen)
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//tell camera to update it's matrices
		camera.update();
		//tell spritbatch to render in the camera's coordinate system
		batch.setProjectionMatrix(camera.projection);		
		//selection logic
		cursor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(cursor);
		//begin a new batch and draws everything
		batch.begin();
		batch.draw(createImage, -camera.position.x, -camera.position.y);
		batch.draw(joinImage, -camera.position.x, -camera.position.y);
		batch.draw(exitImage, -camera.position.x, -camera.position.y);
		batch.end();
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
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
	public ServerInterface startServer(int nbPlayers) {
		new Server(nbPlayers);
	}
	
	public ServerInterface connectToServer(String ip, int serverPort) {
		String name = "server";
    	Registry registry = LocateRegistry.getRegistry(serverPort);
//    	Registry r = new Registry().connect("localhost", serverPort);
    	ServerInterface comp = (ServerInterface) registry.lookup(name);
    	return comp;
	}
}
