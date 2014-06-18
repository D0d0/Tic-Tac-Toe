import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyChooseGameButton extends MyButton implements ActionListener {

	private static final long serialVersionUID = -7324899773747688231L;
	private int id, max, size;
	private Game game;
	private TicTacToe app;
	
	public MyChooseGameButton(String label, int id, Game game, TicTacToe app, int max, int size) {
		super(label);
		this.id = id;
		this.game = game;
		this.app = app;
		this.max = max;
		this.size = size;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		game.setSize(size);
		game.setGameId(id);
		game.setMaxPlayers(max);
		game.connect(id);
		game.setMode(Mode.WAITING_FOR_START);
		app.repaint();
	}

}
