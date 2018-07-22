package GUI;

import DAO.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.hibernate.internal.CoreLogging;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class Main extends Application {
//		private static final Logger
	static {
//		logger=LogManager.getLogger();
		System.out.println("App Loading...");
		HibernateUtil.getSession();
		HibernateUtil.shutdown();
	}
	private static Stage primaryStage;
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader=new FXMLLoader(Main.class.getResource("LoginWindow.fxml"));
		try {
			AnchorPane pane = loader.load();

			Scene scene=new Scene(pane);

			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(o -> System.exit(1));
			primaryStage.setResizable(false);


			this.primaryStage = primaryStage;
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
