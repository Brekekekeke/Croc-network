package game;

import java.rmi.RemoteException;
import java.util.ArrayList;

import engine.controller.GameResolver;
import engine.exceptions.NotEveryoneChoseCardException;
//import engine.models.DataRectangle;
import engine.models.Pirate;
import engine.models.PirateColor;
import engine.models.Player;
import net.headers.CrGameInterface;
import net.headers.ServerInterface;
import net.server.Server;

public class GameScreen/* implements Screen */{

	
	/**
	 * CrocGame class, contains data relative to a croc game.
	 */
	CrGameInterface croc;
	/**
	 * Game class, needs to be passed around to be able to change screens.
	 */
//	public Game game;
	
	/**
	 * used to multiplex the inputprocessor and the stage's processor
	 */
//	private InputMultiplexer inputMultiplexer;
	/**
	 * the camera used to display the screen's content
	 */
//	OrthographicCamera camera;
	/**
	 * batch used to draw the sprites
	 */
//	SpriteBatch batch;
	/**
	 * the text's font
	 */
//	BitmapFont text;
	/**
	 * representation of the cursor
	 */
//	public Vector3 cursor;
	/**
	 * processor used to process the inputs. (clicks only)
	 */
//	GameInputProcessor inputProcessor;
	
//	Texture[] cards;
//	
//	Texture body;
//	Texture head;
//	Texture leftArm;
//	Texture rightArm;
//	Texture leftLeg;
//	Texture rightLeg;
//	
//	Texture gameBoard;
//	Texture shark;
//	Texture handBoard;
//	Texture background;
	
	Integer nextPlayer = null;
	boolean turnBegin;
	int playedCount;
//	DataRectangle[] pCardSelector;
//	DataRectangle[] pCardSelector2;
	Boolean firstRound;
	int selectCount;
	GameResolver gr;
	Boolean sharkTurn;
	int myID;
	ServerInterface server;
	
