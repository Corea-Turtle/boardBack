package com.bitcamp.todo.service;

import com.bitcamp.todo.model.TodoEntity;
import com.bitcamp.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    //조회
    public List<TodoEntity> retrieve(final String userId){
        return repository.findByUserId((userId));
    }

    public List<TodoEntity> create(final TodoEntity entity){
        // (1) 저장 될 엔티티가 유효한지 확인
        validate(entity);
        repository.save(entity);

        log.info("Entity Id : {}is saved",entity.getId());
        return repository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity entity){
        // 유효성 검증을 꼭 해줘야함(외부에서 들어온 데이터일 경우)
        if(entity ==null){
            log.warn("Entity cannot be null");
            throw new RuntimeException(("Entity cannot be null"));
        }
        if(entity.getUserId()==null){
            log.warn("Unknown User");
            throw new RuntimeException("Unknown user");
        }
    }
    //수정
    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo ->{
            // 반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 쓴다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            // 데이터베이스에 새 값을 저장한다.
            repository.save(todo);
        });
        return retrieve(entity.getUserId());
    }

    //삭제
    public List<TodoEntity> delete(final TodoEntity entity){
        // 유효성 체크
        validate(entity);
        try{
            //엔티티 삭제
            repository.delete(entity);
        }catch (Exception e){
            // exception 발생 시 id와 exception을 로깅한다.
            log.error("error deleting entity",entity.getId(), e);
            throw new RuntimeException("error deleting entity "+ entity.getId());
        }
        // 새 Todo 리스트를 가져와 리턴
        return retrieve(entity.getUserId());
    }

//    public String testService(){
//        //TodoEntity 생성
//        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
//        //TodoEntity 저장
//        repository.save(entity);
//
//        return "";
//    }

}
