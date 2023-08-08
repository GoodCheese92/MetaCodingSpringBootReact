package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 기능을 정의할 수 있고, 트랜잭션을 관리할 수 있음.
@RequiredArgsConstructor    // final로 되어있는 것을 생성자 만들어줌(DI)
@Service
public class BookService {
    // 함수 => 송금() -> 레파지토리에 여러개의 함수 실행 -> commit or rollback

    private final BookRepository bookRepository;

    @Transactional  // 서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리하겠다.
    public Book 저장하기(Book book){
       return bookRepository.save(book);
    }

    @Transactional(readOnly = true) // JPA 변경감지라는 내부 기능 활성화 X, update시의 정합성을 유지해줌. insert의 유형데이터현상(팬텀현상) 못막음.
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
    }

    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();
    }

    @Transactional
    public Book 수정하기(Long id, Book book){
        // 영속화 (book 오브젝트) -> 영속성 컨텍스트 보관
        Book bookEntity = bookRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    } // 함수 종료 -> 트랜잭션 종료 => 영속화 되어있는 데이터를 DB 경신(flush)

    public String 삭제하기(Long id){
        bookRepository.deleteById(id); // 오류가 발생하면 Exception을 탄다. 따라서 신경쓸 필요 없음.
        return "ok";
    }


} // end of class
