	public GameScreen(/*Game g_,*/ CrGameInterface cg_, ServerInterface server, int myID){
		sharkTurn = false;
		this.myID = myID;
		this.server = server;
//		gr = new GameResolver(cg_);
		firstRound = true;
		playedCount = 0;
		selectCount = 0;
//		game = g_;
		croc = cg_;
//		camera = new OrthographicCamera();
//		camera.setToOrtho(false, 1024, 640);
//		text = new BitmapFont((Gdx.files.internal("data/default.fnt")));
//		body = new Texture(Gdx.files.internal("data/body.png"));
//		head = new Texture(Gdx.files.internal("data/head.png"));
//		leftArm = new Texture(Gdx.files.internal("data/leftArm.png"));
//		rightArm = new Texture(Gdx.files.internal("data/rightArm.png"));
//		leftLeg = new Texture(Gdx.files.internal("data/leftLeg.png"));
//		rightLeg = new Texture(Gdx.files.internal("data/rightLeg.png"));
//		gameBoard = new Texture(Gdx.files.internal("data/gameboard.png"));
//		shark = new Texture(Gdx.files.internal("data/shark.png"));
//		handBoard = new Texture(Gdx.files.internal("data/handboard.png"));
//		background = new Texture(Gdx.files.internal("data/background.png"));
//		batch = new SpriteBatch();
//		cursor = new Vector3();
//		inputProcessor = new GameInputProcessor(this);
//		Gdx.input.setInputProcessor(inputProcessor);
		int ii = 0;
		try {
			ii = croc.getPirateOrder().get(0).cards.length;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		cards = new Texture[ii];
		int ij = 0;
		try {
			ij = croc.getPirateOrder().get(0).cards.length;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		pCardSelector = new DataRectangle[ij];
		int ik = 0;
		try {
			ik = croc.getPirateOrder().get(0).cards.length;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		pCardSelector2 = new DataRectangle[ik];
//		for(int i = 0; i < cards.length; i++){
//			cards[i] = new Texture(Gdx.files.internal("data/"+(i+1)+".png"));
//			pCardSelector[i] = new DataRectangle(50+ i*50, 0, 50, 33, i+1);
			int il = 0; 
			try {
				il = croc.getPlayers().length;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(il <= 3){
				System.out.println("derp");
//				pCardSelector2[i] = new DataRectangle(50+ i*50, 75, 50, 33, i+1);
//			}
		}
		this.server.initGame();
//		gr.gameInit();
		try {
			for(Pirate p : croc.getPirateOrder()){
				if(p.owner.isBot){
					p.botCardChooser();
					playedCount++;
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectNextPlayer();
	}
	
	public void pirateColor(Pirate p){
		if(p.color == PirateColor.WHITE) {
//			batch.setColor(Color.WHITE);
		} else if(p.color == PirateColor.RED) {
//			batch.setColor(Color.RED);
		} else if(p.color == PirateColor.GREEN) {
//			batch.setColor(Color.GREEN);
		} else if(p.color == PirateColor.PURPLE) {
//			batch.setColor(Color.PURPLE);
		} else if(p.color == PirateColor.ORANGE) {
//			batch.setColor(Color.ORANGE);
		} else if(p.color == PirateColor.YELLOW) {
//			batch.setColor(Color.YELLOW);
		} else if(p.color == PirateColor.BLACK) {
//			batch.setColor(Color.GRAY);
		}
	}

	public void DrawPlayedCards(){
		ArrayList<Pirate> pirates = null;
		try {
			pirates = croc.getPirateOrder();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < pirates.size(); i++){
			pirateColor(pirates.get(i));
			
			for(int j = 0; j < pirates.get(i).cards.length; j++){
				if(!pirates.get(i).cards[j].isInHand()) {
//					batch.draw(cards[j],100 + 75*j -camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
				}
			}
		}
	}
	
	public void DrawPirates(){
		ArrayList<Pirate> pirates = null;
		try {
			pirates = croc.getPirateOrder();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < pirates.size(); i++){
			pirateColor(pirates.get(i));
//			batch.draw(head,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
//			batch.draw(body,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
			if(pirates.get(i).hasLeftArm()) {
//				batch.draw(leftArm,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
			}
			if(pirates.get(i).hasRightArm()) {
//				batch.draw(rightArm,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
			}
			if(pirates.get(i).hasLeftLeg()) {
//				batch.draw(leftLeg,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
			}
			if(pirates.get(i).hasRightLeg()) {
//				batch.draw(rightLeg,50-camera.position.x, camera.viewportHeight- 60*(i+1) -camera.position.y);
			}
			
		}
	}
	
	public void DrawPlayerAvailableCards(){
		if(nextPlayer != null){
			Player selected = null;
			try {
				selected = croc.getPlayers()[nextPlayer];
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < selected.pirates.length; i++){
				pirateColor(selected.pirates[i]);
				for(int j = 0; j < selected.pirates[i].availableCards.size(); j++){
					int temp = selected.pirates[i].availableCards.get(j).value;
//					batch.draw(cards[temp - 1],temp*50 -camera.position.x,i*75 -camera.position.y);
				}
			}
		}
	}
	
	public void selectNextPlayer(){
		int i = 0;
		Integer nextPlayerTemp = nextPlayer;
		try {
			while(i < croc.getPlayers().length && nextPlayerTemp == nextPlayer){
				if(isLocal(croc.getPlayers()[i]) && croc.getPlayers()[i].isBot == false && !croc.getPlayers()[i].pirates[0].hasPlayed && !croc.getPlayers()[i].hasLost){
					nextPlayer = i;
				}
				i++;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nextPlayerTemp == nextPlayer)
			nextPlayer = null;
	}
	
	public boolean isLocal(Player p) {
		return (p.getClientID() == myID);
	}
	public void drawBgandProps(){
//		batch.draw(background, -camera.position.x, -camera.position.y);
//		batch.draw(gameBoard, -camera.position.x, -camera.position.y);
//		batch.draw(handBoard, -camera.position.x, -camera.position.y);
//		batch.draw(shark, -camera.position.x, -camera.position.y);
	}
	
//	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		int localPlayers = 0;
		try {
			for(Pirate p : croc.getPirateOrder()){
				if(isLocal(p.owner)){
					localPlayers++;
				}
			}
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//drawing gray background (also clearing screen)
//		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//tell camera to update it's matrices
//		camera.update();
		//tell spritbatch to render in the camera's coordinate system
//		batch.setProjectionMatrix(camera.projection);		
		//selection logic
//		cursor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//		camera.unproject(cursor);
		//begin a new batch and draws everything
//		batch.begin();
//		batch.setColor(Color.WHITE);
		drawBgandProps();
		DrawPlayedCards();
		DrawPlayerAvailableCards();
		DrawPirates();
//		batch.end();
					
		server.resolveTurn();
				
		try {
			if(croc.getWinner() != null){
				if(isLocal(croc.getWinner()) && !croc.getWinner().isBot)
					/*game.setScreen(new EndScreen(game, */croc.getWinner()/*, true))*/;
				else
					/*game.setScreen(new EndScreen(game, */croc.getWinner()/*, false))*/;
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		selectNextPlayer();
//		if(playedCount == croc.getPirateOrder().size()){
//			try {
//				gr.roundResolve();
////				batch.begin();
////				batch.setColor(Color.WHITE);
//				drawBgandProps();
//				DrawPlayedCards();
//				DrawPlayerAvailableCards();
//				DrawPirates();
////				batch.end();
//			} catch (NotEveryoneChoseCardException e) {
//			}
//			if(firstRound){
//				firstRound = false;
//				playedCount = 0;
//				for(Pirate p : croc.getPirateOrder()){
//					if(p.owner.isBot){
//						p.botCardChooser();
//						playedCount++;
//					}
//				}
//				selectNextPlayer();
//			}
//			else{
//				sharkTurn = true;
//			}
//		}
	}
	
	
//	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
