package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTacoRepository {

//    private JdbcTemplate jdbc;
//    private SimpleJdbcInsert tacoInfoInserter;
//    private ObjectMapper mapper;
//
//    @Autowired
//    public JdbcTacoRepository(JdbcTemplate jdbc) {
//        this.jdbc = jdbc;
//        this.tacoInfoInserter = new SimpleJdbcInsert(jdbc);
//        mapper = new ObjectMapper();
//    }
//
//
//    public Taco save(Taco taco) {
//        taco.setCreatedAt(new Date());
//        long tacoId = saveTacoInfo(taco);
//        taco.setId(tacoId);
//        for (String ingredientId : taco.getIngredients()) {  // Here can cause of Error
//            saveIngredientToTaco(ingredientId, tacoId);          //
//        }
//        return taco;
//    }
//
//    private long saveTacoInfo(Taco taco) {
//
//        tacoInfoInserter.withTableName("Taco").usingGeneratedKeyColumns("id");
//
//        @SuppressWarnings("unchecked")
//        Map<String, Object> values = mapper.convertValue(taco, Map.class);
//        values.put("createdAt",new Timestamp(taco.getCreatedAt().getTime()));
//
//        long tacoId = tacoInfoInserter.executeAndReturnKey(values).longValue();
//
//        return tacoId;
//    }
//
//    private void saveIngredientToTaco(String ingredientId, long tacoId) {  //Here too
//        jdbc.update("insert into taco_ingredients(ingredient, taco) values(?,?)", ingredientId, tacoId);
//    }
}
