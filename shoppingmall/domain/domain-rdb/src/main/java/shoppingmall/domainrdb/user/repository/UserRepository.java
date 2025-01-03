package shoppingmall.domainrdb.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.domainrdb.user.entity.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // TODO : 로직 구현 중 필요하다가 생각해서 작성했으나 구현 중 불필요해진 관계로 일단 주석처리
    //    @Query("SELECT u from User u INNER JOIN u.chatRooms where u.chatRooms = :chatRoom ")
//    @Query("SELECT cr.buyer, cr.seller From ChatRoom cr where cr.id = : chatRoomId")
//    Optional<List<User>> findUserByChatRoom(@Param("chatRoomId") UUID chatRoomId);

    User findByNameAndPassword(String name, String password);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
