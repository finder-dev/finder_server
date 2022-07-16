package com.cmc.finder.domain.message.repository;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "select m " +
            "from Message m " +
            "where m.createTime in (select max(m.createTime) from Message m group by m.to.userId)" +
            "order by m.createTime desc ")
    Slice<Message> findAllByFrom(User from, Pageable pageable);

    @Query(value = "select m "+
    "from Message m " +
    "where (m.from=:from and m.to=:to) or (m.from=:to and m.to=:from) " +
    "order by m.createTime desc ")
    Slice<Message> findAllByFromOrTo(User from, User to, Pageable pageable);

}
