package com.bitcamp.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity             //orm 대상일시 자바클래스를 엔티티로 지정
@Table(name="todo") //테이블 이름을 지정
public class TodoEntity {

    // 오브젝트 아이디 @Id 는 기본 키가 될 필드에 지정한다.
    @Id
    //
    // @GeneratedValue(strategy = GenerationType.IDENTITY) //정수 자동 생성
    @GeneratedValue(generator = "uuid2") // uuid는 id 자동생성 방식
    @GenericGenerator(name= "uuid2", strategy = "uuid2")
    private String id;

//    @Column(name="userId")
    //여기 들어갈 필드는 객체 타입이어야한다. 따라서 int를 쓰는게 아니라 Integer(Wrapper class)형을 써야한다
    private String userId;
    private String title;
    private boolean done;
}
