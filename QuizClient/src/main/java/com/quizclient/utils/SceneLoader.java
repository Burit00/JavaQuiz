package com.quizclient.utils;

import com.quizclient.QuizClientApplication;
import com.quizclient.controller.CreatEditQuizController;
import com.quizclient.controller.QuizDetailsController;
import com.quizclient.controller.QuizScoreController;
import com.quizclient.controller.SolveQuizController;
import com.quizclient.model.query.QuizQuery;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class SceneLoader {
    private static Stage stage;

    public static void setStage(Stage stage) {
        if(SceneLoader.stage == null) SceneLoader.stage = stage;
    }

    private static class Loader {
        public Parent root;
        public FXMLLoader fxmlLoader;

        public Loader(Parent root, FXMLLoader fxmlLoader) {
            this.root = root;
            this.fxmlLoader = fxmlLoader;
        }
    }

    private static Loader loadScene(String fileName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getResource(fileName));
        Parent root = getRoot(fxmlLoader);

        return new Loader(root, fxmlLoader);
    }

    public static void loadSelectQuizScene() {
        Loader loader = loadScene("select-quiz-view.fxml");
        showScene(loader.root);
    }

    public static void loadQuizDetailsScene(UUID quizId) {
        Loader loader = loadScene("quiz-details-view.fxml");

        QuizDetailsController controller = loader.fxmlLoader.getController();
        controller.setParameter(quizId);

        showScene(loader.root);
    }

    public static void loadSolveQuizScene(QuizQuery quiz) {
        Loader loader = loadScene("solve-quiz-view.fxml");

        SolveQuizController controller = loader.fxmlLoader.getController();
        controller.setParameter(quiz);

        showScene(loader.root);
    }

    public static void loadQuizScoreScene(List<List<String>> correctQuizAnswers, List<List<String>> userQuizAnswers) {
        Loader loader = loadScene("quiz-score-view.fxml");

        QuizScoreController controller = loader.fxmlLoader.getController();
        controller.setParameter(correctQuizAnswers, userQuizAnswers);

        showScene(loader.root);
    }

    public static void loadCreateQuizScene() {
        Loader loader = loadScene("create-edit-quiz-view.fxml");
        showScene(loader.root);
    }

    public static void loadEditQuizScene(UUID quizId) {
        Loader loader = loadScene("create-edit-quiz-view.fxml");

        CreatEditQuizController controller = loader.fxmlLoader.getController();
        controller.setParameter(quizId);

        showScene(loader.root);
    }

    private static URL getResource(String fileName) {
        return QuizClientApplication.class.getResource(fileName);
    }

    private static Parent getRoot(FXMLLoader fxmlLoader) {
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    private static void showScene(Parent root) {
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
