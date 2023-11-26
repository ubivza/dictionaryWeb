package ru.aleksandr.dictionaryweb.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.aleksandr.dictionaryweb.models.EngRuDictWord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnglishDictionaryDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/dictionary";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EngRuDictWord> index() {
        List<EngRuDictWord> resultList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select word, translate_word from eng_word ew left join eng_translate et on ew.id = et.eng_word_id");
            preparedStatement.executeQuery();

            ResultSet resultSet = preparedStatement.getResultSet();
            while(resultSet.next()) {
                EngRuDictWord word = new EngRuDictWord();
                word.setEnglishWord(resultSet.getString("word"));
                word.setRuWord(resultSet.getString("translate_word"));
                resultList.add(word);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultList;
    }
}
