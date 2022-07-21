package com.cmc.finder.domain.message.repository;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m " +
            "from Message m " +
            "where m.createTime in (select max(m.createTime) from Message m where m.owner=:owner group by m.other.userId)" +
            "order by m.createTime desc ")
    Slice<Message> findAllByOwner(User owner, Pageable pageable);

    @Query(value = "select m " +
            "from Message m " +
            "where m.owner=:owner and m.other=:other " +
            "order by m.createTime desc ")
    Slice<Message> findAllByOwnerAndOther(User owner, User other, Pageable pageable);

    @Query(value = "select m " +
            "from Message m " +
            "where m.owner=:owner and m.other=:other " +
            "order by m.createTime desc ")
    List<Message> findAllByOwnerAndOther(User other, User owner);


}
