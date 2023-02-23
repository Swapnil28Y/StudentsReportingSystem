package com.sale.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.sale.model.Student;

public interface StudentDao extends ElasticsearchRepository<Student, String> {

}
