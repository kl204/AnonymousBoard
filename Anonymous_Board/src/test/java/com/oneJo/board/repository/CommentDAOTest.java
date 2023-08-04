//package com.oneJo.board.repository;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class YourRepositoryTest {
//
//    @Autowired
//    private YourRepository yourRepository;
//
//    @Autowired
//    private DataSource dataSource;
//
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setup() {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//    }
//
//    @After
//    public void cleanup() {
//        // �뀒�뒪�듃 �썑 �뜲�씠�꽣 �젙由� �벑�쓽 �옉�뾽 �닔�뻾
//    }
//
//    @Test
//    public void testFindById() {
//        // Given
//        YourEntity entity = new YourEntity();
//        entity.setId(1L);
//        entity.setName("Test Entity");
//        // ... (�뀒�뒪�듃 �뜲�씠�꽣 以�鍮�)
//
//        // When
//        yourRepository.save(entity);
//        YourEntity result = yourRepository.findById(entity.getId());
//
//        // Then
//        assertNotNull(result);
//        assertEquals(entity.getId(), result.getId());
//        assertEquals(entity.getName(), result.getName());
//        // ... (湲곕��븯�뒗 寃곌낵 �솗�씤)
//    }
//
//    @Test
//    public void testCustomQuery() {
//        // Given
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, name VARCHAR(255))");
//        jdbcTemplate.execute("INSERT INTO test_table (id, name) VALUES (1, 'Test')");
//        // ... (�뀒�뒪�듃 �뜲�씠�꽣 以�鍮�)
//
//        // When
//        YourEntity result = yourRepository.customQuery("Test");
//
//        // Then
//        assertNotNull(result);
//        assertEquals(1L, result.getId().longValue());
//        assertEquals("Test", result.getName());
//        // ... (湲곕��븯�뒗 寃곌낵 �솗�씤)
//    }
//}
//
//
