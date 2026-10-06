package com.kaan.gradesystem;

import com.kaan.gradesystem.repository.StudentElasticsearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class GradeSystemApplicationTests {

    // Context testi gerçek Elasticsearch'e bağlanmasın.
    // Repository yerine test sırasında mock nesne kullanılır.
    @MockitoBean
	//Bu test sırasında gerçek StudentElasticsearchRepository oluşturma. Onun yerine sahte/mock bir tane koy.
    private StudentElasticsearchRepository studentElasticsearchRepository;

    @Test
    void contextLoads() {
    }
}