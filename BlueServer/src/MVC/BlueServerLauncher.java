package MVC;

public class BlueServerLauncher {
	public static void main(String[] args) {
		//TODO launch url request to check last version
		BlueServerModel model = new BlueServerModel();
		BlueServerController controller = new BlueServerController(model);
		controller.displayViews();
	}
}
